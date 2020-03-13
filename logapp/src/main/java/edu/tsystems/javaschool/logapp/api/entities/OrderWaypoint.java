package edu.tsystems.javaschool.logapp.api.entities;

import javax.persistence.*;

@Entity
@Table(name = "orders_waypoints")
public class OrderWaypoint {

    @Id
    @GeneratedValue
    @Column(name = "ow_id")
    private Integer id;

    @Column(name="ow_isCompleted")
    private boolean isCompleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "ow_operation")
    private Operation operationType;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="cargo_id")
    private Cargo cargo;

    @ManyToOne()
    @JoinColumn(name = "orders_or_id")
    private Order order;

    @Column(name = "waypoint_weight")
    private int waypointWeight;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;


    public enum Operation{
        LOAD,UNLOAD
    }

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

    public Operation getOperationType() {
        return operationType;
    }

    public void setOperationType(Operation operationType) {
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
