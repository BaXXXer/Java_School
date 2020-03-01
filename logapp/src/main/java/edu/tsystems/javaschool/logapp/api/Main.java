package edu.tsystems.javaschool.logapp.api;

import edu.tsystems.javaschool.logapp.api.config.WebMvcConfig;
import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dao.TruckDaoImpl;
import edu.tsystems.javaschool.logapp.api.entities.City;
import edu.tsystems.javaschool.logapp.api.entities.Truck;
import edu.tsystems.javaschool.logapp.api.services.TruckService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(WebMvcConfig.class);
        TruckService service = context.getBean(TruckService.class);



        Truck truck = new Truck("AAA01234",10,1000, Truck.Condition.OK,2);
        try {
            service.saveTruck(truck);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(service.getAllTrucks());

    }
}
