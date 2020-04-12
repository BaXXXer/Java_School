package edu.tsystems.javaschool.logapp.api.controller.rest;

import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/drivers/api")
public class DriverRestController {

    private final DriverService driverService;

    @Autowired
    public DriverRestController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("")
    public @ResponseBody List<DriverDTO> apiList() {

        return driverService.getAllDrivers();
    }


}
