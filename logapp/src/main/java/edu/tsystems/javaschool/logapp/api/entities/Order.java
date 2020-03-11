package edu.tsystems.javaschool.logapp.api.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="lg_orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name="or_id")
    private Long orderId;

    @Column(name="or_isDone")
    private boolean orderIsDone;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    @OrderBy(value = "waypointWeight asc")
    private List<OrderWaypoint> wayPoints;

    @OneToOne
    @JoinColumn(name = "truckOnOrder_tr_id")
    private Truck truckOnOrder;


    @ManyToMany
    @JoinTable(name="lg_orders_driversOnOrder",joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "driver_id"))
    private List <Driver> driversOnOrder;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public boolean isOrderIsDone() {
        return orderIsDone;
    }

    public void setOrderIsDone(boolean orderIsDone) {
        this.orderIsDone = orderIsDone;
    }

    public List<OrderWaypoint> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(List<OrderWaypoint> wayPoints) {
        this.wayPoints = wayPoints;
    }

    public Truck getTruckOnOrder() {
        return truckOnOrder;
    }

    public void setTruckOnOrder(Truck truckOnOrder) {
        this.truckOnOrder = truckOnOrder;
    }

    public List<Driver> getDriversOnOrder() {
        return driversOnOrder;
    }

    public void setDriversOnOrder(List<Driver> driversOnOrder) {
        this.driversOnOrder = driversOnOrder;
    }
}
