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
@Getter
@Setter

public class Truck {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "truck_seq")
    @SequenceGenerator(name="truck_seq",sequenceName = "truck_seq",allocationSize = 1)
    @Id
    @Column(name = "tr_id")
    private Integer id;

    @Column(name = "tr_regNumber")
    private String regNumber;

    @OneToMany(mappedBy = "driversTruck", cascade = CascadeType.ALL)
    private List<Driver> drivers;

    @Column(name = "tr_workingHours")
    private Integer driverWorkingHours;

//    @Column(name = "tr_cityId")
    @ManyToOne()
    @JoinColumn(name = "current_city")
    private City currentCity;

    @Column(name = "tr_capacityTons")
    private Integer capacityTons;

    @Enumerated(EnumType.STRING)
    @Column(name = "tr_condition")
    private Condition condition;

    public enum Condition {OK, BROKEN;}


}
