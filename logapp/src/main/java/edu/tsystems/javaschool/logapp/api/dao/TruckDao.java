package edu.tsystems.javaschool.logapp.api.dao;

import edu.tsystems.javaschool.logapp.api.entities.Truck;

import java.io.IOException;
import java.util.List;

public interface TruckDao {

    List<Truck> getAllTrucks();
    void saveTruck(Truck truck) throws IOException;
}
