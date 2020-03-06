package edu.tsystems.javaschool.logapp.api.services;

import edu.tsystems.javaschool.logapp.api.entities.Driver;
import edu.tsystems.javaschool.logapp.api.entities.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.entities.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.services.mappers.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {

    private DriverDao driverDao;
    private final DriverMapper mapper;

    @Autowired
    public DriverService(DriverDao driverDao, DriverMapper mapper) {
        this.driverDao = driverDao;
        this.mapper = mapper;
    }

    public void saveDriver(DriverDTO driverDTO) throws IOException {
        driverDao.saveDriver(mapper.toEntity(driverDTO));
    }

    @Transactional
    public List<DriverDTO> getAllDrivers(){
        List<DriverDTO> dtos = new ArrayList();

        for(Driver d: driverDao.getAllDrivers()){
            dtos.add(mapper.toDto(d));
        }
        return dtos;

    }


}
