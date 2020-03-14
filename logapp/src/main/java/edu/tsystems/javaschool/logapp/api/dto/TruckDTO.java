package edu.tsystems.javaschool.logapp.api.dto;

import edu.tsystems.javaschool.logapp.api.entity.Truck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TruckDTO {
    @GeneratedValue
    private int id;

    private String regNumber;

    private int driverWorkingHours;


    private int capacityTons;

    private Truck.Condition condition;

    private Integer currentCityId;

}
