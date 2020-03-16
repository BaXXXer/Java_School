package edu.tsystems.javaschool.logapp.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity()
@Table(name="lg_cities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
    @SequenceGenerator(name="city_seq",sequenceName = "city_seq",allocationSize = 1)
    @Column(name="c_id")
    private int cityId;

    @Column(name="c_name")
    private String cityName;

    @Column(name = "c_lat")
    private double lat;

    @Column(name = "c_lng")
    private double lng;

    @OneToMany(mappedBy = "currentCity",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Truck> truckList;


   
}
