package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.CityDao;
import edu.tsystems.javaschool.logapp.api.dto.CityDTO;
import edu.tsystems.javaschool.logapp.api.dto.converter.CityDtoConverter;
import edu.tsystems.javaschool.logapp.api.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityService {

    private final CityDao cityDao;
    private final CityDtoConverter converter;

    @Autowired
    public CityService(CityDao cityDao, CityDtoConverter converter) {
        this.cityDao = cityDao;
        this.converter = converter;
    }


    public List<City> getAllCities() {
        return cityDao.getAllCities();
    }

    public List<CityDTO> getAllCitiesDTO() {
        List<CityDTO> cities = new ArrayList<>();
        for (City c : getAllCities()) {
            cities.add(converter.convertToDto(c));
        }
        return cities;
    }


    @Transactional
    public City getCityById(int id) {
        return cityDao.getCityById(id);
    }

    @Transactional
    public CityDTO getCityDtoById(int id) {

        return converter.convertToDto(cityDao.getCityById(id));
    }


    public Map<Integer, String> getCityMap() {
        Map<Integer, String> cityMap = new HashMap<>();
        for (City c : getAllCities()) {
            cityMap.put(c.getCityId(), c.getCityName());
        }
        return cityMap;
    }
}
