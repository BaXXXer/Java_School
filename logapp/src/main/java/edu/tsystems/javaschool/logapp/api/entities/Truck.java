package edu.tsystems.javaschool.logapp.api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lg_trucks")
public class Truck {

    @GeneratedValue
    @Id
    @Column(name = "tr_id")
    private Long id;

    @Column(name = "tr_regNum")
    private String regNum;

//    @Column(name = "tr_drivers")
    @OneToMany()
    private List<Driver> drivers;

    @Column(name = "tr_orderId")
    private Long orderId;

    @Column(name="tr_workingHours")
    private Integer workingHours;

    @Column(name = "tr_cityId")
    private Long cityId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
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
