package org.tsystems.javaschool.logapp.api.entities;

import java.util.List;

public class CountryMap {
    private List<City> cities;
    private int distance;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
