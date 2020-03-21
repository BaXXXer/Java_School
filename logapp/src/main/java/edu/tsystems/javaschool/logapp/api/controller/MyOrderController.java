package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.dto.DriverUserDTO;
import edu.tsystems.javaschool.logapp.api.exception.BusinessLogicException;
import edu.tsystems.javaschool.logapp.api.service.CityService;
import edu.tsystems.javaschool.logapp.api.service.DriverService;
import edu.tsystems.javaschool.logapp.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/myOrder")
public class MyOrderController {


    private final DriverService driverService;
    private final OrderService orderService;
    private final CityService cityService;

    @Autowired
    public MyOrderController(DriverService driverService, OrderService orderService, CityService cityService) {
        this.driverService = driverService;
        this.orderService = orderService;
        this.cityService = cityService;
    }

    @GetMapping("")
    public String list(Model model, Principal principal) throws BusinessLogicException {
        DriverUserDTO driver = driverService.getDUDtoByEmail(principal.getName());
        List<Integer> wayPoints = driver.getAssignedOrder().getWayPointsIds();
        List<Integer> driversOnOrder = driver.getAssignedOrder().getDriversOnOrderIds();


        model.addAttribute("driver", driver);
        model.addAttribute("pointList", wayPoints);
        model.addAttribute("driverList",driversOnOrder );
        model.addAttribute("pointMap",orderService.getPointMap());
        model.addAttribute("cityMap",cityService.getCityMap());
        model.addAttribute("driverMap",driverService.getDriverMap());
        return "myOrder/myAccount";
    }





}
