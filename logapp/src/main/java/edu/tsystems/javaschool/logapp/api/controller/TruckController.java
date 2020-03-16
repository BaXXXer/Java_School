package edu.tsystems.javaschool.logapp.api.controller;


import edu.tsystems.javaschool.logapp.api.dto.TruckDTO;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import edu.tsystems.javaschool.logapp.api.service.CityService;
import edu.tsystems.javaschool.logapp.api.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class TruckController {

    private final TruckService truckService;
    private final CityService cityService;



    @Autowired
    public TruckController(TruckService truckService, CityService cityService) {
        this.truckService = truckService;
        this.cityService = cityService;
    }


    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String startPage(){
        return "index";
    }


    @RequestMapping(value = "/addTruck", method = RequestMethod.GET)
    public ModelAndView showForm() {
        ModelAndView model = new ModelAndView("trucks/addNewTruck");
        model.addObject("truckToAdd", new TruckDTO());
        model.addObject("enumCondition",Truck.Condition.values());
        model.addObject("cityList",cityService.getAllCities());

        return model;
    }

    //TODO: make error view + add redirect to success
    @RequestMapping(value = "/addTruck", method = RequestMethod.POST)
    public String submit(@ModelAttribute("truckToAdd") TruckDTO truck,
                         ModelMap model) throws IOException {

        model.addAttribute("regNum", truck);
        model.addAttribute("driverWorkingHours", truck);
        model.addAttribute("capacityTons", truck);
        model.addAttribute("condition", truck);
        model.addAttribute("currentCityId", truck);
        truckService.saveTruck(truck);

        return "trucks/addNewTruck";
    }


    @RequestMapping(value = "/allTrucks",method = RequestMethod.GET)
    public String getAllTrucks(Model model) {
        model.addAttribute("truck",new TruckDTO());
        model.addAttribute("cityService",cityService);
        model.addAttribute("cityMap",cityService.getCityMap());
        model.addAttribute("trucks",truckService.getAllTrucks());

        return "trucks/allTrucks";
    }

    @RequestMapping(value = "/removeTruck/{id}",method = RequestMethod.GET)
    public String removeTruck(@PathVariable("id") int id) {
        truckService.removeTruck(id);
        return "redirect:/allTrucks";
    }

    @RequestMapping(value="/editTruck/{id}",method = RequestMethod.GET)
    public ModelAndView editShowForm (@PathVariable("id") int id, Model model){
        ModelAndView mav = new ModelAndView("trucks/editTruck");
        mav.addObject("truckToEdit",truckService.getTruckById(id));
        mav.addObject("enumCondition", Truck.Condition.values());
        mav.addObject("cityList",cityService.getAllCities());
        mav.addObject("cityMap",cityService.getCityMap());

        return mav;
    }

    @RequestMapping(value = "/editTruck/{id}", method = RequestMethod.POST)
    public String submitEdit(@ModelAttribute("truckToEdit") TruckDTO truck,
                         ModelMap model) throws IOException {

        model.addAttribute("id",truck);
        model.addAttribute("regNumber", truck);
        model.addAttribute("driverWorkingHours", truck);
        model.addAttribute("capacityTons", truck);
        model.addAttribute("condition", truck);
        model.addAttribute("currentCityId", truck);
        truckService.updateTruck(truck);

        return "trucks/editTruck";
    }







}
