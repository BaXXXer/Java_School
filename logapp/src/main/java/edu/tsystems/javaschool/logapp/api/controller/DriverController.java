package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.service.CityService;
import edu.tsystems.javaschool.logapp.api.service.DriverService;
import edu.tsystems.javaschool.logapp.api.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;
    private final TruckService truckService;
    private final CityService cityService;


    @Autowired
    public DriverController(DriverService driverService, TruckService truckService, CityService cityService) {
        this.driverService = driverService;
        this.truckService = truckService;
        this.cityService = cityService;
    }

    @RequestMapping(value = "/addDriver", method = RequestMethod.GET)
    public ModelAndView showForm() {
        ModelAndView model = new ModelAndView("drivers/addNewDriver");
        model.addObject("driverToAdd", new DriverDTO());
        model.addObject("enumStatus", Driver.Status.values());
        model.addObject("truckList", truckService.getAllTrucks());
        model.addObject("cityList",cityService.getAllCities());

        return model;
    }

    //TODO: make error view + add redirect to success
    @RequestMapping(value = "/addDriver", method = RequestMethod.POST)
    public String submit(@ModelAttribute("driverToAdd") @Valid DriverDTO driverDTO,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "drivers/addNewDriver";
        }
        driverService.saveDriver(driverDTO);
        return "drivers/driverAddedSuccess";

    }

    @RequestMapping(value = "/allDrivers", method = RequestMethod.GET)
    public ModelAndView getAllDrivers() {
        ModelAndView mav = new ModelAndView("drivers/allDrivers");
        mav.addObject("drivers", driverService.getAllDrivers());
        mav.addObject("truckMap", truckService.getTruckMap());
        mav.addObject("cityMap", cityService.getCityMap());
        return mav;
    }

    @RequestMapping(value = "/removeDriver/{id}", method = RequestMethod.GET)
    public String removeDriver(@PathVariable("id") int id) {
        driverService.removeDriver(id);
        return "redirect: ../allDrivers";
    }

    @RequestMapping(value = "/editDriver/{id}", method = RequestMethod.GET)
    public ModelAndView editShowForm(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("drivers/editDriver");
        mav.addObject("driverToEdit", driverService.getDriverById(id));
        mav.addObject("enumStatus", Driver.Status.values());
        mav.addObject("cityList",cityService.getAllCitiesDTO());
        mav.addObject("truckList",truckService.getAllTrucks());
        mav.addObject("cityService",cityService);
        return mav;
    }

    @RequestMapping(value = "/editDriver/{driverId}", method = RequestMethod.POST)
    public String submitEdit(@ModelAttribute("driverToEdit") @Valid DriverDTO driver,
                             BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "drivers/editDriver";
        }
        driverService.updateDriver(driver);
        return "redirect: ../allDrivers";
    }


}
