package edu.tsystems.javaschool.logapp.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tsystems.javaschool.logapp.api.dto.DriverUserDTO;
import edu.tsystems.javaschool.logapp.api.dto.OrderDTO;
import edu.tsystems.javaschool.logapp.api.service.CityService;
import edu.tsystems.javaschool.logapp.api.service.DriverService;
import edu.tsystems.javaschool.logapp.api.service.OrderService;
import edu.tsystems.javaschool.logapp.api.service.OrderWayPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/myOrder")
public class MyOrderController {


    private final DriverService driverService;
    private final OrderService orderService;
    private final CityService cityService;
    private final OrderWayPointService pointService;
    private static final String REDIRECT = "redirect: .";
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public MyOrderController(DriverService driverService, OrderService orderService, CityService cityService,
                             OrderWayPointService pointService) {
        this.driverService = driverService;
        this.orderService = orderService;
        this.cityService = cityService;
        this.pointService = pointService;
    }

    @RequestMapping(value = "", method = {RequestMethod.GET,RequestMethod.POST})
    public String list(Model model, Principal principal) {
        DriverUserDTO driver = driverService.getDUDtoByEmail(principal.getName());

        if (driver.getAssignedOrder() != null) {
            List<Integer> wayPoints = driver.getAssignedOrder().getWayPointsIds();
            model.addAttribute("pointList", wayPoints);
            List<Integer> driversOnOrder = driver.getAssignedOrder().getDriversOnOrderIds();
            model.addAttribute("driverList", driversOnOrder);
        }
        model.addAttribute("driver", driver);
        model.addAttribute("pointMap", orderService.getPointMap());
        model.addAttribute("cityMap", cityService.getCityMap());
        model.addAttribute("driverMap", driverService.getDriverMap());
        model.addAttribute("statusEnum", DriverUserDTO.Status.values());
        return "myOrder/MyOrderPage";
    }

    @PostMapping(value = "/setNewStatus")
    public String setStatusSubmit(@RequestParam(name = "driverStatus") String status, Principal principal) {
        driverService.getDUDtoByEmailAndSetStatus(principal.getName(),status);


        return REDIRECT;
    }

    @GetMapping("/editOrder/{id}")
    public ModelAndView editOrderShow(@PathVariable("id") Integer id,Principal principal) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView("myOrder/EditOrderPage");
        OrderDTO orderDTO = orderService.getOrderById(id);
        List<Integer> wayPointsIds = orderDTO.getWayPointsIds();
        mav.addObject("cityJsonList",
                objectMapper.writeValueAsString(pointService.getCityCoordinates(wayPointsIds)));
        mav.addObject("order", orderDTO);
        mav.addObject("waypoints", pointService.getAllWaypoints());
        DriverUserDTO driver = driverService.getDUDtoByEmail(principal.getName());
        driverService.validateGrants(driver,id);
        return mav;
    }

    @RequestMapping(value = "/setLoaded/{orderId}/{pointId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String setLoaded(@PathVariable("orderId") int orderId, @PathVariable("pointId") int pointId,Principal principal) {
        DriverUserDTO driver = driverService.getDUDtoByEmail(principal.getName());
        driverService.validateGrants(driver,orderId);

        orderService.updateCargoStatus(orderId, pointId);
        return "redirect: /myOrder/editOrder/"+orderId;
    }

    @RequestMapping(value = "/setUnloaded/{orderId}/{pointId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String setUnoaded(@PathVariable("orderId") int orderId, @PathVariable("pointId") int pointId,Principal principal) {
        DriverUserDTO driver = driverService.getDUDtoByEmail(principal.getName());
        driverService.validateGrants(driver,orderId);

        orderService.updateCargoStatus(orderId, pointId);
        orderService.checkIfOrderIsDone(orderId);
        return "redirect: /myOrder/editOrder/"+orderId;
    }

    @RequestMapping(value = "/setOnRest", method = {RequestMethod.GET, RequestMethod.POST})
    public String setOnRest(Principal principal) {
        DriverUserDTO driver = driverService.getDUDtoByEmail(principal.getName());
        driverService.setDriverOnRest(driver);
        return REDIRECT;
    }

    @RequestMapping(value = "/setOnShift", method = {RequestMethod.GET, RequestMethod.POST})
    public String setOnShift(Principal principal) {
        DriverUserDTO driver = driverService.getDUDtoByEmail(principal.getName());
        driverService.setDriverOnShift(driver);
        return REDIRECT;
    }

}
