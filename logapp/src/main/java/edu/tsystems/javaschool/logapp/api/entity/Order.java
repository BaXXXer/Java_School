package edu.tsystems.javaschool.logapp.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="lg_orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name="order_seq",sequenceName = "order_seq",allocationSize = 1)
    @Column(name="or_id")
    @Getter
    @Setter
    private Integer orderId;

    @Column(name="or_isDone")
    @Getter
    @Setter
    private boolean orderIsDone;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,orphanRemoval = true)
    @Getter
    @Setter
    private Collection<OrderWaypoint> wayPoints;

    @OneToOne
    @JoinColumn(name = "truckOnOrder_tr_id")
    @Getter
    @Setter
    private Truck truckOnOrder;


    @ManyToMany
    @JoinTable(name="lg_orders_driversOnOrder",joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "driver_id"))
    @Getter
    @Setter
    private List <Driver> driversOnOrder;

}