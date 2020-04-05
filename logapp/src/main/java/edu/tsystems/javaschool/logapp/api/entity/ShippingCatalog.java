package edu.tsystems.javaschool.logapp.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="lg_constants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShippingCatalog {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "working_hours")
    private final Integer maxWorkingHours = 176;

    @Column(name = "limit_per_day")
    private final Integer limitHoursPerDay = 8;

    @Column(name = "avg_speed")
    private final Integer truckAvgSpeed = 80;

}
