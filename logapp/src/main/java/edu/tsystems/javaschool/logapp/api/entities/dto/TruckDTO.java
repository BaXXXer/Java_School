package edu.tsystems.javaschool.logapp.api.entities.dto;

import edu.tsystems.javaschool.logapp.api.entities.Truck;

import javax.persistence.GeneratedValue;

public class TruckDTO {
    @GeneratedValue
    private int id;

    private String regNumber;

    private int driverWorkingHours;


    private int capacityTons;

    private Truck.Condition condition;

    private Integer currentCityId;

    public TruckDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TruckDTO(String regNumber, int driverWorkingHours, int capacityTons, Truck.Condition condition, Integer currentCityId) {
        this.regNumber = regNumber;
        this.driverWorkingHours = driverWorkingHours;
        this.capacityTons = capacityTons;
        this.condition = condition;
        this.currentCityId = currentCityId;
    }


    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public int getDriverWorkingHours() {
        return driverWorkingHours;
    }

    public void setDriverWorkingHours(int driverWorkingHours) {
        this.driverWorkingHours = driverWorkingHours;
    }

    public int getCapacityTons() {
        return capacityTons;
    }

    public void setCapacityTons(int capacityTons) {
        this.capacityTons = capacityTons;
    }

    public Truck.Condition getCondition() {
        return condition;
    }

    public void setCondition(Truck.Condition condition) {
        this.condition = condition;
    }

    public Integer getCurrentCityId() {
        return currentCityId;
    }

    public void setCurrentCityId(Integer currentCityId) {
        this.currentCityId = currentCityId;
    }

    @Override
    public String toString() {
        return regNumber;
    }
}
