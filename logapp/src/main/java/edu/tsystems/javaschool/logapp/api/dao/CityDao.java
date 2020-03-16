package edu.tsystems.javaschool.logapp.api.dao;

import edu.tsystems.javaschool.logapp.api.entity.City;

import java.util.List;

public interface CityDao {
    List<City> getAllCities();
    City getCityById(int id);
}
