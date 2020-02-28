package org.tsystems.javaschool.logapp.api.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.tsystems.javaschool.logapp.api.entities.Truck;

@Controller
@RequestMapping(value = "/truck")
public class TruckController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView truck() {
        return new ModelAndView("truck", "command", new Truck());
    }

    @RequestMapping(value = "/addTruck", method = RequestMethod.POST)
    public String addTruck(@ModelAttribute("SpringWeb") Truck truck, ModelMap model) {
        model.addAttribute("regNumber", truck.getRegNumber());
        model.addAttribute("driverWorkingHours", truck.getDriverWorkingHours());
        model.addAttribute("capacity", truck.getCapacityKg());
        model.addAttribute("condition", truck.getCondition());
        model.addAttribute("currentCity", truck.getCurrentCity());

        return "result";


    }
}
