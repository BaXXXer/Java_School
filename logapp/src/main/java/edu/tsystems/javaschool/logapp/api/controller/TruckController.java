package edu.tsystems.javaschool.logapp.api.controller;


import edu.tsystems.javaschool.logapp.api.entities.Truck;
import edu.tsystems.javaschool.logapp.api.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class TruckController {

    private final TruckService truckService;



    @Autowired
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }


    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String startPage(){
        return "index";
    }


    @RequestMapping(value = "/addTruck", method = RequestMethod.GET)
    public ModelAndView showForm() {
        ModelAndView model = new ModelAndView("addNewTruck");
        model.addObject("truckToAdd", new Truck());
        model.addObject("enumCondition",Truck.Condition.values());
        return model;
    }

    //TODO: make error view + add redirect to success
    @RequestMapping(value = "/addTruck", method = RequestMethod.POST)
    public String submit(@ModelAttribute("truckToAdd")  Truck truck,
                         ModelMap model) throws IOException {
//        if (result.hasErrors()) {
//            return "addNewTruck";
//        }
        model.addAttribute("regNumber", truck);
        model.addAttribute("driverWorkingHours", truck);
        model.addAttribute("capacityKg", truck);
        model.addAttribute("condition", truck);
        model.addAttribute("currentCityId", truck);
        truckService.saveTruck(truck);

        return "addNewTruck";
    }


    @RequestMapping(value = "/allTrucks",method = RequestMethod.GET)
    public String getAllTrucks(Model model) {
        model.addAttribute("truck",new Truck());

        model.addAttribute("trucks",truckService.getAllTrucks());

        return "allTrucks";
    }
}
