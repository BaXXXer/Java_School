package edu.tsystems.javaschool.logapp.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders_waypoints")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderWaypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waypoint_seq")
    @SequenceGenerator(name="waypoint_seq",sequenceName = "waypoint_seq",allocationSize = 1)
    @Column(name = "ow_id")
    private Integer id;

    @Column(name="ow_isCompleted")
    private boolean isCompleted;

    @Column(name="ow_point")
    private String pointName;

    @Enumerated(EnumType.STRING)
    @Column(name = "ow_operation")
    private Operation operationType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cargo_id")
    private Cargo cargo;

    @ManyToOne()
    @JoinColumn(name = "orders_or_id")
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "city_id", nullable = false)
    private City city;


    public enum Operation{
        LOAD,UNLOAD
    }

    }
