package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dto.TruckDTO;
import edu.tsystems.javaschool.logapp.api.dto.mapper.TruckMapper;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TruckService {


    private TruckDao truckDao;
    private CityService cityService;

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
    public void saveTruck(TruckDTO truckDTO) throws IOException {
        Truck entity = toEntity(truckDTO);
        List <Truck> trucks = entity.getCurrentCity().getTruckList();
        if(!trucks.contains(entity)) {
            trucks.add(entity);
        }
        truckDao.saveTruck(entity);
    }

    @Transactional
    public List<TruckDTO> getAllTrucks(){
        List<TruckDTO> dtos = new ArrayList();

        for (Truck t:truckDao.getAllTrucks()) {
            dtos.add(toDTO(t));
        }
        return dtos;


//        return truckDao.getAllTrucks();
    }

    public void updateTruck(TruckDTO truck) {

        truckDao.updateTruck(toEntity(truck));
    }

    public void removeTruck(int id) {
        truckDao.removeTruck(id);
    }

    //TODO: refactor with working hours dependent on orders
    @Transactional
    public TruckDTO getTruckById(int id){
        Truck entity = truckDao.getTruckById(id);
        entity.getCurrentCity().getTruckList().add(entity);// adds truck to the city
        return toDTO(entity);
    }

    private TruckDTO toDTO(Truck entity){
        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setId(entity.getId());
        truckDTO.setRegNumber(entity.getRegNumber());

        truckDTO.setDriverWorkingHours(entity.getDriverWorkingHours());
        truckDTO.setCapacityTons(entity.getCapacityTons());
        truckDTO.setCondition(entity.getCondition());
        truckDTO.setCurrentCityId(entity.getCurrentCity().getCityId());
        return truckDTO;

    }

    private Truck toEntity(TruckDTO dto){
        Truck entity = new Truck();
        entity.setId(dto.getId());
        entity.setCapacityTons(dto.getCapacityTons());
        entity.setCondition(dto.getCondition());
        entity.setCurrentCity(cityService.getCityById(dto.getCurrentCityId()));
        entity.setDriverWorkingHours(dto.getDriverWorkingHours());
        entity.setRegNumber(dto.getRegNumber());
        return entity;

    }

    @Transactional
    public Map<Integer, String> getTruckMap(){
        Map<Integer, String> map = new HashMap<>();
        for(TruckDTO t: getAllTrucks()){
            map.put(t.getId(),t.getRegNumber());
        }
        return map;
    }


}
