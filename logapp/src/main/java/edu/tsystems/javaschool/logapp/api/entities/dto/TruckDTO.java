package edu.tsystems.javaschool.logapp.api.entities.dto;

import edu.tsystems.javaschool.logapp.api.entities.City;
import edu.tsystems.javaschool.logapp.api.entities.Truck;

public class TruckDTO {
    private String regNumber;
    private String driverName;
    private Truck.Condition condition;
    private City currentCity;
    private String orderId;

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Truck.Condition getCondition() {
        return condition;
    }

    public void setCondition(Truck.Condition condition) {
        this.condition = condition;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


}
