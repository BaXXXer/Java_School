package edu.tsystems.javaschool.logapp.api.services;

import edu.tsystems.javaschool.logapp.api.entities.Truck;
import edu.tsystems.javaschool.logapp.api.entities.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.entities.dto.TruckDTO;
import edu.tsystems.javaschool.logapp.api.services.mappers.TruckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TruckService {


    private TruckDao truckDao;

    private final TruckMapper mapper;

    @Autowired
    public TruckService(TruckDao truckDao, TruckMapper mapper) {
        this.truckDao = truckDao;
        this.mapper = mapper;
    }

    public TruckDao getTruckDao() {
        return truckDao;
    }

    //TODO: correct mapping dto-dao finish (@Transactional??)
    public void saveTruck(TruckDTO truckDTO) throws IOException {
        truckDao.saveTruck(mapper.toEntity(truckDTO));
//        truckDao.saveTruck(truck);
    }

    @Transactional
    public List<TruckDTO> getAllTrucks(){
        List<TruckDTO> dtos = new ArrayList();

        for (Truck t:truckDao.getAllTrucks()) {
            dtos.add(mapper.toDto(t));
        }
        return dtos;


//        return truckDao.getAllTrucks();
    }

    public void updateTruck(TruckDTO truck) {


        truckDao.updateTruck(mapper.toEntity(truck));
    }

    public void removeTruck(int id) {
        truckDao.removeTruck(id);
    }

    //TODO: refactor with working hours dependent on orders

    @Transactional
    public TruckDTO getTruckById(int id){
        Truck dao = truckDao.getTruckById(id);
        TruckDTO dto = new TruckDTO();
        dto.setId(dao.getId());
        dto.setRegNumber(dao.getRegNumber());

        dto.setDriverWorkingHours(dao.getDriverWorkingHours());
        dto.setCapacityTons(dao.getCapacityTons());
        dto.setCondition(dao.getCondition());
        dto.setCurrentCityId(dao.getCurrentCityId());
        return dto;
    }

    private static TruckDTO toDTO(Truck dao){
        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setId(dao.getId());
        truckDTO.setRegNumber(dao.getRegNumber());

        truckDTO.setDriverWorkingHours(dao.getDriverWorkingHours());
        truckDTO.setCapacityTons(dao.getCapacityTons());
        truckDTO.setCondition(dao.getCondition());
        truckDTO.setCurrentCityId(dao.getCurrentCityId());
        return truckDTO;

    }


}
