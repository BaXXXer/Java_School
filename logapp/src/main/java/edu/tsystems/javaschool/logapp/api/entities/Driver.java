package edu.tsystems.javaschool.logapp.api.entities;

import javax.persistence.*;

@Entity
@Table(name = "lg_drivers")
public class Driver {

    @Id
    @GeneratedValue
    @Column(name = "dr_id")
    private Long driverId;

    @Column(name = "dr_firstName")
    private String driverFirstName;

    @Column(name="dr_surname")
    private String driverSurname;

    @Column(name="dr_PrivateNum")
    private Long driverPrivateNum;

    @Column(name="dr_workingHours")
    private Integer driverWorkedHours;

    @Enumerated(EnumType.STRING)
    @Column(name="dr_status")
    private Status driverStatus;

    @Column(name="dr_cityId")
    private int driverCityId;


    @ManyToOne()
    @JoinColumn(name="dr_id",insertable = false, updatable = false)
    private Truck currentTruck;

    public Driver() {
    }

    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck currentTruck) {
        this.currentTruck = currentTruck;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public enum Status {
        OFF,WORKING,DRIVING
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public void setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
    }

    public String getDriverSurname() {
        return driverSurname;
    }

    public void setDriverSurname(String driverSurname) {
        this.driverSurname = driverSurname;
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

    public int getDriverCityId() {
        return driverCityId;
    }

    public void setDriverCityId(int driverCityId) {
        this.driverCityId = driverCityId;
    }


}
