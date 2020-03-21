package edu.tsystems.javaschool.logapp.api.dto;

import edu.tsystems.javaschool.logapp.api.entity.Driver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.Null;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverDTO {

    @GeneratedValue
    private int driverId;
    private String driverFirstName;
    private String driverSurname;
    private Integer driverPrivateNum;
    private Integer driverWorkedHours;
    private Driver.Status driverStatus;
    private Integer driverCityId;
    private Integer driversTruckId;
    @Null
    private Integer userId;


}
