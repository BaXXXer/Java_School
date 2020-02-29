package edu.tsystems.javaschool.logapp.api;

import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dao.TruckDaoImpl;
import edu.tsystems.javaschool.logapp.api.entities.City;
import edu.tsystems.javaschool.logapp.api.entities.Truck;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        TruckDao dao = new TruckDaoImpl();
        Truck truck = new Truck("AAA01234",10,1000, Truck.Condition.OK,2);
        try {
            dao.saveTruck(truck);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dao.getAllTrucks());

    }
}
