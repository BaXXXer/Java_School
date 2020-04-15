package edu.tsystems.javaschool.logapp.api.dao;

import edu.tsystems.javaschool.logapp.api.entity.Driver;

import java.util.List;

public interface DriverDao {
    List<Driver> getAllDrivers();
    void saveDriver(Driver driver);
    void updateDriver(Driver driver);
    void removeDriver(int id);
    Driver getDriverById(int id);
    List<Driver> findFreeDriversInCity(int cityId, int maxHours);

    Long getDriversOnRestNumber();

    Long getAllDriversNumber();
}
