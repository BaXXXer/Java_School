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
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_seq")
    @SequenceGenerator(name="driver_seq",sequenceName = "driver_seq",allocationSize = 1)
    @Column(name = "dr_id")
    @Getter
    @Setter
    private Integer driverId;

    @Column(name = "dr_firstName")
    @Getter
    @Setter
    private String driverFirstName;

    @Column(name="dr_surname")
    @Getter
    @Setter
    private String driverSurname;

    @Column(name="dr_PrivateNum")
    @Getter
    @Setter
    private Integer driverPrivateNum;

    @Column(name="dr_workingHours")
    @Getter
    @Setter
    private Integer driverWorkedHours;

    @Enumerated(EnumType.STRING)
    @Column(name="dr_status")
    @Getter
    @Setter
    private Status driverStatus;

    @Column(name="dr_cityId")
    @Getter
    @Setter
    private int driverCityId;


    @ManyToOne()
    @JoinColumn(name="driversTruck_tr_id")
    @Getter
    @Setter
    private Truck driversTruck;

    public enum Status {
        OFF,WORKING,DRIVING
    }


}
