package edu.tsystems.javaschool.logapp.api.dto;

import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import edu.tsystems.javaschool.logapp.api.entity.Order;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderWaypointDTO {

    @GeneratedValue
    private Integer id;


    private boolean isCompleted;

    private OrderWaypoint.Operation operationType;


    private Cargo cargo;


    private Order order;


    private int waypointWeight;

    private Integer cityId;


}
