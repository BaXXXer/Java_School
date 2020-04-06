package edu.tsystems.javaschool.logapp.api.entity;

import edu.tsystems.javaschool.logapp.api.dto.DriverUserDTO;
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
        REST,CARGO_HANDLING, REST_ON_SHIFT,DRIVING,CO_DRIVER;

        public static DriverUserDTO.Status switchFromDriverToDtoStatus(Status statusFrom){
            switch (statusFrom){
                case DRIVING:
                    return DriverUserDTO.Status.DRIVING;
                case REST:
                    return DriverUserDTO.Status.REST;
                case REST_ON_SHIFT:
                    return  DriverUserDTO.Status.REST_ON_SHIFT;
                case CARGO_HANDLING:
                    return DriverUserDTO.Status.CARGO_HANDLING;
                default:
                    return DriverUserDTO.Status.CO_DRIVER;
            }
        }

        public static Status switchFromDtoToStatus(DriverUserDTO.Status statusFrom){
            switch (statusFrom){
                case DRIVING:
                    return DRIVING;
                case REST:
                    return REST;
                case REST_ON_SHIFT:
                    return REST_ON_SHIFT ;
                case CARGO_HANDLING:
                    return CARGO_HANDLING;
                default:
                    return CO_DRIVER;
            }
        }


        public static Status convertDriverStatusToEnum(String status){
            Status[] values = values();
            for(Status st: values){
                if(st.toString().equals(status)){
                    return st;
                }
            }
            throw new EntityNotFoundException("Status " + status + " was not found");

        }
    }

    @ManyToOne()
    @JoinColumn(name = "orders_or_id")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dr_user")
//    @PrimaryKeyJoinColumn
    private User user;



}
