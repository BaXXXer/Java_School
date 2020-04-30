package edu.tsystems.javaschool.logapp.api.dto.converter;

import edu.tsystems.javaschool.logapp.api.dao.CargoDao;
import edu.tsystems.javaschool.logapp.api.dto.CargoDTO;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CargoDtoConverter {

    private final CargoDao cargoDao;
    private final CityDtoConverter cityConverter;

    public CargoDtoConverter(CargoDao cargoDao, CityDtoConverter cityConverter) {
        this.cargoDao = cargoDao;
        this.cityConverter = cityConverter;
    }

    @Transactional
    public CargoDTO convertToDto(Cargo entity){
        CargoDTO dto = new CargoDTO();
        dto.setCargoId(entity.getCargoId());
        dto.setCargoName(entity.getCargoName());
        dto.setCargoStatus(entity.getCargoStatus());
        dto.setTitle(entity.getTitle());
        dto.setCargoWeightKilos(entity.getCargoWeightKilos());
        dto.setCurrentCity(cityConverter.convertToDto(entity.getCurrentCity()));
        return dto;
    }

    @Transactional
    public Cargo convertToEntity(CargoDTO dto){
        Cargo entity = cargoDao.findCargoById(dto.getCargoId());
        entity.setCargoName(dto.getCargoName());
        entity.setCargoStatus(dto.getCargoStatus());
        entity.setCargoWeightKilos(dto.getCargoWeightKilos());
        entity.setCurrentCity(cityConverter.convertToEntity(dto.getCurrentCity()));
        return entity;

    }
}
