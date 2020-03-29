package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.CargoDao;
import edu.tsystems.javaschool.logapp.api.dto.CargoDTO;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CargoService {

    private final CargoDao cargoDao;
    private final CityService cityService;

    @Autowired
    public CargoService(CargoDao cargoDao, CityService cityService) {
        this.cargoDao = cargoDao;
        this.cityService = cityService;
    }

    @Transactional
    public CargoDTO toDto(Cargo entity){
        CargoDTO dto = new CargoDTO();
        dto.setCargoId(entity.getCargoId());
        dto.setCargoName(entity.getCargoName());
        dto.setCargoStatus(entity.getCargoStatus());
        dto.setTitle(entity.getTitle());
        dto.setCargoWeightKilos(entity.getCargoWeightKilos());
        dto.setCurrentCity(cityService.toDto(entity.getCurrentCity()));
        return dto;
    }

    @Transactional
    public Cargo toEntity(CargoDTO dto){
        Cargo entity = cargoDao.findCargoById(dto.getCargoId());
        entity.setCargoName(dto.getCargoName());
        entity.setCargoStatus(dto.getCargoStatus());
        entity.setCargoWeightKilos(dto.getCargoWeightKilos());
        entity.setCurrentCity(cityService.toEntity(dto.getCurrentCity()));
        return entity;
    }



    @Transactional
    public List<CargoDTO> getAllCargoes(){
        List<Cargo> entities = cargoDao.getAllCargoes();
        List<CargoDTO> dtos = new ArrayList<>();
        for(Cargo c:entities){
            dtos.add(toDto(c));
        }
        return dtos;
    }

    @Transactional
    public CargoDTO findCargoById(int id){
        return toDto(cargoDao.findCargoById(id));
    }
}
