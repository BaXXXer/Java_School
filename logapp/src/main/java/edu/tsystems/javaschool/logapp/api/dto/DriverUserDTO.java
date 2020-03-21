package edu.tsystems.javaschool.logapp.api.dto;

import edu.tsystems.javaschool.logapp.api.entity.Driver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverUserDTO {

    @GeneratedValue
    private int driverId;
    private String driverFirstName;
    private String driverSurname;
    private Integer driverPrivateNum;
    private Driver.Status driverStatus;
    private String truckRegNumber;
    private OrderDTO assignedOrder;
}
