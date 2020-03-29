package edu.tsystems.javaschool.logapp.api.dto;

import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CargoWaypointDTO {
    private Integer id;
    private String name;
    private CargoDTO cargo;
    private CityDTO destCity;
    private TruckDTO assignedTruck;
    private boolean isCompleted;
    private OrderWaypoint.Operation operationType;


}
