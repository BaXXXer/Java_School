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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    public MyOrderController(DriverService driverService, OrderService orderService, CityService cityService, OrderWayPointService pointService) {
        this.driverService = driverService;
        this.orderService = orderService;
        this.cityService = cityService;
        this.pointService = pointService;
    }

    @GetMapping("")
    public String list(Model model, Principal principal) throws BusinessLogicException {
        DriverUserDTO driver = driverService.getDUDtoByEmail(principal.getName());
        if(driver.getAssignedOrder()!=null) {
            List<Integer> wayPoints = driver.getAssignedOrder().getWayPointsIds();
            model.addAttribute("pointList", wayPoints);
            List<Integer> driversOnOrder = driver.getAssignedOrder().getDriversOnOrderIds();
            model.addAttribute("driverList",driversOnOrder );
        }
        model.addAttribute("driver", driver);

        model.addAttribute("pointMap",orderService.getPointMap());
        model.addAttribute("cityMap",cityService.getCityMap());
        model.addAttribute("driverMap",driverService.getDriverMap());
        return "myOrder/myAccount";
    }

    @GetMapping("/editOrder/{id}")
    public ModelAndView editOrderShow(@PathVariable("id") int id){
        ModelAndView mav = new ModelAndView("orders/editOrder");
        OrderDTO orderDTO = orderService.getOrderById(id);
        mav.addObject("order", orderDTO);
        mav.addObject("waypoints", pointService.getAllWaypoints());
        return mav;
    }

    @RequestMapping(value="/editOrder/{orderId}/{pointId}", method = {RequestMethod.GET,RequestMethod.POST})
    public String setLoaded(@PathVariable("orderId") int orderId, @PathVariable("pointId") int pointId) {
        orderService.updateCargoStatus(orderId,pointId);
        return "orders/editOrder";
    }


}
