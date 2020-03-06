package edu.tsystems.javaschool.logapp.api.services;

import edu.tsystems.javaschool.logapp.api.entities.Truck;
import edu.tsystems.javaschool.logapp.api.entities.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.entities.dto.TruckDTO;
import edu.tsystems.javaschool.logapp.api.services.mappers.TruckMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@ManagedBean(name = "truckService")
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


//    public List<String> getAllTruckRegNums(){
//        List<String> regNumbers = new ArrayList();
//        for(TruckDTO dto : this.getAllTrucks()){
//            regNumbers.add(dto.getRegNumber());
//        }
//        return regNumbers;
//
//    }
}
