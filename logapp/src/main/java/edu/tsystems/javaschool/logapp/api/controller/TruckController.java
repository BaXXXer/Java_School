package edu.tsystems.javaschool.logapp.api.controller;


import edu.tsystems.javaschool.logapp.api.dto.TruckDTO;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import edu.tsystems.javaschool.logapp.api.service.CityService;
import edu.tsystems.javaschool.logapp.api.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/trucks")
public class TruckController {

    private final TruckService truckService;
    private final CityService cityService;


    @Autowired
    public TruckController(TruckService truckService, CityService cityService) {
        this.truckService = truckService;
        this.cityService = cityService;
    }


//    @GetMapping
//    public String startPage() {
//        return "orders/OrdersIndexPage";
//    }


    @RequestMapping(value = "/addTruck", method = RequestMethod.GET)
    public ModelAndView showForm() {
        ModelAndView model = new ModelAndView("trucks/AddNewTruckPage");
        model.addObject("truckToAdd", new TruckDTO());
        model.addObject("enumCondition", Truck.Condition.values());
        model.addObject("cityList", cityService.getAllCities());

        return model;
    }




    @RequestMapping(value = "/addTruck", method = RequestMethod.POST)
    public String submit(@ModelAttribute("truckToAdd") @Valid TruckDTO truck,
                         BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "trucks/AddNewTruckPage";
        }

        truckService.saveTruck(truck);
        return "redirect: ./allTrucks";
    }


    @RequestMapping(value = "/allTrucks", method = RequestMethod.GET)
    public ModelAndView getAllTrucks(Model model) {
        ModelAndView mav = new ModelAndView("trucks/TrucksIndexPage");
        mav.addObject("trucks",truckService.getAllTrucks());
        mav.addObject("cityMap", cityService.getCityMap());

        return mav;
    }

    @RequestMapping(value = "/removeTruck/{id}", method = RequestMethod.GET)
    public String removeTruck(@PathVariable("id") int id) {
        truckService.removeTruck(id);
        return "redirect: ../allTrucks";
    }

    @RequestMapping(value = "/editTruck/{id}", method = RequestMethod.GET)
    public ModelAndView editShowForm(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("trucks/EditTruckPage");//was:editTruck.jsp
        mav.addObject("truckToEdit", truckService.getTruckById(id));
        mav.addObject("enumCondition", Truck.Condition.values());
        mav.addObject("cityService",cityService);
        mav.addObject("cityList", cityService.getAllCities());
        mav.addObject("cityMap", cityService.getCityMap());
        return mav;
    }

    @RequestMapping(value = "/editTruck/{id}", method = RequestMethod.POST)
    public String submitEdit(@ModelAttribute("truckToEdit") @Valid TruckDTO truck,
                             BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "trucks/EditTruckPage";
        }
        truckService.updateTruck(truck);
        return "redirect: ../allTrucks";
    }


}
