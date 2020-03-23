package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.dto.DriverUserDTO;
import edu.tsystems.javaschool.logapp.api.dto.mapper.DriverMapper;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.entity.User;
import edu.tsystems.javaschool.logapp.api.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DriverService {

    private DriverDao driverDao;
    private final DriverMapper mapper;
    private TruckDao truckDao;
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    public DriverService(DriverDao driverDao, DriverMapper mapper, TruckDao truckDao, UserService userService) {
        this.driverDao = driverDao;
        this.mapper = mapper;
        this.truckDao = truckDao;
        this.userService = userService;
    }

    @Transactional
    public void saveDriver(DriverDTO driverDTO) throws EntityNotFoundException {
        Driver driver = toEntity(driverDTO);
        User user = createUser(driver);
        driver.setUser(user);

        try {
            driver.setDriversTruck(truckDao.getTruckById(driverDTO.getDriversTruckId()));
//            driver.setUser(user);
            driverDao.saveDriver(driver);
            userService.createUser(user);



//        } catch (DataIntegrityViolationException ex) {
//            throw new EntityNotFoundException("Truck", driverDTO.getDriversTruckId());
        } catch (IOException ex) {
            throw new RuntimeException("exception while saving" + ex);
        }


    }

    @Transactional
    User createUser(Driver driver){
        User user = new User();
        user.setEmail(driver.getDriverFirstName().toLowerCase()+"."+driver.getDriverSurname().toLowerCase()+"@logapp.com");
        user.setRole(User.UserRole.ROLE_DRIVER);
        user.setPasswordMd5("password");
        user.setDriver(driver);
        return user;

    }

    public Driver toEntity(DriverDTO driverDTO) {
        Driver driver = mapper.toEntity(driverDTO);

        driver.setDriverSurname(driverDTO.getDriverSurname());
        driver.setDriverFirstName(driverDTO.getDriverFirstName());
        driver.setDriverPrivateNum(driverDTO.getDriverPrivateNum());
        driver.setDriverWorkedHours(driverDTO.getDriverWorkedHours());
        driver.setDriverStatus(driverDTO.getDriverStatus());
        driver.setDriverCityId(driverDTO.getDriverCityId());
//        driver.setUser(userService.findUserById(driverDTO.getUserId()));
        driver.setDriversTruck(truckDao.getTruckById(driverDTO.getDriversTruckId()));
        return driver;
    }

    public DriverDTO toDto(Driver entity) {
        DriverDTO dto = new DriverDTO();

        try {
            dto.setDriversTruckId(entity.getDriversTruck().getId());
        } catch (NullPointerException ex) {
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
        Driver entity = toEntity(driver);
        entity.setDriversTruck(truckDao.getTruckById(driver.getDriversTruckId()));
        driverDao.updateDriver(entity);
    }

    public List<DriverDTO> findFreeDriversInCity(int cityId, int maxHours){
        List<DriverDTO> dtos = new ArrayList();
        for(Driver d: driverDao.findFreeDriversInCity(cityId,maxHours)){
            dtos.add(toDto(d));
        }

        return dtos;
    }

    /**
     * Converts Driver entity to DriverUserDTO
     * @param entity
     * @return DriverUserDTO
     */
    public DriverUserDTO toDUDto(Driver entity){
        DriverUserDTO dto = new DriverUserDTO();
        dto.setDriverId(entity.getDriverId());
        dto.setDriverPrivateNum(entity.getDriverPrivateNum());
        switch (entity.getDriverStatus()){
            case DRIVING:
                dto.setDriverStatus(DriverUserDTO.Status.DRIVING);
            case REST:
                dto.setDriverStatus(DriverUserDTO.Status.REST);
            case ON_SHIFT:
                dto.setDriverStatus(DriverUserDTO.Status.CO_DRIVER);
        }
        dto.setDriverFirstName(entity.getDriverFirstName());
        dto.setDriverSurname(entity.getDriverSurname());
        if(entity.getOrder()!=null) {
            dto.setAssignedOrder(orderService.toDto(entity.getOrder()));
        }
        if(entity.getDriversTruck()!=null) {
            dto.setTruckRegNumber(entity.getDriversTruck().getRegNumber());
        }
        return dto;
    }

    @Transactional
    public DriverUserDTO getDUDtoByEmail(String email){
        Driver entity = userService.findByEmail(email).getDriver();
        return toDUDto(entity);
    }

    @Transactional
    public Map<Integer, Integer> getDriverMap(){
        List<Driver> drivers = driverDao.getAllDrivers();
        Map<Integer, Integer> map = new HashMap<>();
        for (Driver d: drivers){
            map.put(d.getDriverId(),d.getDriverPrivateNum());
        }
        return map;

    }



}
