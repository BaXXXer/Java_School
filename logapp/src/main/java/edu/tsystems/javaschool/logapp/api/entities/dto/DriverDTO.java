package edu.tsystems.javaschool.logapp.api.entities.dto;

import edu.tsystems.javaschool.logapp.api.entities.Driver;

import javax.persistence.GeneratedValue;

public class DriverDTO {
    @GeneratedValue
    private int driverId;
    private String driverFirstName;
    private String driverSurname;
    private Integer driverPrivateNum;
    private Integer driverWorkedHours;
    private Driver.Status driverStatus;
    private Integer driverCityId;
    private Integer driversTruckId;

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public Integer getDriverPrivateNum() {
        return driverPrivateNum;
    }

    public void setDriverPrivateNum(Integer driverPrivateNum) {
        this.driverPrivateNum = driverPrivateNum;
    }

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

    public void setDriverWorkedHours(Integer driverWorkedHours) {
        this.driverWorkedHours = driverWorkedHours;
    }

    public Integer getDriverCityId() {
        return driverCityId;
    }

    public void setDriverCityId(Integer driverCityId) {
        this.driverCityId = driverCityId;
    }

    public Integer getDriversTruckId() {
        return driversTruckId;
    }

    public void setDriversTruckId(Integer driversTruckId) {
        this.driversTruckId = driversTruckId;
    }
}
