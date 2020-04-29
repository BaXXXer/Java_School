package edu.tsystems.javaschool.logapp.api.controller.rest;

import edu.tsystems.javaschool.logapp.api.dto.BoardOrderStatusDTO;
import edu.tsystems.javaschool.logapp.api.dto.DriverStatusDTO;
import edu.tsystems.javaschool.logapp.api.dto.TruckStatusDTO;
import edu.tsystems.javaschool.logapp.api.service.DriverService;
import edu.tsystems.javaschool.logapp.api.service.OrderService;
import edu.tsystems.javaschool.logapp.api.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/rest")
//@Path("/drivers/api")
public class BasicRestController {

    private final DriverService driverService;
    private final TruckService truckService;
    private final OrderService orderService;

    @Autowired
    public BasicRestController(DriverService driverService, TruckService truckService, OrderService orderService) {
        this.driverService = driverService;
        this.truckService = truckService;
        this.orderService = orderService;
    }

    @GetMapping("/driverStatus")
    public @ResponseBody DriverStatusDTO getDriverStatus(){
        return driverService.getDriverStatus();
    }

    @GetMapping("/truckStatus")
    public @ResponseBody TruckStatusDTO getTruckStatus(){
        return truckService.getTruckStatus();
    }

    @GetMapping("/orderStatus")
    public @ResponseBody List<BoardOrderStatusDTO> getOrderStatus(){
        return orderService.getLastOrdersStatus();
    }
}
