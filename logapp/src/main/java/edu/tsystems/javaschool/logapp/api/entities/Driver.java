package edu.tsystems.javaschool.logapp.api.entities;

public class Driver {
    private String driverFirstName;
    private String driverSurName;
    private long driverPrivateNum;
    private int driverWorkedHours;
    private Status driverSstatus;
    private City driverCurrentCity;
    private Truck driverCurrentTruck;

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

    public Status getDriverSstatus() {
        return driverSstatus;
    }

    public void setDriverSstatus(Status driverSstatus) {
        this.driverSstatus = driverSstatus;
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
