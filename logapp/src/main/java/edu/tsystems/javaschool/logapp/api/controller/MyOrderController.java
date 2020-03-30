package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.dto.DriverUserDTO;
import edu.tsystems.javaschool.logapp.api.dto.OrderDTO;
import edu.tsystems.javaschool.logapp.api.exception.BusinessLogicException;
import edu.tsystems.javaschool.logapp.api.service.CityService;
import edu.tsystems.javaschool.logapp.api.service.DriverService;
import edu.tsystems.javaschool.logapp.api.service.OrderService;
import edu.tsystems.javaschool.logapp.api.service.OrderWayPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/myOrder")
public class MyOrderController {


    private final DriverService driverService;
    private final OrderService orderService;
    private final CityService cityService;
    private final OrderWayPointService pointService;
    private final HttpServletRequest request;

    @Autowired
    public MyOrderController(DriverService driverService, OrderService orderService, CityService cityService, OrderWayPointService pointService, HttpServletRequest request) {
        this.driverService = driverService;
        this.orderService = orderService;
        this.cityService = cityService;
        this.pointService = pointService;
        this.request = request;
    }

    @RequestMapping(value = "", method = {RequestMethod.GET,RequestMethod.POST})
    public String list(Model model, Principal principal) throws BusinessLogicException {
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
        return "myOrder/myAccount";
    }

    @RequestMapping(value = "/setNewStatus", method = {RequestMethod.POST})
    public String setStatusSubmit(@RequestParam(name = "driverStatus") String status, Principal principal) {
        driverService.getDUDtoByEmailAndSetStatus(principal.getName(),status);

        return "redirect: .";
    }

    @GetMapping("/editOrder/{id}")
    public ModelAndView editOrderShow(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("myOrder/editOrder");
        OrderDTO orderDTO = orderService.getOrderById(id);
        mav.addObject("order", orderDTO);
        mav.addObject("waypoints", pointService.getAllWaypoints());
        return mav;
    }

    @RequestMapping(value = "/setLoaded/{orderId}/{pointId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String setLoaded(@PathVariable("orderId") int orderId, @PathVariable("pointId") int pointId) {
        orderService.updateCargoStatus(orderId, pointId);
        return "redirect: /myOrder/editOrder/"+orderId;
    }

    @RequestMapping(value = "/setUnloaded/{orderId}/{pointId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String setUnoaded(@PathVariable("orderId") int orderId, @PathVariable("pointId") int pointId) {
        orderService.updateCargoStatus(orderId, pointId);
        orderService.checkIfOrderIsDone(orderId);
        return "redirect: /myOrder/editOrder/"+orderId;
    }

    @RequestMapping(value = "/setOnRest", method = {RequestMethod.GET, RequestMethod.POST})
    public String setOnRest(Principal principal) {
        DriverUserDTO driver = driverService.getDUDtoByEmail(principal.getName());
        driverService.setDriverOnRest(driver);
        return "redirect: .";
    }

    @RequestMapping(value = "/setOnShift", method = {RequestMethod.GET, RequestMethod.POST})
    public String setOnShift(Principal principal) {
        DriverUserDTO driver = driverService.getDUDtoByEmail(principal.getName());
        driverService.setDriverOnShift(driver);
        return "redirect: .";
    }
//
//    @GetMapping("/setStatus")
//    public ModelAndView setStatus(Model model, Principal principal) {
//        ModelAndView mav = new ModelAndView("myOrder/setStatus");
//        mav.addObject("driverDto", driverService.getDUDtoByEmail(principal.getName()));
//        mav.addObject("statusEnum", DriverUserDTO.Status.values());
//        return mav;
//    }




}
