package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.entities.Cargo;
import edu.tsystems.javaschool.logapp.api.entities.OrderWaypoint;
import edu.tsystems.javaschool.logapp.api.entities.dto.OrderDTO;
import edu.tsystems.javaschool.logapp.api.exceptions.EntityNotFoundException;
import edu.tsystems.javaschool.logapp.api.services.DriverService;
import edu.tsystems.javaschool.logapp.api.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {

    private final DriverService driverService;
    private final TruckService truckService;
    private final OrderWaypoint waypoint;

    @Autowired
    public OrderController(DriverService driverService, TruckService truckService, OrderWaypoint waypoint) {
        this.driverService = driverService;
        this.truckService = truckService;
        this.waypoint = waypoint;
    }

    @RequestMapping(value = "/addOrder", method = RequestMethod.GET)
    public ModelAndView showForm() {
        ModelAndView model = new ModelAndView("orders/addNewOrder");
        model.addObject("orderToAdd", new OrderDTO());
        model.addObject("enumOperations", OrderWaypoint.Operation.values());
        model.addObject("enumCargoStatus", Cargo.Status.values());
        model.addObject("truckList", truckService.getAllTrucks());

        return model;
    }

    //TODO: make error view + add redirect to success
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public String submit(@ModelAttribute("orderToAdd") OrderDTO orderDto,
                         ModelMap model) throws EntityNotFoundException {


        model.addAttribute("orderId", orderDto);
        model.addAttribute("orderIsDone", orderDto);
        model.addAttribute("orderWayPointsIds", orderDto);
        model.addAttribute("truckId", orderDto);
        model.addAttribute("driversOnOrderIds", orderDto);

        return "orders/addNewOrder";
    }
}
