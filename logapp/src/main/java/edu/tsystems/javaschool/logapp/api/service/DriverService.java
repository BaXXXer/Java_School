package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.dto.DriverStatusDTO;
import edu.tsystems.javaschool.logapp.api.dto.DriverUserDTO;
import edu.tsystems.javaschool.logapp.api.dto.OrderDTO;
import edu.tsystems.javaschool.logapp.api.dto.mapper.DriverMapper;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.entity.Driver.Status;
import edu.tsystems.javaschool.logapp.api.entity.User;
import edu.tsystems.javaschool.logapp.api.exception.DuplicateEntityException;
import edu.tsystems.javaschool.logapp.api.exception.EntityNotFoundException;
import edu.tsystems.javaschool.logapp.api.producer.MessageProducer;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static edu.tsystems.javaschool.logapp.api.entity.Driver.Status.convertDriverStatusToEnum;

@Service
public class DriverService {

    private DriverDao driverDao;
    private final DriverMapper mapper;
    private TruckDao truckDao;
    private UserService userService;

    private static final Logger LOG = Logger.getLogger(DriverService.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    public DriverService(DriverDao driverDao, DriverMapper mapper, TruckDao truckDao, UserService userService) {
        this.driverDao = driverDao;
        this.mapper = mapper;
        this.truckDao = truckDao;
        this.userService = userService;
    }

    @Transactional
    public void saveDriver(DriverDTO driverDTO) throws EntityNotFoundException {
        for(DriverDTO d:getAllDrivers()){
            if(driverDTO.getDriverPrivateNum().equals(d.getDriverPrivateNum())){
                LOG.error("Driver with private number #" + d.getDriverPrivateNum()+ " already exists in DB!");
                throw new DuplicateEntityException();
            }
        }

        Driver driver = toEntity(driverDTO);
        driverDao.saveDriver(driver);
        messageProducer.sendMessage("drivers changed");

        User user = createUser(driver,driverDTO.getPassword());
        userService.createUser(user);
        driver.setUser(userService.findUserById(user.getId()));
        driverDao.updateDriver(driver);
    }

    @Transactional
    User createUser(Driver driver,String password) {
        User user = new User();
        user.setEmail(driver.getDriverFirstName().toLowerCase().trim() + "." +
                driver.getDriverSurname().toLowerCase().trim() + "@logapp.com");
        user.setRole(User.UserRole.ROLE_DRIVER);
        user.setPasswordMd5(password);
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
            if(driverDTO.getDriversTruckId() != null){
                driver.setDriversTruck(truckDao.getTruckById(driverDTO.getDriversTruckId()));
            }
            return driver;


        } catch (ObjectNotFoundException ex) {
            LOG.info("driver was not found! id: " +driverDTO.getDriverId());
            Driver driver = new Driver();
            driver.setDriverSurname(driverDTO.getDriverSurname());
            driver.setDriverFirstName(driverDTO.getDriverFirstName());
            driver.setDriverPrivateNum(driverDTO.getDriverPrivateNum());
            driver.setDriverWorkedHours(driverDTO.getDriverWorkedHours());
            if (driverDTO.getDriverStatus() == null) {
                driver.setDriverStatus(Status.REST);
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

    public DriverStatusDTO getDriverStatus() {
        DriverStatusDTO status = new DriverStatusDTO();
        status.setTotalDrivers(getAllDriverNumber());
        status.setDriversOnRest(getDriversOnRestNumber());
        return status;
    }

    private Long    getAllDriverNumber(){
        return driverDao.getAllDriversNumber();
    }

    private Long getDriversOnRestNumber(){
        return driverDao.getDriversOnRestNumber();
    }

    public int getLastDriverId(){
        int index = getAllDrivers().size()-1;
        return getAllDrivers().get(index).getDriverId();
    }

    public DriverDTO toDto(Driver entity) {
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
        messageProducer.sendMessage("drivers changed");
    }

    @Transactional
    public void updateDriver(DriverDTO driver) {
        Driver entity = toEntity(driver);
        driverDao.updateDriver(entity);
    }

    @Transactional
    public void updateDriver(Driver driver){

        driverDao.updateDriver(driver);
        messageProducer.sendMessage("drivers changed");
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

        DriverUserDTO.Status status = Status.switchFromDriverToDtoStatus(entity.getDriverStatus());
        dto.setDriverStatus(status);

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
        Status status = Status.switchFromDtoToStatus(dto.getDriverStatus());
        entity.setDriverStatus(status);
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
        if(entity.getOrder()!=null){
            List<Driver> driversOnOrder = entity.getOrder().getDriversOnOrder();
            if (status.equals("CO_DRIVER") && driversOnOrder.size() > 1) {
                Collections.shuffle(driversOnOrder);
                for (Driver driver : driversOnOrder) {
                    if (!driver.getDriverStatus().equals(Status.DRIVING) &&
                            entity.getDriverId() != driver.getDriverId()) {
                        driver.setDriverStatus(Status.DRIVING);
                        break;
                    }
                }
            }
        }

        entity.setDriverStatus(convertDriverStatusToEnum(status));
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

    public void validateGrants(DriverUserDTO driver, int id) {
        OrderDTO currentOrder = driver.getAssignedOrder();
        int assignedOrderId = currentOrder.getOrderId();
        if(assignedOrderId!=id){
            throw new AccessDeniedException("Access denied!");
        }
    }
}
