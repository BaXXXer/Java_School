package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.dto.DriverStatusDTO;
import edu.tsystems.javaschool.logapp.api.dto.DriverUserDTO;
import edu.tsystems.javaschool.logapp.api.dto.OrderDTO;
import edu.tsystems.javaschool.logapp.api.dto.converter.DriverDtoConverter;
import edu.tsystems.javaschool.logapp.api.dto.converter.DriverUserDtoConverter;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.entity.Driver.Status;
import edu.tsystems.javaschool.logapp.api.entity.User;
import edu.tsystems.javaschool.logapp.api.exception.DuplicateEntityException;
import edu.tsystems.javaschool.logapp.api.producer.MessageProducer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static edu.tsystems.javaschool.logapp.api.entity.Driver.Status.convertDriverStatusToEnum;

@Service
public class DriverService {

    private final DriverDao driverDao;
    private final UserService userService;
    private final DriverDtoConverter driverDtoConverter;
    private final DriverUserDtoConverter driverUserDtoConverter;
    private static final Logger LOG = Logger.getLogger(DriverService.class);
    private static final String MESSAGE = "driver changed";


    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    public DriverService(DriverDao driverDao, UserService userService, DriverDtoConverter driverDtoConverter, DriverUserDtoConverter driverUserDtoConverter) {
        this.driverDao = driverDao;
        this.userService = userService;
        this.driverDtoConverter = driverDtoConverter;
        this.driverUserDtoConverter = driverUserDtoConverter;
    }

    @Transactional
    public void saveDriver(DriverDTO driverDTO){
        for (DriverDTO d : getAllDrivers()) {
            if (driverDTO.getDriverPrivateNum().equals(d.getDriverPrivateNum())) {
                LOG.error("Driver with private number #" + d.getDriverPrivateNum() + " already exists in DB!");
                throw new DuplicateEntityException();
            }
        }

        Driver driver = driverDtoConverter.convertToEntity(driverDTO);
        driverDao.saveDriver(driver);
        messageProducer.sendMessage(MESSAGE);

        User user = createUser(driver, driverDTO.getPassword());
        userService.createUser(user);
        driver.setUser(userService.findUserById(user.getId()));
        driverDao.updateDriver(driver);
    }

    @Transactional
    public User createUser(Driver driver, String password) {
        User user = new User();
        user.setEmail(driver.getDriverFirstName().toLowerCase().trim() + "." +
                driver.getDriverSurname().toLowerCase().trim() + "@logapp.com");
        user.setRole(User.UserRole.ROLE_DRIVER);
        user.setPasswordMd5(password);//convert to sha256
        user.setDriver(driver);
        return user;
    }

    public DriverStatusDTO getDriverStatus() {
        DriverStatusDTO status = new DriverStatusDTO();
        status.setTotalDrivers(getAllDriverNumber());
        status.setDriversOnRest(getDriversOnRestNumber());
        return status;
    }

    private Long getAllDriverNumber() {
        return driverDao.getAllDriversNumber();
    }

    private Long getDriversOnRestNumber() {
        return driverDao.getDriversOnRestNumber();
    }

    @Transactional
    public int getLastDriverId() {
        int index = getAllDrivers().size() - 1;
        return getAllDrivers().get(index).getDriverId();
    }


    @Transactional
    public List<DriverDTO> getAllDrivers() {
        List<DriverDTO> dtos = new ArrayList();

        for (Driver d : driverDao.getAllDrivers()) {
            dtos.add(driverDtoConverter.convertToDTO(d));
        }
        return dtos;
    }

    @Transactional
    public DriverDTO getDriverById(int id) {
        Driver dao = driverDao.getDriverById(id);
        return driverDtoConverter.convertToDTO(dao);
    }

    @Transactional
    public void removeDriver(int id) {
        driverDao.removeDriver(id);
        messageProducer.sendMessage(MESSAGE);
    }

    @Transactional
    public void updateDriver(DriverDTO driver) {
        Driver entity = driverDtoConverter.convertToEntity(driver);
        driverDao.updateDriver(entity);
    }

    @Transactional
    public void updateDriver(Driver driver) {

        driverDao.updateDriver(driver);
        messageProducer.sendMessage(MESSAGE);
    }

    public List<DriverDTO> findFreeDriversInCity(int cityId, int maxHours) {
        List<DriverDTO> dtos = new ArrayList();
        for (Driver d : driverDao.findFreeDriversInCity(cityId, maxHours)) {
            dtos.add(driverDtoConverter.convertToDTO(d));
        }

        return dtos;
    }

    @Transactional
    public DriverUserDTO getDUDtoByEmail(String email) {
        Driver entity = userService.findByEmail(email).getDriver();
        return driverUserDtoConverter.convertToDTO(entity);
    }

    @Transactional
    public DriverUserDTO getDUDtoByEmailAndSetStatus(String email, String status) {
        Driver entity = userService.findByEmail(email).getDriver();
        if (entity.getOrder() != null) {
            List<Driver> driversOnOrder = entity.getOrder().getDriversOnOrder();
            if (status.equals("CO_DRIVER") && driversOnOrder.size() > 1) {
                Collections.shuffle(driversOnOrder);
                for (Driver driver : driversOnOrder) {
                    if (!driver.getDriverStatus().equals(Status.DRIVING) &&
                            !entity.getDriverId().equals(driver.getDriverId())) {
                        driver.setDriverStatus(Status.DRIVING);
                        break;
                    }
                }
            }
        }

        entity.setDriverStatus(convertDriverStatusToEnum(status));
        return driverUserDtoConverter.convertToDTO(entity);
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
        updateDriver(driverUserDtoConverter.convertToEntity(dto));
    }


    @Transactional
    public void setDriverOnShift(DriverUserDTO dto) {
        dto.setDriverStatus(DriverUserDTO.Status.REST_ON_SHIFT);
        updateDriver(driverUserDtoConverter.convertToEntity(dto));
    }

    public void validateGrants(DriverUserDTO driver, int id) {
        OrderDTO currentOrder = driver.getAssignedOrder();
        int assignedOrderId = currentOrder.getOrderId();
        if (assignedOrderId != id) {
            throw new AccessDeniedException("Access denied!");
        }
    }
}
