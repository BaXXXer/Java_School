package edu.tsystems.javaschool.logapp.api.entities.dao;

import edu.tsystems.javaschool.logapp.api.entities.Truck;

import java.io.IOException;
import java.util.List;

public interface TruckDao {

    List<Truck> getAllTrucks();
    void saveTruck(Truck truck) throws IOException;
    void updateTruck(Truck truck);
    void removeTruck(int id);
    Truck getTruckById(int id);

}
