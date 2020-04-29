package edu.tsystems.javaschool.logapp.api.config;

import edu.tsystems.javaschool.logapp.api.dto.DriverStatusDTO;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.springframework.context.annotation.Configuration;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/resources")
//@Configuration
public class RestConfig extends Application {
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(DriverStatusDTO.class));
    }

}
