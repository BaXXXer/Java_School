package edu.tsystems.javaschool.logapp.api.entities;

public class Driver {
    private Long driverId;
    private String driverFirstName;
    private String driverSurName;
    private long driverPrivateNum;
    private int driverWorkedHours;
    private Status driverStatus;
    private City driverCurrentCity;
    private Truck driverCurrentTruck;

    public Driver(String driverFirstName, String driverSurName, long driverPrivateNum,
                  int driverWorkedHours, Status driverStatus, City driverCurrentCity, Truck driverCurrentTruck) {
        this.driverFirstName = driverFirstName;
        this.driverSurName = driverSurName;
        this.driverPrivateNum = driverPrivateNum;
        this.driverWorkedHours = driverWorkedHours;
        this.driverStatus = driverStatus;
        this.driverCurrentCity = driverCurrentCity;
        this.driverCurrentTruck = driverCurrentTruck;
    }

    public Driver() {
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    private enum Status {
        OFF,WORKING,DRIVING
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public void setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
    }

    public String getDriverSurName() {
        return driverSurName;
    }

    public void setDriverSurName(String driverSurName) {
        this.driverSurName = driverSurName;
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

    public Status getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(Status driverStatus) {
        this.driverStatus = driverStatus;
    }

    public City getDriverCurrentCity() {
        return driverCurrentCity;
    }

    public void setDriverCurrentCity(City driverCurrentCity) {
        this.driverCurrentCity = driverCurrentCity;
    }

    public Truck getDriverCurrentTruck() {
        return driverCurrentTruck;
    }

    public void setDriverCurrentTruck(Truck driverCurrentTruck) {
        this.driverCurrentTruck = driverCurrentTruck;
    }
}
