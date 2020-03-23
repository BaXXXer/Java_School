package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.CityDao;
import edu.tsystems.javaschool.logapp.api.dto.CityDTO;
import edu.tsystems.javaschool.logapp.api.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityService {

    private CityDao cityDao;

    @Autowired
    public CityService(CityDao cityDao) {
        this.cityDao = cityDao;
    }


    public List<City> getAllCities(){
        return cityDao.getAllCities();
    }

    @Transactional
    public City getCityById(int id){
        return cityDao.getCityById(id);
    }

    public Map<Integer, String> getCityMap(){
        Map <Integer, String> cityMap = new HashMap();
        for(City c: getAllCities()){
            cityMap.put(c.getCityId(),c.getCityName());
        }
        return cityMap;
    }

    @Transactional
    public CityDTO toDto(City entity){
        CityDTO dto = new CityDTO();
        dto.setCityId(entity.getCityId());
        dto.setCityName(entity.getCityName());
        dto.setLat(entity.getLat());
        dto.setLng(entity.getLng());
        return dto;

    }

    @Transactional
    public City toEntity(CityDTO dto){
        City entity = new City();
        entity.setCityName(dto.getCityName());
        entity.setLat(dto.getLat());
        entity.setLng(dto.getLng());
        return entity;
    }
}
