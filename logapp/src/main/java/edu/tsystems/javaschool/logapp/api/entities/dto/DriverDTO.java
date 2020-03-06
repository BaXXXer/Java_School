package edu.tsystems.javaschool.logapp.api.entities.dto;

import edu.tsystems.javaschool.logapp.api.entities.City;
import edu.tsystems.javaschool.logapp.api.entities.Driver;
import edu.tsystems.javaschool.logapp.api.entities.Truck;

public class DriverDTO {
    private String driverFirstName;
    private String driverSurname;
    private Long driverPrivateNum;
    private Integer driverWorkedHours;
    private Driver.Status driverStatus;
    private City driverCity;
    private Truck currentTruck;

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public void setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
    }

    public String getDriverSurname() {
        return driverSurname;
    }

    public void setDriverSurname(String driverSurname) {
        this.driverSurname = driverSurname;
    }

    public long getDriverPrivateNum() {
        return driverPrivateNum;
    }

    public void setDriverPrivateNum(long driverPrivateNum) {
        this.driverPrivateNum = driverPrivateNum;
    }

    public int getDriverWorkedHours() {
        return driverWorkedHours;
    }

    public void setDriverWorkedHours(int driverWorkedHours) {
        this.driverWorkedHours = driverWorkedHours;
    }

    public Driver.Status getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(Driver.Status driverStatus) {
        this.driverStatus = driverStatus;
    }

    public City getDriverCity() {
        return driverCity;
    }

    public void setDriverCity(City driverCity) {
        this.driverCity = driverCity;
    }

    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }



}
