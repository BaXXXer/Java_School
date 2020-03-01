package edu.tsystems.javaschool.logapp.api.controller;


import edu.tsystems.javaschool.logapp.api.entities.Truck;
import edu.tsystems.javaschool.logapp.api.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TruckController {

    private final TruckService truckService;

    @Autowired
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }



    @RequestMapping(value = "/truck",method = RequestMethod.GET)
    public ModelAndView truck() {
        return new ModelAndView("truckInfo", "command", new Truck());
    }

    @RequestMapping(value = "/addTruck", method = {RequestMethod.GET,RequestMethod.POST})

    public String addTruck(@ModelAttribute("addTruck") Truck truck, ModelMap model) {
        model.addAttribute("regNumber", truck.getRegNumber());
        model.addAttribute("driverWorkingHours", truck.getDriverWorkingHours());
        model.addAttribute("capacity", truck.getCapacityKg());
        model.addAttribute("condition", truck.getCondition());
        model.addAttribute("currentCityId", truck.getCurrentCityId());

        return "truckEdit";


    }
}
