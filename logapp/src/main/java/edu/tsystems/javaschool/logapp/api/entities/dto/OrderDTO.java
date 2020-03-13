package edu.tsystems.javaschool.logapp.api.entities.dto;

import javax.persistence.GeneratedValue;
import java.util.List;

public class OrderDTO {
    @GeneratedValue
    private Integer orderId;

    private boolean orderIsDone;

    private List<Integer> wayPointsIds;

    private Integer truckId;

    private List <Integer> driversOnOrderIds;


    public Integer getOrderId() {
        return orderId;
    }



    public boolean isOrderIsDone() {
        return orderIsDone;
    }

    public void setOrderIsDone(boolean orderIsDone) {
        this.orderIsDone = orderIsDone;
    }

    public List<Integer> getWayPointsIds() {
        return wayPointsIds;
    }

    public void setWayPointsIds(List<Integer> wayPointsIds) {
        this.wayPointsIds = wayPointsIds;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getTruckId() {
        return truckId;
    }

    public void setTruckId(Integer truckId) {
        this.truckId = truckId;
    }

    public List<Integer> getDriversOnOrderIds() {
        return driversOnOrderIds;
    }

    public void setDriversOnOrderIds(List<Integer> driversOnOrderIds) {
        this.driversOnOrderIds = driversOnOrderIds;
    }
}
