package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.dto.DriverUserDTO;
import edu.tsystems.javaschool.logapp.api.dto.mapper.DriverMapper;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.entity.User;
import edu.tsystems.javaschool.logapp.api.exception.EntityNotFoundException;
import edu.tsystems.javaschool.logapp.api.util.EnumConverter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

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
    public void saveDriver(DriverDTO driverDTO) throws EntityNotFoundException, IOException {
        Driver driver = toEntity(driverDTO);
        driver.setDriversTruck(truckDao.getTruckById(driverDTO.getDriversTruckId()));
        driverDao.saveDriver(driver);
        User user = createUser(driver);
        userService.createUser(user);
        driver.setUser(userService.findUserById(user.getId()));
        driverDao.updateDriver(driver);
    }

    @Transactional
    User createUser(Driver driver) {
        User user = new User();
        user.setEmail(driver.getDriverFirstName().toLowerCase() + "." + driver.getDriverSurname().toLowerCase() + "@logapp.com");
        user.setRole(User.UserRole.ROLE_DRIVER);
        user.setPasswordMd5("password");
        user.setDriver(driver);
        return user;

    }

    public Driver toEntity(DriverDTO driverDTO) {
        try {
            Driver driver = driverDao.getDriverById(driverDTO.getDriverId());
            driver.setDriverSurname(driverDTO.getDriverSurname());
            driver.setDriverFirstName(driverDTO.getDriverFirstName());
            driver.setDriverPrivateNum(driverDTO.getDriverPrivateNum());
            driver.setDriverWorkedHours(driverDTO.getDriverWorkedHours());
            driver.setDriverStatus(driverDTO.getDriverStatus());
            driver.setDriverCityId(driverDTO.getDriverCityId());
            driver.setDriversTruck(truckDao.getTruckById(driverDTO.getDriversTruckId()));
            return driver;


        } catch (ObjectNotFoundException ex) {
            Driver driver = new Driver();
            driver.setDriverSurname(driverDTO.getDriverSurname());
            driver.setDriverFirstName(driverDTO.getDriverFirstName());
            driver.setDriverPrivateNum(driverDTO.getDriverPrivateNum());
            driver.setDriverWorkedHours(driverDTO.getDriverWorkedHours());
            driver.setDriverStatus(driverDTO.getDriverStatus());
            driver.setDriverCityId(driverDTO.getDriverCityId());
            driver.setDriversTruck(truckDao.getTruckById(driverDTO.getDriversTruckId()));
            return driver;

        }
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

    @Transactional
    public void updateDriver(Driver driver) {
        driverDao.updateDriver(driver);
    }

    public List<DriverDTO> findFreeDriversInCity(int cityId, int maxHours) {
        List<DriverDTO> dtos = new ArrayList();
        for (Driver d : driverDao.findFreeDriversInCity(cityId, maxHours)) {
            dtos.add(toDto(d));
        }

        return dtos;
    }

    /**
     * Converts Driver entity to DriverUserDTO
     *
     * @param entity
     * @return DriverUserDTO
     */
    public DriverUserDTO toDUDto(Driver entity) {
        DriverUserDTO dto = new DriverUserDTO();
        dto.setDriverId(entity.getDriverId());
        dto.setDriverPrivateNum(entity.getDriverPrivateNum());
        switch (entity.getDriverStatus()) {
            case DRIVING:
                dto.setDriverStatus(DriverUserDTO.Status.DRIVING);
                break;
            case REST:
                dto.setDriverStatus(DriverUserDTO.Status.REST);
                break;
            case REST_ON_SHIFT:
                dto.setDriverStatus(DriverUserDTO.Status.REST_ON_SHIFT);
                break;
            case CARGO_HANDLING:
                dto.setDriverStatus(DriverUserDTO.Status.CARGO_HANDLING);
                break;
            case CO_DRIVER:
                dto.setDriverStatus(DriverUserDTO.Status.CO_DRIVER);
                break;
        }
        dto.setDriverFirstName(entity.getDriverFirstName());
        dto.setDriverSurname(entity.getDriverSurname());
        if (entity.getOrder() != null) {
            dto.setAssignedOrder(orderService.toDto(entity.getOrder()));
        }
        if (entity.getDriversTruck() != null) {
            dto.setTruckRegNumber(entity.getDriversTruck().getRegNumber());
        }
        return dto;
    }

    @Transactional
    public Driver fromDUDtoToEntity(DriverUserDTO dto) {
        Driver entity = driverDao.getDriverById(dto.getDriverId());
        switch (dto.getDriverStatus()) {
            case DRIVING:
                entity.setDriverStatus(Driver.Status.DRIVING);
                break;
            case REST:
                entity.setDriverStatus(Driver.Status.REST);
                break;
            case CO_DRIVER:
                entity.setDriverStatus(Driver.Status.CO_DRIVER);
                break;
            case CARGO_HANDLING:
                entity.setDriverStatus(Driver.Status.CARGO_HANDLING);
                break;
            case REST_ON_SHIFT:
                entity.setDriverStatus(Driver.Status.REST_ON_SHIFT);
                break;
        }
        return entity;

    }


    @Transactional
    public DriverUserDTO getDUDtoByEmail(String email) {
        Driver entity = userService.findByEmail(email).getDriver();
        return toDUDto(entity);
    }

    @Transactional
    public DriverUserDTO getDUDtoByEmailAndSetStatus(String email, String status) {
        Driver entity = userService.findByEmail(email).getDriver();
        List<Driver> driversOnOrder = entity.getOrder().getDriversOnOrder();
        if (status.equals("CO_DRIVER") && driversOnOrder.size() > 1) {
            Collections.shuffle(driversOnOrder);
            for (Driver driver : driversOnOrder) {
                if (!driver.getDriverStatus().equals(Driver.Status.DRIVING) && entity.getDriverId() != driver.getDriverId()) {
                    driver.setDriverStatus(Driver.Status.DRIVING);
                    break;
                }
            }
        }
        entity.setDriverStatus(EnumConverter.convertDriverStatusToEnum(status));
        return toDUDto(entity);
    }


    @Transactional
    public Map<Integer, Integer> getDriverMap() {
        List<Driver> drivers = driverDao.getAllDrivers();
        Map<Integer, Integer> map = new HashMap<>();
        for (Driver d : drivers) {
            map.put(d.getDriverId(), d.getDriverPrivateNum());
        }
        return map;
    }

    @Transactional
    public void setDriverOnRest(DriverUserDTO dto) {
        dto.setDriverStatus(DriverUserDTO.Status.REST);
        updateDriver(fromDUDtoToEntity(dto));
    }


    @Transactional
    public void setDriverOnShift(DriverUserDTO dto) {
        dto.setDriverStatus(DriverUserDTO.Status.REST_ON_SHIFT);
        updateDriver(fromDUDtoToEntity(dto));
    }
}
