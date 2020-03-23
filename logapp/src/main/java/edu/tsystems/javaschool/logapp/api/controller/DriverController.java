package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.exception.EntityNotFoundException;
import edu.tsystems.javaschool.logapp.api.service.CityService;
import edu.tsystems.javaschool.logapp.api.service.DriverService;
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
    public String submit(@ModelAttribute("driverToAdd") DriverDTO driverDTO,
                         ModelMap model) throws IOException, EntityNotFoundException {


        model.addAttribute("driverFirstName", driverDTO);
        model.addAttribute("driverSurname", driverDTO);
        model.addAttribute("driverPrivateNum", driverDTO);
        model.addAttribute("driverWorkedHours", driverDTO);
        model.addAttribute("driverStatus", driverDTO);
        model.addAttribute("driverCityId", driverDTO);
        model.addAttribute("driversTruckId", driverDTO);
        driverService.saveDriver(driverDTO);
        return "drivers/addNewDriver";
    }

    @RequestMapping(value = "/allDrivers", method = RequestMethod.GET)
    public String getAllDrivers(Model model) {
        model.addAttribute("driver", new DriverDTO());
        model.addAttribute("drivers", driverService.getAllDrivers());
        model.addAttribute("trucks", truckService.getAllTrucks());
        model.addAttribute("cityMap",cityService.getCityMap());
        model.addAttribute("truckMap",truckService.getTruckMap());

        return "drivers/allDrivers";
    }

    @RequestMapping(value = "/removeDriver/{id}", method = RequestMethod.GET)
    public String removeDriver(@PathVariable("id") int id) {
        driverService.removeDriver(id);
        return "redirect:drivers/allDrivers";
    }

    @RequestMapping(value = "/editDriver/{id}", method = RequestMethod.GET)
    public ModelAndView editShowForm(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("drivers/editDriver");
        mav.addObject("driverToEdit", driverService.getDriverById(id));
        mav.addObject("enumStatus", Driver.Status.values());
        mav.addObject("cityList",cityService.getAllCities());
        mav.addObject("truckList",truckService.getAllTrucks());
        return mav;
    }

    @RequestMapping(value = "/editDriver/{driverId}", method = RequestMethod.POST)
    public String submitEdit(@ModelAttribute("driverToEdit") DriverDTO driver,
                             ModelMap model) {
        model.addAttribute("id", driver);
        model.addAttribute("driverFirstName",driver);
        model.addAttribute("driverSurname",driver);
        model.addAttribute("driverPrivateNum",driver);
        model.addAttribute("driverWorkedHours",driver);
        model.addAttribute("driverStatus",driver);
        model.addAttribute("driversTruckId",driver);
        model.addAttribute("driverCityId",driver);
        driverService.updateDriver(driver);

        return "drivers/editDriver";
    }


}
