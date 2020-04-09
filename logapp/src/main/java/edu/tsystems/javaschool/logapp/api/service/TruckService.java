package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dto.TruckDTO;
import edu.tsystems.javaschool.logapp.api.dto.mapper.TruckMapper;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import edu.tsystems.javaschool.logapp.api.exception.DuplicateEntityException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TruckService {


    private TruckDao truckDao;
    private CityService cityService;
    private static final Logger LOG  = Logger.getLogger(TruckService.class);


    private final TruckMapper mapper;

    @Autowired
    public TruckService(TruckDao truckDao, CityService cityService, TruckMapper mapper) {
        this.truckDao = truckDao;
        this.cityService = cityService;
        this.mapper = mapper;
    }

    public TruckDao getTruckDao() {
        return truckDao;
    }


    @Transactional
    public void saveTruck(TruckDTO truckDTO) {
        List<TruckDTO> allTrucks = getAllTrucks();
        for (TruckDTO t : allTrucks) {
            if (t.getRegNumber().equals(truckDTO.getRegNumber())) {
                LOG.error("Truck with number " + truckDTO.getRegNumber()+ " already exists in DB!");
                throw new DuplicateEntityException();
            }
        }
        Truck entity = toEntity(truckDTO);
        truckDao.saveTruck(entity);
    }


    @Transactional
    public List<TruckDTO> getAllTrucks() {
        List<TruckDTO> dtos = new ArrayList();

        for (Truck t : truckDao.getAllTrucks()) {
            dtos.add(toDTO(t));
        }
        return dtos;

    }

    @Transactional
    public void updateTruck(TruckDTO truck) {
        truckDao.updateTruck(toEntity(truck));
    }

    public void removeTruck(int id) {

        truckDao.removeTruck(id);
    }

    //TODO: refactor with working hours dependent on orders
    @Transactional
    public TruckDTO getTruckById(int id) {
        Truck entity = truckDao.getTruckById(id);
        return toDTO(entity);
    }

    public TruckDTO toDTO(Truck entity) {
        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setId(entity.getId());
        truckDTO.setRegNumber(entity.getRegNumber());

        truckDTO.setDriverWorkingHours(entity.getDriverWorkingHours());
        truckDTO.setCapacityTons(entity.getCapacityTons());
        truckDTO.setCondition(entity.getCondition());
        truckDTO.setCurrentCityId(entity.getCurrentCity().getCityId());
        return truckDTO;

    }

    public Truck toEntity(TruckDTO dto) {
        Truck entity;
        if(dto.getId()==null){

            entity = new Truck();
        }else{
            entity = truckDao.getTruckById(dto.getId());
        }
        entity.setId(dto.getId());
        entity.setCapacityTons(dto.getCapacityTons());
        entity.setCondition(dto.getCondition());
        entity.setCurrentCity(cityService.getCityById(dto.getCurrentCityId()));
        if(dto.getDriverWorkingHours()==null){
            dto.setDriverWorkingHours(0);
        }else{
            entity.setDriverWorkingHours(dto.getDriverWorkingHours());
        }

        entity.setRegNumber(dto.getRegNumber());
        return entity;

    }

    @Transactional
    public Map<Integer, String> getTruckMap() {
        Map<Integer, String> map = new HashMap<>();
        for (TruckDTO t : getAllTrucks()) {
            map.put(t.getId(), t.getRegNumber());
        }
        return map;
    }

    public List<TruckDTO> getReadyToGoTrucks() {
        List<TruckDTO> readyDtos = new ArrayList<>();
        for(Truck t: truckDao.getReadyToGoTrucks()){
            readyDtos.add(toDTO(t));
        }
        return readyDtos;
    }

    public int getLastAddedTruckIndex() {
        int index = getAllTrucks().size() - 1;
        return getAllTrucks().get(index).getId();
    }


//    }


}
