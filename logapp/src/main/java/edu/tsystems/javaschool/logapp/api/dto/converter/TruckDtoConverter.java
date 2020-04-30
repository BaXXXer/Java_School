package edu.tsystems.javaschool.logapp.api.dto.converter;

import edu.tsystems.javaschool.logapp.api.dao.CityDao;
import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dto.TruckDTO;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TruckDtoConverter {

    private final TruckDao truckDao;
    private final CityDao cityDao;

    @Autowired
    public TruckDtoConverter(TruckDao truckDao, CityDao cityDao) {
        this.truckDao = truckDao;
        this.cityDao = cityDao;
    }


    @Transactional
    public TruckDTO convertToDto(Truck entity){
        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setId(entity.getId());
        truckDTO.setRegNumber(entity.getRegNumber());

        truckDTO.setDriverWorkingHours(entity.getDriverWorkingHours());
        truckDTO.setCapacityTons(entity.getCapacityTons());
        truckDTO.setCondition(entity.getCondition());
        truckDTO.setCurrentCityId(entity.getCurrentCity().getCityId());
        return truckDTO;
    }

    @Transactional
    public Truck convertToEntity(TruckDTO dto){
        Truck entity;
        if(dto.getId()==null){

            entity = new Truck();
        }else{
            entity = truckDao.getTruckById(dto.getId());
        }
        entity.setId(dto.getId());
        entity.setCapacityTons(dto.getCapacityTons());
        entity.setCondition(dto.getCondition());
        entity.setCurrentCity(cityDao.getCityById(dto.getCurrentCityId()));
        if(dto.getDriverWorkingHours()==null){
            dto.setDriverWorkingHours(0);
        }else{
            entity.setDriverWorkingHours(dto.getDriverWorkingHours());
        }

        entity.setRegNumber(dto.getRegNumber());
        return entity;

    }
}
