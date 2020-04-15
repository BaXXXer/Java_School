package edu.tsystems.javaschool.logapp.api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DriverStatusDTO {
    Long totalDrivers;
    Long driversOnRest;
}
