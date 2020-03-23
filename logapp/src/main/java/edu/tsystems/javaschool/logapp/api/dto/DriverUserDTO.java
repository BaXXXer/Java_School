package edu.tsystems.javaschool.logapp.api.dto;

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
    public enum Status {
        CARGO_HANDLING,REST,DRIVING,CO_DRIVER
    }

    private Status driverStatus;
    private String truckRegNumber;
    private OrderDTO assignedOrder;
}
