package edu.tsystems.javaschool.logapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDTO {
    @GeneratedValue
    private Integer orderId;

    private boolean orderIsDone;

    private List<Integer> wayPointsIds;

    private Integer truckId;

    private List <Integer> driversOnOrderIds;

    private List<CargoWaypointDTO> points;



}
