package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.entities.Driver;
import edu.tsystems.javaschool.logapp.api.entities.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.entities.dto.TruckDTO;
import edu.tsystems.javaschool.logapp.api.services.DriverService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class DriverController {

    private final DriverService driverService;
    private final TruckService truckService;


    @Autowired
    public DriverController(DriverService driverService, TruckService truckService) {
        this.driverService = driverService;
        this.truckService = truckService;
    }

    @RequestMapping(value = "/addDriver", method = RequestMethod.GET)
    public ModelAndView showForm() {
        ModelAndView model = new ModelAndView("drivers/addNewDriver");
        model.addObject("driverToAdd", new DriverDTO());
        model.addObject("enumStatus", Driver.Status.values());
        model.addObject("truckList", getAllTruckRegNums());

        return model;
    }

    //TODO: make error view + add redirect to success
    @RequestMapping(value = "/addDriver", method = RequestMethod.POST)
    public String submit(@ModelAttribute("driverToAdd") DriverDTO driverDTO,
                         ModelMap model) throws IOException {


        model.addAttribute("driverFirstName", driverDTO);
        model.addAttribute("driverSurname", driverDTO);
        model.addAttribute("driverPrivateNum", driverDTO);
        model.addAttribute("driverWorkedHours", driverDTO);
        model.addAttribute("driverStatus", driverDTO);
        model.addAttribute("driverCity", driverDTO);
        model.addAttribute("currentTruck", driverDTO);
        driverService.saveDriver(driverDTO);
        return "drivers/addNewDriver";
    }

    @RequestMapping(value = "/allDrivers",method = RequestMethod.GET)
    public String getAllDrivers(Model model) {
        model.addAttribute("driver",new DriverDTO());

        model.addAttribute("drivers",driverService.getAllDrivers());

        return "drivers/allDrivers";
    }

        private List<String> getAllTruckRegNums(){
        List<String> regNumbers = new ArrayList();
        for(TruckDTO dto : truckService.getAllTrucks()){
            regNumbers.add(dto.getRegNumber());
        }
        return regNumbers;

    }








}
