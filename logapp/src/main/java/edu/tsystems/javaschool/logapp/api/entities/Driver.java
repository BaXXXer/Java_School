package edu.tsystems.javaschool.logapp.api.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "lg_drivers")
public class Driver {

    @Id
    @GeneratedValue
    @Column(name = "dr_id")
    private Integer driverId;

    @Column(name = "dr_firstName")
    private String driverFirstName;

    @Column(name="dr_surname")
    private String driverSurname;

    @Column(name="dr_PrivateNum")
    private Integer driverPrivateNum;

    @Column(name="dr_workingHours")
    private Integer driverWorkedHours;

    @Enumerated(EnumType.STRING)
    @Column(name="dr_status")
    private Status driverStatus;

    @Column(name="dr_cityId")
    private int driverCityId;


    @ManyToOne()
    @JoinColumn(name="driversTruckId_tr_id")
    private Truck driversTruckId;

    public Driver() {
    }




    public Truck getDriversTruckId() {
        return driversTruckId;
    }

    public void setDriversTruckId(Truck driversTruckId) {
        this.driversTruckId = driversTruckId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public enum Status {
        OFF,WORKING,DRIVING
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return driverCityId == driver.driverCityId &&
                Objects.equals(driverId, driver.driverId) &&
                Objects.equals(driverFirstName, driver.driverFirstName) &&
                Objects.equals(driverSurname, driver.driverSurname) &&
                Objects.equals(driverPrivateNum, driver.driverPrivateNum) &&
                Objects.equals(driverWorkedHours, driver.driverWorkedHours) &&
                driverStatus == driver.driverStatus &&
                Objects.equals(driversTruckId, driver.driversTruckId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId, driverFirstName, driverSurname, driverPrivateNum, driverWorkedHours, driverStatus, driverCityId, driversTruckId);
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

    public Integer getDriverPrivateNum() {
        return driverPrivateNum;
    }

    public void setDriverPrivateNum(Integer driverPrivateNum) {
        this.driverPrivateNum = driverPrivateNum;
    }

    public void setDriverWorkedHours(Integer driverWorkedHours) {
        this.driverWorkedHours = driverWorkedHours;
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
