package edu.tsystems.javaschool.logapp.api.entities.dao;

import edu.tsystems.javaschool.logapp.api.entities.Driver;

import java.io.IOException;
import java.util.List;

public interface DriverDao {
    List<Driver> getAllDrivers();
    void saveDriver(Driver driver) throws IOException;
}
