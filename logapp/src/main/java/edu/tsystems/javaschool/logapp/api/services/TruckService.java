package edu.tsystems.javaschool.logapp.api.services;

import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dao.TruckDaoImpl;
import edu.tsystems.javaschool.logapp.api.entities.Truck;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TruckService {

    private TruckDao truckDao = new TruckDaoImpl();

    public TruckService() {
    }

    public void saveTruck(Truck truck) throws IOException {
        truckDao.saveTruck(truck);
    }

    public List<Truck> getAllTrucks(){
        return truckDao.getAllTrucks();
    }
}
