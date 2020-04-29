package edu.tsystems.javaschool.logapp.api.controller.rest;

import edu.tsystems.javaschool.logapp.api.dto.DriverStatusDTO;
import edu.tsystems.javaschool.logapp.api.service.DriverService;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/drivers/api")
@Path("/drivers/api")
public class DriverRestController {

    private final DriverService driverService;

    @Autowired
    public DriverRestController(DriverService driverService) {
        this.driverService = driverService;
    }

    @Path("/driverStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public @ResponseBody DriverStatusDTO getDriverStatus(){
        return driverService.getDriverStatus();
    }
}
