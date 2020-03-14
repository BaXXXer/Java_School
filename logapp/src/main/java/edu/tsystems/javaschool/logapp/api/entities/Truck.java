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
    private String regNumber;

    @OneToMany(mappedBy = "driversTruck", cascade = CascadeType.ALL)
    private List<Driver> drivers;

    @Column(name = "tr_workingHours")
    private Integer driverWorkingHours;

    @Column(name = "tr_cityId")
    private Integer currentCityId;

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


    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public Integer getDriverWorkingHours() {
        return driverWorkingHours;
    }

    public void setDriverWorkingHours(Integer driverWorkingHours) {
        this.driverWorkingHours = driverWorkingHours;
    }

    public Integer getCurrentCityId() {
        return currentCityId;
    }

    public void setCurrentCityId(Integer currentCityId) {
        this.currentCityId = currentCityId;
    }

    public void setCapacityTons(Integer capacityTons) {
        this.capacityTons = capacityTons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
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
                "regNum='" + regNumber +
                '}';
    }

}
