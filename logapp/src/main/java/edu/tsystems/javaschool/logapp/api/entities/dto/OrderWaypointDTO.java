package edu.tsystems.javaschool.logapp.api.entities.dto;

import edu.tsystems.javaschool.logapp.api.entities.Cargo;
import edu.tsystems.javaschool.logapp.api.entities.Order;
import edu.tsystems.javaschool.logapp.api.entities.OrderWaypoint;

import javax.persistence.GeneratedValue;

public class OrderWaypointDTO {

    @GeneratedValue
    private Integer id;


    private boolean isCompleted;

    private OrderWaypoint.Operation operationType;


    private Cargo cargo;


    private Order order;


    private int waypointWeight;

    private Integer cityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public OrderWaypoint.Operation getOperationType() {
        return operationType;
    }

    public void setOperationType(OrderWaypoint.Operation operationType) {
        this.operationType = operationType;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getWaypointWeight() {
        return waypointWeight;
    }

    public void setWaypointWeight(int waypointWeight) {
        this.waypointWeight = waypointWeight;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
}
