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

import javax.persistence.EntityManager;
import java.io.IOException;

@Controller
public class TruckController {

    private final TruckService truckService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }



    @RequestMapping(value = "/truck",method = RequestMethod.GET)
    public ModelAndView truck() {
        return new ModelAndView("truckInfo", "command", new Truck());
    }

    @RequestMapping(value = "/addTruck", method = {RequestMethod.GET,RequestMethod.POST})
    public String addTruckPost(@ModelAttribute("addTruck") Truck truck, ModelMap model) throws IOException {
//        model.addAttribute("regNumber", truck);
//        model.addAttribute("driverWorkingHours", truck);
//        model.addAttribute("capacityKg", truck);
//        model.addAttribute("condition", truck);
//        model.addAttribute("currentCityId", truck);
        truck.setRegNumber("AA00100");
        truck.setCapacityKg(100);
        truck.setCondition("OK");
        truck.setCurrentCityId(1);
        truck.setDriverWorkingHours(10);


        truckService.saveTruck(truck);

        return "addNewTruck";
    }
    @RequestMapping(value = "/allTrucks",method = RequestMethod.GET)
    public String getAllTrucks(ModelMap model) {
        model.addAttribute("trucks",truckService.getAllTrucks());
        return "allTrucks";
    }
}
