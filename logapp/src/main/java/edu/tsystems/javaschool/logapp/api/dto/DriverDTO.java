package edu.tsystems.javaschool.logapp.api.dto;

import edu.tsystems.javaschool.logapp.api.entity.Driver;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DriverDTO {

    @GeneratedValue
    private int driverId;

    @NotNull(message = "Should not be empty")
    @Size(min = 1, max = 256, message = "Name should be 1-256 chars long.")
    private String driverFirstName;

    @NotNull(message = "Should not be empty")
    @Size(min = 1, max = 256, message = "Name should be 1-256 chars long.")
    private String driverSurname;

    @NotNull(message = "Should not be empty")
    @Min(10000000)
    @Max(99999999)
    private Integer driverPrivateNum;

    @NotNull(message = "Should not be empty")
    @Min(0)
    @Max(176)
    private Integer driverWorkedHours;



    private Driver.Status driverStatus;

    @NotNull(message = "Should not be empty")
    private Integer driverCityId;

    private Integer driversTruckId;

    @Null
    private Integer userId;


}
