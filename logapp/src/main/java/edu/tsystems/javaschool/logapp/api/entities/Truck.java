package edu.tsystems.javaschool.logapp.api.entities;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "lg_trucks")
public class Truck {

    @Id
    @GeneratedValue
    @Column(name = "tr_id")
    private Long truckId;

    @Column(name = "tr_regNum")
    @Pattern(regexp = "[A-Z]{2}\\d{5}")
    private String regNumber;

    @Column(name = "tr_workingHours")
    private int driverWorkingHours;

    @Column(name = "tr_capacityKg")
    private int capacityKg;

    @Column(name = "tr_condition")
//    @Enumerated(EnumType.STRING)
    private String condition;

//    @ManyToOne
//    @JoinColumn(name = "city_id")
    @Column(name = "tr_cityid")
    private int currentCityId;

    public Truck(@Pattern(regexp = "[A-Z]{2}\\d{5}") String regNumber, int driverWorkingHours, int capacityKg, String condition, int currentCityId) {
        this.regNumber = regNumber;
        this.driverWorkingHours = driverWorkingHours;
        this.capacityKg = capacityKg;
        this.condition = condition;
        this.currentCityId = currentCityId;
    }

    public Truck() {
    }

    public enum Condition {
        DEFECTIVE, OK
    }


    public long getTruckId() {
        return truckId;
    }

    public void setTruckId(long truckId) {
        this.truckId = truckId;
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

    public int getCapacityKg() {
        return capacityKg;
    }

    public void setCapacityKg(int capacityKg) {
        this.capacityKg = capacityKg;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getCurrentCityId() {
        return currentCityId;
    }

    public void setCurrentCityId(int currentCityId) {
        this.currentCityId = currentCityId;
    }


}
