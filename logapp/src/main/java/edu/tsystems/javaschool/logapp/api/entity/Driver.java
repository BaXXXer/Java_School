package edu.tsystems.javaschool.logapp.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lg_drivers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_seq")
    @SequenceGenerator(name="driver_seq",sequenceName = "driver_seq",allocationSize = 1)
    @Column(name = "dr_id")
    private Integer driverId;

    @Column(name = "dr_firstName")
    private String driverFirstName;

    @Column(name="dr_surname")
    private String driverSurname;

    @Column(name="dr_PrivateNum")
    private Integer driverPrivateNum;

    @Column(name="dr_workingHours")
    private Integer driverWorkedHours;

    @Enumerated(EnumType.STRING)
    @Column(name="dr_status")
    private Status driverStatus;

    @Column(name="dr_cityId")
    private int driverCityId;


    @ManyToOne()
    @JoinColumn(name="driversTruck_tr_id")
    private Truck driversTruck;

    public enum Status {
        OFF,WORKING,DRIVING
    }


}
