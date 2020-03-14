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
public class OrderWaypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "waypoint_seq")
    @SequenceGenerator(name="waypoint_seq",sequenceName = "waypoint_seq",allocationSize = 1)
    @Column(name = "ow_id")
    @Getter
    @Setter
    private Integer id;

    @Column(name="ow_isCompleted")
    @Getter
    @Setter
    private boolean isCompleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "ow_operation")
    @Getter
    @Setter
    private Operation operationType;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="cargo_id")
    @Getter
    @Setter
    private Cargo cargo;

    @ManyToOne()
    @JoinColumn(name = "orders_or_id")
    @Getter
    @Setter
    private Order order;

    @Column(name = "waypoint_weight")
    @Getter
    @Setter
    private int waypointWeight;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    @Getter
    @Setter
    private City city;


    public enum Operation{
        LOAD,UNLOAD
    }

    }
