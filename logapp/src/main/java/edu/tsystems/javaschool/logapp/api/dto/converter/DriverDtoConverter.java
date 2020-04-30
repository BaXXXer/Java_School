package edu.tsystems.javaschool.logapp.api.dto.converter;

import edu.tsystems.javaschool.logapp.api.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class DriverDtoConverter {

    private final DriverDao driverDao;
    private final TruckDao truckDao;

    public DriverDtoConverter(DriverDao driverDao, TruckDao truckDao) {
        this.driverDao = driverDao;
        this.truckDao = truckDao;
    }

    @Transactional
    public DriverDTO convertToDTO(Driver entity){
        DriverDTO dto = new DriverDTO();

        if (entity.getDriversTruck() != null) {
            dto.setDriversTruckId(entity.getDriversTruck().getId());
        }

        if(entity.getUser()!=null){
            dto.setUserId(entity.getUser().getId());
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
    public Driver convertToEntity(DriverDTO driverDTO){
        try {
            Driver driver = driverDao.getDriverById(driverDTO.getDriverId());
            driver.setDriverSurname(driverDTO.getDriverSurname());
            driver.setDriverFirstName(driverDTO.getDriverFirstName());
            driver.setDriverPrivateNum(driverDTO.getDriverPrivateNum());
            driver.setDriverWorkedHours(driverDTO.getDriverWorkedHours());
            driver.setDriverStatus(driverDTO.getDriverStatus());
            driver.setDriverCityId(driverDTO.getDriverCityId());
            if(driverDTO.getDriversTruckId() != null){
                driver.setDriversTruck(truckDao.getTruckById(driverDTO.getDriversTruckId()));
            }
            return driver;


        } catch (ObjectNotFoundException ex) {
            Driver driver = new Driver();
            driver.setDriverSurname(driverDTO.getDriverSurname());
            driver.setDriverFirstName(driverDTO.getDriverFirstName());
            driver.setDriverPrivateNum(driverDTO.getDriverPrivateNum());
            driver.setDriverWorkedHours(driverDTO.getDriverWorkedHours());
            if (driverDTO.getDriverStatus() == null) {
                driver.setDriverStatus(Driver.Status.REST);
            } else {
                driver.setDriverStatus(driverDTO.getDriverStatus());
            }
            driver.setDriverCityId(driverDTO.getDriverCityId());
            if(driverDTO.getDriversTruckId() != null){
                driver.setDriversTruck(truckDao.getTruckById(driverDTO.getDriversTruckId()));
            }
            return driver;
        }

    }
}
