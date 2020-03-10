package edu.tsystems.javaschool.logapp.api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lg_trucks")
public class Truck {

    @GeneratedValue
    @Id
    @Column(name = "tr_id")
    private Integer id;

    @Column(name = "tr_regNum")
    private String regNum;

    @OneToMany(mappedBy = "currentTruck", cascade = CascadeType.ALL)
    private List<Driver> drivers;

    @OneToOne(mappedBy = "truckOnOrder")
    private Order currentOrder;

    @Column(name = "tr_workingHours")
    private Integer workingHours;

    @Column(name = "tr_cityId")
    private Integer cityId;

    @Column(name = "tr_capacityTons")
    private Integer capacityTons;

    @Enumerated(EnumType.STRING)
    @Column(name = "tr_condition")
    private Condition condition;

    public enum Condition {
        OK, BROKEN;

        private String code;

        Condition(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        Condition() {

        }

        public static Condition fromCode(String condition) {
            for (Condition cond : Condition.values()) {
                if (cond.getCode().equals(condition)) {
                    return cond;
                }
            }
            throw new UnsupportedOperationException("The code " + condition + "is not supported!");
        }
    }

    public Truck() {
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public void setCapacityTons(Integer capacityTons) {
        this.capacityTons = capacityTons;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCapacityTons() {
        return capacityTons;
    }

    public void setCapacityTons(int capacityTons) {
        this.capacityTons = capacityTons;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "regNum='" + regNum + '\'' +
                '}';
    }

}
