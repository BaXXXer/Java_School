package edu.tsystems.javaschool.logapp.api.services;

import edu.tsystems.javaschool.logapp.api.entities.Truck;
import edu.tsystems.javaschool.logapp.api.entities.dao.TruckDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.List;

@Service
@ManagedBean(name = "truckService")
public class TruckService {


    @Autowired
    private TruckDao truckDao;

    public TruckService() {
    }

    public void saveTruck(Truck truck) throws IOException {
        truckDao.saveTruck(truck);
    }

    @Transactional
    public List<Truck> getAllTrucks(){

        return truckDao.getAllTrucks();
    }
}
