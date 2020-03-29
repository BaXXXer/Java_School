package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.dto.OrderDTO;
import edu.tsystems.javaschool.logapp.api.dto.OrderStatusDTO;
import edu.tsystems.javaschool.logapp.api.exception.InvalidStateException;
import edu.tsystems.javaschool.logapp.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final DriverService driverService;
    private final TruckService truckService;
    private final OrderWayPointService pointService;
    private final OrderService orderService;
    private final CargoService cargoService;
    private final CityService cityService;


    @Autowired
    public OrderController(DriverService driverService, TruckService truckService, OrderWayPointService pointService, OrderService orderService, CargoService cargoService, CityService cityService) {
        this.driverService = driverService;
        this.truckService = truckService;
        this.pointService = pointService;
        this.orderService = orderService;
        this.cargoService = cargoService;
        this.cityService = cityService;
    }

//    @RequestMapping(value = "/addOrder", method = RequestMethod.GET)
//    public ModelAndView showForm() {
//        ModelAndView model = new ModelAndView("orders/addNewOrder");
//        model.addObject("orderToAdd", new OrderDTO());
//        model.addObject("enumOperations", OrderWaypoint.Operation.values());
//        model.addObject("enumCargoStatus", Cargo.Status.values());
//        model.addObject("truckList", truckService.getAllTrucks());
//        model.addObject("pointList", pointService.getAllWaypoints());
////        model.addObject("driverList", driverService.getAllDrivers());
//        return model;
//    }

    //TODO: make error view + add redirect to success
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public String submit(@ModelAttribute("orderToAdd") OrderDTO orderDto,
                         ModelMap model) throws InvalidStateException {
        orderDto.setOrderIsDone(false);
        orderService.saveOrder(orderDto);
        return "index";

//        if(request.getParameter("orderIsDone").equals("Yes")){
//            orderDto.setOrderIsDone(true);
//        }else{
//            orderDto.setOrderIsDone(false);
//        }
//        model.addAttribute("orderId", orderDto);
//        orderDto.setWayPointsIds(orderService.getListOfPointIds());
//        model.addAttribute("truckId", orderDto);
//        orderDto.setDriversOnOrderIds(orderService.getListOfDriverIds());

    }

    @RequestMapping(value = "/allOrders", method = RequestMethod.GET)
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("pointMap", orderService.getPointMap());
        model.addAttribute("driverService", driverService);
        return "orders/allOrdersTables";
    }

    @RequestMapping(value = "/notAssignedCargoes/{id}", method = RequestMethod.GET)
    public ModelAndView getNotAssignedCargoes(@PathVariable(name = "id") int id ) {
        ModelAndView mav = new ModelAndView("orders/notAssignedCargoes");
        mav.addObject("order",orderService.getOrderById(id));
        mav.addObject("cargoes", pointService.getNotAssignedCargoes());
        mav.addObject("points", pointService.getAllWaypoints());
        mav.addObject("cityList",cityService.getAllCitiesDTO());
        return mav;
    }

    @RequestMapping(value = "/notAssignedCargoes/{orderId}/{cargoId}", method = {RequestMethod.POST})
    public String setCargoAndPointToOrder(@RequestBody MultiValueMap<String, String> formData,
                                          @PathVariable(name = "orderId") int id,
                                          @PathVariable(name = "cargoId") int cargoId) {

        orderService.getParamsAndSetToOrder(formData,cargoId,id);

        return "redirect: .";
    }




    @RequestMapping(value = "/orderStatus", method = RequestMethod.GET)
    public String getOrderStatus(Model model) {
        model.addAttribute("order", new OrderStatusDTO());
        model.addAttribute("orders", orderService.getOrderStatus());

        return "orders/orderStatus";
    }

    @RequestMapping(value = "/readyToGoTrucks/{id}", method = RequestMethod.GET)
    public ModelAndView getReadyToGoTrucks(@PathVariable("id") int id, Model model) {
        ModelAndView mav = new ModelAndView("orders/readyToGoTrucks");
        mav.addObject("truckList", orderService.getReadyToGoTrucks(orderService.getOrderById(id)));
        mav.addObject("order", orderService.getOrderById(id));
        mav.addObject("cityMap", cityService.getCityMap());
        return mav;
    }

    @RequestMapping(value = "/readyToGoTrucks/{orderId}/{truckId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String submitTruckAssign(@PathVariable("orderId") int orderId,
                               ModelMap model, @PathVariable("truckId") int truckId) {

        orderService.assignTruck(truckService.getTruckById(truckId),orderService.getOrderById(orderId));
        return "orders/readyForTripDrivers";
    }





    @RequestMapping(value = "/readyForTripDrivers/{id}", method = RequestMethod.GET)
    public ModelAndView getReadyForTripDrivers(@PathVariable("id") int id, Model model) {
        ModelAndView mav = new ModelAndView("orders/readyForTripDrivers");
        mav.addObject("drivers", orderService.findDriversForTrip(orderService.getOrderById(id)));
        mav.addObject("cityMap", cityService.getCityMap());
        mav.addObject("truckMap", truckService.getTruckMap());
        mav.addObject("order", orderService.getOrderById(id));
        return mav;
    }

    @RequestMapping(value = "/readyForTripDrivers/{orderId}/{driverId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String submitAssign(@PathVariable("orderId") int orderId,
                               ModelMap model, @PathVariable("driverId") int driverId) {

        orderService.assignDriver(driverService.getDriverById(driverId), orderService.getOrderById(orderId));
        return "orders/readyForTripDrivers";
    }


}
