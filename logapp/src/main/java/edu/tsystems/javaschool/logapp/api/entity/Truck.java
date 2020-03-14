package edu.tsystems.javaschool.logapp.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lg_trucks")
@AllArgsConstructor
@NoArgsConstructor

public class Truck {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "truck_seq")
    @SequenceGenerator(name="truck_seq",sequenceName = "truck_seq",allocationSize = 1)
    @Id
    @Column(name = "tr_id")
    @Getter
    @Setter
    private Integer id;

    @Column(name = "tr_regNumber")
    @Getter
    @Setter
    private String regNumber;

    @OneToMany(mappedBy = "driversTruck", cascade = CascadeType.ALL)
    @Getter
    @Setter
    private List<Driver> drivers;

    @Column(name = "tr_workingHours")
    @Getter
    @Setter
    private Integer driverWorkingHours;

    @Column(name = "tr_cityId")
    @Getter
    @Setter
    private Integer currentCityId;

    @Column(name = "tr_capacityTons")
    @Getter
    @Setter
    private Integer capacityTons;

    @Enumerated(EnumType.STRING)
    @Column(name = "tr_condition")
    @Getter
    @Setter
    private Condition condition;

    public enum Condition {OK, BROKEN;}


}
