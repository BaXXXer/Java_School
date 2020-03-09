package edu.tsystems.javaschool.logapp.api.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "lg_trucks")
public class Truck {

    @GeneratedValue
    @Id
    @Column(name = "tr_id")
    private Integer id;

    @Column(name = "tr_regNum")
    private String regNum;

    @OneToMany(mappedBy = "currentTruck",cascade = CascadeType.ALL)
    private List<Driver> drivers;

    @Column(name = "tr_orderId")
    private Integer orderId;

    @Column(name="tr_workingHours")
    private Integer workingHours;

    @Column(name = "tr_cityId")
    private Integer cityId;

    @Column(name = "tr_capacityTons")
    private Integer capacityTons;

    @Enumerated(EnumType.STRING)
    @Column(name = "tr_condition")
    private Condition condition;

    public enum Condition {
        OK, BROKEN ;

        private String code;

        Condition(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        Condition() {

        }

        public static Condition fromCode(String condition){
            for(Condition cond: Condition.values()){
                if(cond.getCode().equals(condition)){
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

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return Objects.equals(id, truck.id) &&
                Objects.equals(regNum, truck.regNum) &&
                Objects.equals(drivers, truck.drivers) &&
                Objects.equals(orderId, truck.orderId) &&
                Objects.equals(workingHours, truck.workingHours) &&
                Objects.equals(cityId, truck.cityId) &&
                Objects.equals(capacityTons, truck.capacityTons) &&
                condition == truck.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regNum, drivers, orderId, workingHours, cityId, capacityTons, condition);
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
