package edu.tsystems.javaschool.logapp.api.entities;

import javax.persistence.*;

@Entity()
@Table(name="lg_cities")
public class City {
    @Id
    @GeneratedValue
    @Column(name="c_id")
    private int cityId;

    @Column(name="c_name")
    private String cityName;

    @Column(name = "c_lat")
    private double lat;

    @Column(name = "c_lng")
    private double lng;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
