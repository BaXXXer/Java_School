package edu.tsystems.javaschool.logapp.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @Getter
    @Setter
    private int cityId;

    @Column(name="c_name")
    @Getter
    @Setter
    private String cityName;

    @Column(name = "c_lat")
    @Getter
    @Setter
    private double lat;

    @Column(name = "c_lng")
    @Getter
    @Setter
    private double lng;


   
}
