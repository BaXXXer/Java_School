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

    private Integer cityId
            ;

    public TruckDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TruckDTO(String regNumber, int driverWorkingHours, int capacityTons, Truck.Condition condition, Integer cityId) {
        this.regNumber = regNumber;
        this.driverWorkingHours = driverWorkingHours;
        this.capacityTons = capacityTons;
        this.condition = condition;
        this.cityId = cityId;
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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return regNumber;
    }
}
