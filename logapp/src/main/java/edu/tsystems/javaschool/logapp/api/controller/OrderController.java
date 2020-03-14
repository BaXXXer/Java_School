package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.dto.OrderDTO;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import edu.tsystems.javaschool.logapp.api.exception.InvalidStateException;
import edu.tsystems.javaschool.logapp.api.service.DriverService;
import edu.tsystems.javaschool.logapp.api.service.OrderService;
import edu.tsystems.javaschool.logapp.api.service.OrderWayPointService;
import edu.tsystems.javaschool.logapp.api.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

    private final DriverService driverService;
    private final TruckService truckService;
    private final OrderWayPointService pointService;
    private final OrderService  orderService;
    private final HttpServletRequest request;


    @Autowired
    public OrderController(DriverService driverService, TruckService truckService, OrderWayPointService pointService, OrderService orderService, HttpServletRequest request) {
        this.driverService = driverService;
        this.truckService = truckService;
        this.pointService = pointService;
        this.orderService = orderService;
        this.request = request;
    }

    @RequestMapping(value = "/addOrder", method = RequestMethod.GET)
    public ModelAndView showForm() {
        ModelAndView model = new ModelAndView("orders/addNewOrder");
        model.addObject("orderToAdd", new OrderDTO());
        model.addObject("enumOperations", OrderWaypoint.Operation.values());
        model.addObject("enumCargoStatus", Cargo.Status.values());
        model.addObject("truckList", truckService.getAllTrucks());
        model.addObject("pointList", pointService.getAllWaypoints());
        model.addObject("driverList", driverService.getAllDrivers());
        return model;
    }

    //TODO: make error view + add redirect to success
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public String submit(@ModelAttribute("orderToAdd") OrderDTO orderDto,
                         ModelMap model) throws InvalidStateException {
        if(request.getParameter("orderIsDone").equals("Yes")){
            orderDto.setOrderIsDone(true);
        }else{
            orderDto.setOrderIsDone(false);
        }
        model.addAttribute("orderId", orderDto);
        orderDto.setWayPointsIds(orderService.getListOfPointIds());
        model.addAttribute("truckId", orderDto);
        orderDto.setDriversOnOrderIds(orderService.getListOfDriverIds());
        orderService.saveOrder(orderDto);
        return "orders/addNewOrder";
    }

    @RequestMapping(value = "/allOrders",method = RequestMethod.GET)
    public String getAllOrders(Model model) {
        model.addAttribute("order",new OrderDTO());

        model.addAttribute("orders",orderService.getAllOrders());

        return "orders/allOrders";
    }


}
