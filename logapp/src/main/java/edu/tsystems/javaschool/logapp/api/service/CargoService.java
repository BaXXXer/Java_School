package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.CargoDao;
import edu.tsystems.javaschool.logapp.api.dto.CargoDTO;
import edu.tsystems.javaschool.logapp.api.dto.converter.CargoDtoConverter;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CargoService {

    private final CargoDao cargoDao;
    private final CargoDtoConverter converter;

    @Autowired
    public CargoService(CargoDao cargoDao, CargoDtoConverter converter) {
        this.cargoDao = cargoDao;
        this.converter = converter;
    }


    @Transactional
    public List<CargoDTO> getAllCargoes(){
        List<Cargo> entities = cargoDao.getAllCargoes();
        List<CargoDTO> dtos = new ArrayList<>();
        for(Cargo c:entities){
            dtos.add(converter.convertToDto(c));
        }
        return dtos;
    }

    @Transactional
    public CargoDTO findCargoById(int id){
        return converter.convertToDto(cargoDao.findCargoById(id));
    }
}
