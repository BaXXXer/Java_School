package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.CargoDao;
import edu.tsystems.javaschool.logapp.api.dto.CargoDTO;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CargoService {

    private final CargoDao cargoDao;

    @Autowired
    public CargoService(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    @Transactional
    public CargoDTO toDto(Cargo entity){
        CargoDTO dto = new CargoDTO();
        dto.setCargoId(entity.getCargoId());
        dto.setCargoName(entity.getCargoName());
        dto.setCargoStatus(entity.getCargoStatus());
        dto.setTitle(entity.getTitle());
        dto.setCargoWeightKilos(entity.getCargoWeightKilos());
        return dto;
    }

    @Transactional
    public Cargo toEntity(CargoDTO dto){
        Cargo entity = cargoDao.findCargoById(dto.getCargoId());
        entity.setCargoName(dto.getCargoName());
        entity.setCargoStatus(dto.getCargoStatus());
        entity.setCargoWeightKilos(dto.getCargoWeightKilos());
        return entity;
    }
}
