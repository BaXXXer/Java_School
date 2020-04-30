package edu.tsystems.javaschool.logapp.api.dto.converter;

import edu.tsystems.javaschool.logapp.api.dao.CityDao;
import edu.tsystems.javaschool.logapp.api.dto.CityDTO;
import edu.tsystems.javaschool.logapp.api.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CityDtoConverter {

    private final CityDao cityDao;

    @Autowired
    public CityDtoConverter(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Transactional
    public CityDTO convertToDto(City entity){
        CityDTO dto = new CityDTO();
        dto.setCityId(entity.getCityId());
        dto.setCityName(entity.getCityName());
        dto.setLat(entity.getLat());
        dto.setLng(entity.getLng());
        return dto;
    }
    @Transactional
    public City convertToEntity(CityDTO dto){
        City entity;
        entity = cityDao.getCityById(dto.getCityId());
        entity.setCityName(dto.getCityName());
        entity.setLat(dto.getLat());
        entity.setLng(dto.getLng());
        return entity;
    }


}
