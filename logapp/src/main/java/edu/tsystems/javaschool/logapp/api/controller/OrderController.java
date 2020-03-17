package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.dto.OrderDTO;
import edu.tsystems.javaschool.logapp.api.dto.OrderStatusDTO;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import edu.tsystems.javaschool.logapp.api.exception.InvalidStateException;
import edu.tsystems.javaschool.logapp.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final CityService cityService;


    @Autowired
    public OrderController(DriverService driverService, TruckService truckService, OrderWayPointService pointService, OrderService orderService, HttpServletRequest request, CityService cityService) {
        this.driverService = driverService;
        this.truckService = truckService;
        this.pointService = pointService;
        this.orderService = orderService;
        this.request = request;
        this.cityService = cityService;
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
        model.addAttribute("pointService", pointService);
        model.addAttribute("driverService", driverService);
        model.addAttribute("pointMap",orderService.getPointMap());


        return "orders/allOrdersTables";
    }

    @RequestMapping(value = "/orderStatus",method = RequestMethod.GET)
    public String getOrderStatus(Model model) {
        model.addAttribute("order",new OrderStatusDTO());
        model.addAttribute("orders",orderService.getOrderStatus());

        return "orders/orderStatus";
    }

    @RequestMapping(value = "/readyToGoTrucks/{id}",method = RequestMethod.GET)
    public ModelAndView getReadyToGoTrucks(@PathVariable("id") int id, Model model) {
        ModelAndView mav = new ModelAndView("orders/readyToGoTrucks");
        mav.addObject("truckList",orderService.getReadyToGoTrucks(orderService.getOrderById(id)));
        mav.addObject("cityMap",cityService.getCityMap());
        return mav;
    }

    @RequestMapping(value = "/removeOrder/{id}", method = RequestMethod.GET)
    public String removeDriver(@PathVariable("id") int id) {
        orderService.removeOrder(id);
        return "redirect:/allOrders";
    }


}
