package edu.tsystems.javaschool.logapp.api.services;

import edu.tsystems.javaschool.logapp.api.entities.Driver;
import edu.tsystems.javaschool.logapp.api.entities.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.entities.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.entities.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.exceptions.EntityNotFoundException;
import edu.tsystems.javaschool.logapp.api.services.mappers.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {

    private DriverDao driverDao;
    private final DriverMapper mapper;
    private TruckService truckService;
    private TruckDao truckDao;

    @Autowired
    public DriverService(DriverDao driverDao, DriverMapper mapper, TruckService truckService, TruckDao truckDao) {
        this.driverDao = driverDao;
        this.mapper = mapper;
        this.truckService = truckService;
        this.truckDao = truckDao;
    }

    @Transactional
    public void saveDriver(DriverDTO driverDTO) throws EntityNotFoundException {
        Driver driver = toEntity(driverDTO);
        try {
            driver.setDriversTruckId(truckDao.getTruckById(driverDTO.getDriversTruckId()));
            driverDao.saveDriver(driver);

        } catch (DataIntegrityViolationException ex) {
            throw new EntityNotFoundException("Truck", driverDTO.getDriversTruckId());
        } catch (IOException ex) {
            throw new RuntimeException("exception while saving" + ex);
        }


    }

    private Driver toEntity(DriverDTO driverDTO) {
        Driver driver = mapper.toEntity(driverDTO);

        driver.setDriverSurname(driverDTO.getDriverSurname());
        driver.setDriverFirstName(driverDTO.getDriverFirstName());
        driver.setDriverPrivateNum(driverDTO.getDriverPrivateNum());
        driver.setDriverWorkedHours(driverDTO.getDriverWorkedHours());
        driver.setDriverStatus(driverDTO.getDriverStatus());
        driver.setDriverCityId(driverDTO.getDriverCityId());
        return driver;
    }

    private DriverDTO toDto(Driver entity) {
        DriverDTO dto = new DriverDTO();

        try {
            dto.setDriversTruckId(entity.getDriversTruckId().getId());
        }catch (NullPointerException ex){
            dto.setDriversTruckId(0);
        }
        dto.setDriverId(entity.getDriverId());
        dto.setDriverPrivateNum(entity.getDriverPrivateNum());
        dto.setDriverStatus(entity.getDriverStatus());
        dto.setDriverCityId(entity.getDriverCityId());
        dto.setDriverFirstName(entity.getDriverFirstName());
        dto.setDriverSurname(entity.getDriverSurname());
        dto.setDriverWorkedHours(entity.getDriverWorkedHours());

        return dto;
    }



    @Transactional
    public List<DriverDTO> getAllDrivers() {
        List<DriverDTO> dtos = new ArrayList();

        for (Driver d : driverDao.getAllDrivers()) {
            dtos.add(toDto(d));
        }
        return dtos;
    }

    @Transactional
    public DriverDTO getDriverById(int id) {
        Driver dao = driverDao.getDriverById(id);
        return toDto(dao);
    }

    @Transactional
    public void removeDriver(int id) {
        driverDao.removeDriver(id);
    }

    @Transactional
    public void updateDriver(DriverDTO driver) {
        driverDao.updateDriver(mapper.toEntity(driver));
    }




}
