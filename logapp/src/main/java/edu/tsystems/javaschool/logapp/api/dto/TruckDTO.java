package edu.tsystems.javaschool.logapp.api.dto;

import edu.tsystems.javaschool.logapp.api.entity.Truck;
import lombok.*;

import javax.persistence.GeneratedValue;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TruckDTO {
    @GeneratedValue
    private int id;

    private String regNumber;

    private Integer driverWorkingHours;


    private int capacityTons;

    private Truck.Condition condition;

    private Integer currentCityId;

}
