package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.dao.OrderDao;
import edu.tsystems.javaschool.logapp.api.dao.ShippingCatalogDao;
import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dto.*;
import edu.tsystems.javaschool.logapp.api.dto.converter.CargoWaypointDtoConverter;
import edu.tsystems.javaschool.logapp.api.dto.converter.CityDtoConverter;
import edu.tsystems.javaschool.logapp.api.dto.converter.DriverDtoConverter;
import edu.tsystems.javaschool.logapp.api.dto.converter.OrderDtoConverter;
import edu.tsystems.javaschool.logapp.api.entity.*;
import edu.tsystems.javaschool.logapp.api.exception.InvalidStateException;
import edu.tsystems.javaschool.logapp.api.producer.MessageProducer;
import edu.tsystems.javaschool.logapp.api.util.DistanceCalculator;
import edu.tsystems.javaschool.logapp.api.util.WorkingHoursCalc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {

    private final OrderDao orderDao;
    private final DriverDao driverDao;
    private final TruckDao truckDao;
    private final OrderWayPointService pointService;
    private final DriverService driverService;
    private final DistanceCalculator distanceCalculator;
    private final CityService cityService;
    private final TruckService truckService;
    private final CargoService cargoService;
    private final ShippingCatalogDao constantsDao;
    private final OrderDtoConverter orderConverter;
    private final CityDtoConverter cityDtoConverter;
    private final CargoWaypointDtoConverter pointConverter;
    private final DriverDtoConverter driverDtoConverter;

    @Autowired
    private MessageProducer messageProducer;


    @Autowired
    public OrderService(OrderDao orderDao, DriverDao driverDao, TruckDao truckDao,
                        OrderWayPointService pointService, DriverService driverService,
                        DistanceCalculator distanceCalculator, CityService cityService, TruckService truckService,
                        CargoService cargoService, ShippingCatalogDao constantsDao, OrderDtoConverter orderConverter, CityDtoConverter cityDtoConverter, CargoWaypointDtoConverter pointConverter, DriverDtoConverter driverDtoConverter) {
        this.orderDao = orderDao;
        this.driverDao = driverDao;
        this.truckDao = truckDao;
        this.pointService = pointService;
        this.driverService = driverService;
        this.distanceCalculator = distanceCalculator;
        this.cityService = cityService;
        this.truckService = truckService;
        this.cargoService = cargoService;
        this.constantsDao = constantsDao;
        this.orderConverter = orderConverter;
        this.cityDtoConverter = cityDtoConverter;
        this.pointConverter = pointConverter;
        this.driverDtoConverter = driverDtoConverter;
    }


    @Transactional
    public int saveOrder(OrderDTO dto) {
        Order order = orderConverter.convertToEntity(dto);
        int i = orderDao.saveOrder(order);
        messageProducer.sendMessage("orders changed");
        return i;
    }


    public List<OrderDTO> getLastTenOrders() {
        List<OrderDTO> dtos = new ArrayList();

        for (Order d : orderDao.getLastTenOrders()) {
            dtos.add(orderConverter.convertToDTO(d));
        }
        return dtos;
    }

    @Transactional
    public List<BoardOrderStatusDTO> getLastOrdersStatus() {
        List<OrderDTO> lastTenOrders = getLastTenOrders();
        List<BoardOrderStatusDTO> statusList = new ArrayList<>();
        for (OrderDTO order : lastTenOrders) {
            BoardOrderStatusDTO status = new BoardOrderStatusDTO();
            status.setOrderId(order.getOrderId());
            status.setCompleted(order.isOrderIsDone());
            List<String> drivers = new ArrayList<>();
            if (order.getDriversOnOrderIds() != null) {

                for (Integer id : order.getDriversOnOrderIds()) {
                    drivers.add(driverService.getDriverById(id).getDriverSurname());
                }

            }
            status.setDrivers(drivers);
            if (order.getTruckId() != null) {
                status.setTruck(truckService.getTruckById(order.getTruckId()).getRegNumber());
            }
            int completedPoints = 0;
            if (order.getPoints() != null) {
                status.setNumOfPointsTotal(order.getPoints().size());
                for (CargoWaypointDTO point : order.getPoints()) {
                    if (point.isCompleted()) {
                        completedPoints++;
                    }
                }
            }else{
                status.setNumOfPointsTotal(0);
            }
            status.setNumOfCompletedPoints(completedPoints);
            statusList.add(status);
        }


        return statusList;
    }

    @Transactional
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> dtos = new ArrayList();

        for (Order d : orderDao.getAllOrders()) {
            dtos.add(orderConverter.convertToDTO(d));
        }
        return dtos;
    }

    public int getLastAddedOrderId() {
        int index = getAllOrders().size() - 1;
        return getAllOrders().get(index).getOrderId();
    }

    @Transactional
    public OrderDTO getOrderById(int id) {
        Order entity = orderDao.getOrderById(id);
        return orderConverter.convertToDTO(entity);
    }

    @Transactional
    public List<TruckDTO> getReadyToGoTrucks(OrderDTO orderDto) {
        Order order = orderConverter.convertToEntity(orderDto);
        List<TruckDTO> readyTrucks = truckService.getReadyToGoTrucks();
        List<TruckDTO> readyToGoTrucks = new ArrayList<>();
        for (OrderDTO o : getAllOrders()) {
            if (o.getTruckId() != null) {
                readyTrucks.removeIf(t -> t.getId() == o.getTruckId());
            }
        }
        List<OrderWaypoint> points = (List<OrderWaypoint>) order.getWayPoints();
        int checkCounter = 0;

        for (TruckDTO t : readyTrucks) {
            for (OrderWaypoint point : points) {
                if (t.getCapacityTons() * 1000 >= point.getCargo().getCargoWeightKilos()) {
                    checkCounter++;
                }
            }
            if (checkCounter == points.size()) {
                readyToGoTrucks.add(t);
            }
            checkCounter = 0;
        }

        Set<TruckDTO> set = new HashSet<>(readyToGoTrucks);
        readyToGoTrucks.clear();
        readyToGoTrucks.addAll(set);

        return readyToGoTrucks;
    }

    @Transactional
    public Map<Integer, String> getPointMap() {

        List<Order> orders = orderDao.getAllOrders();
        Map<Integer, String> map = new HashMap<>();
        for (Order o : orders) {
            List<OrderWaypoint> points = (List<OrderWaypoint>) o.getWayPoints();
            for (OrderWaypoint ow : points) {
                map.put(ow.getId(), ow.getPointName());
            }
        }
        return map;

    }

    @Transactional
    public List<DriverDTO> findDriversForTrip(OrderDTO orderDto) {
        List<DriverDTO> driversForTrip = new ArrayList();
        double requiredWorkingHoursPerDriver = getRequiredWorkingHoursPerDriver(orderDto, null);
        for (CargoWaypointDTO point : orderDto.getPoints()) {
            driversForTrip.addAll(driverService.findFreeDriversInCity(truckDao.getTruckById(orderDto.getTruckId())
                            .getCurrentCity().getCityId(),
                    (int) (constantsDao.getConstants().getMaxWorkingHours() - requiredWorkingHoursPerDriver)));
        }
        Set<DriverDTO> set = new HashSet<>(driversForTrip);
        driversForTrip.clear();
        driversForTrip.addAll(set);
        return driversForTrip;
    }

    /**
     * Calculates the duration of trip and hours required per driver
     * CurrentCity can be null in order to find current city without assigned trucks
     *
     * @param orderDto
     * @return
     */
    @Transactional
    double getRequiredWorkingHoursPerDriver(OrderDTO orderDto, CityDTO currentCity) {
        if (currentCity == null) {
            currentCity = cityDtoConverter.convertToDto(cityService.getCityById(truckService.getTruckById(orderDto.getTruckId())
                    .getCurrentCityId()));
        }

        int routeDistance = distanceCalculator.getRouteDistance(orderDto.getPoints(), currentCity); //cumulative distance between all the cities over all the waypoints
        double routeDuration;
        if (!orderDto.getDriversOnOrderIds().isEmpty()) {
            routeDuration = distanceCalculator.getRouteHoursDuration(routeDistance, orderDto.getDriversOnOrderIds().size());
        } else {
            routeDuration = distanceCalculator.getRouteHoursDuration(routeDistance, 1);
        }

        LocalDateTime expectedArrival = LocalDateTime.now().plusHours((long) routeDuration);
        double requiredWorkingHoursTotal = WorkingHoursCalc.getRequiredWorkHoursInCurrentMonth(LocalDateTime.now(),
                expectedArrival);
        double requiredWorkingHoursPerDriver;
        if (!orderDto.getDriversOnOrderIds().isEmpty()) {
            requiredWorkingHoursPerDriver = requiredWorkingHoursTotal / (orderDto.getDriversOnOrderIds().size() + 1);
        } else {
            requiredWorkingHoursPerDriver = requiredWorkingHoursTotal;
        }
        return requiredWorkingHoursPerDriver;
    }


    /**
     * Assignes chosen driver to a current order
     *
     * @param driverDTO
     * @param orderDTO
     */
    @Transactional
    public void assignDriver(DriverDTO driverDTO, OrderDTO orderDTO) {
        Truck truckOnCurrentOrder = truckDao.getTruckById(orderDTO.getTruckId());

        if (orderDTO.getDriversOnOrderIds().isEmpty()) { //if our driver is the only one and we need to charge hours just for him
            int currentWorkedHours = driverDTO.getDriverWorkedHours();
            double requiredWorkingHoursPerDriver = getRequiredWorkingHoursPerDriver(orderDTO, null);
            currentWorkedHours += requiredWorkingHoursPerDriver;
            driverDTO.setDriverWorkedHours(currentWorkedHours);
            driverService.updateDriver(driverDtoConverter.convertToEntity(driverDTO));

        } else { //if we already have assigned driver and need to recalculate working hours for current order
            int totalWorkHoursPerCurrentOrder = truckOnCurrentOrder.getDriverWorkingHours();
            for (int id : orderDTO.getDriversOnOrderIds()) { //update working hours for all assigned drivers
                DriverDTO driverOnOrder = driverService.getDriverById(id);
                int workingHoursTotal = driverOnOrder.getDriverWorkedHours();
                double requiredWorkingHoursPerDriver = getRequiredWorkingHoursPerDriver(orderDTO, null);
                int workHoursBacked = workingHoursTotal -
                        totalWorkHoursPerCurrentOrder / orderDTO.getDriversOnOrderIds().size();//rollback
                int workerHoursUpdated = workHoursBacked + (int) Math.round(requiredWorkingHoursPerDriver);
                driverOnOrder.setDriverWorkedHours(workerHoursUpdated);
                driverService.updateDriver(driverDtoConverter.convertToEntity(driverOnOrder));
            }

            int currentWorkedHours = driverDTO.getDriverWorkedHours();
            double requiredWorkingHoursPerDriver = getRequiredWorkingHoursPerDriver(orderDTO, null);
            currentWorkedHours += requiredWorkingHoursPerDriver;
            driverDTO.setDriverWorkedHours(currentWorkedHours);
            driverService.updateDriver(driverDtoConverter.convertToEntity(driverDTO));

        }
        Driver driver = driverDao.getDriverById(driverDTO.getDriverId());
        driver.setDriversTruck(truckOnCurrentOrder);
        Order order = orderConverter.convertToEntity(orderDTO);
        List<Driver> assignedDrivers = order.getDriversOnOrder(); //get current drivers on this order
        assignedDrivers.add(driver); // add a driver to assign
        Set<Driver> set = new HashSet<>(assignedDrivers); //remove duplicates
        assignedDrivers.clear();
        assignedDrivers.addAll(set);
        order.setDriversOnOrder(assignedDrivers);//set to entity
        driver.setOrder(order);

        orderDao.updateOrder(order);//update entity

        messageProducer.sendMessage("orders changed");
    }


    /**
     * Checks if the operation type and the cargo status assigned for new order
     * are compatible
     *
     * @param dto
     */
    @Transactional
    public void updateOrder(OrderDTO dto) {
        checkTypes(dto);
        Order order = orderConverter.convertToEntity(dto);
        orderDao.updateOrder(order);

        messageProducer.sendMessage("orders changed");
    }

    private void checkTypes(OrderDTO dto) {
        if (dto.getPoints() != null) {
            List<CargoWaypointDTO> points = dto.getPoints();
            for (CargoWaypointDTO point : points) {
                if (point.getOperationType() == OrderWaypoint.Operation.LOAD && point.getCargo().getCargoStatus()
                        == Cargo.Status.READY
                        || point.getOperationType() == OrderWaypoint.Operation.UNLOAD && point.getCargo().getCargoStatus()
                        == Cargo.Status.SHIPPED) {
                    continue;
                } else {
                    throw new InvalidStateException("Incorrect operation - Cargo #" + point.getCargo().getCargoId() +
                            " status is "
                            + point.getCargo().getCargoStatus() + " and operation is " + point.getOperationType());
                }
            }
        }
    }


    /**
     * Changes the cargo status when a driver had set it
     * "Loaded" or "Unloaded"
     *
     * @param orderId
     * @param pointId
     */
    @Transactional
    public void updateCargoStatus(int orderId, int pointId) {
        Order order = orderDao.getOrderById(orderId);
        OrderDTO orderDTO = getOrderById(orderId);
        List<CargoWaypointDTO> points = orderDTO.getPoints();
        List<CargoWaypointDTO> waypointDTOSUpdated =
                pointService.setLoadedById(points, pointId);
        List<OrderWaypoint> pointEntities = new ArrayList();//returned an array with updated cargo status
        for (CargoWaypointDTO dto : waypointDTOSUpdated) {
            pointEntities.add(pointConverter.convertToEntity(dto));
        }
        for (OrderWaypoint owpoint : pointEntities) {
            owpoint.setOrder(order);
        }
        order.setWayPoints(pointEntities);
        orderDao.updateOrder(order);
        messageProducer.sendMessage("orders changed");

    }


    /**
     * Checks if all the waypoints of the order were completed
     * If yes, sets the order status to 'Completed'
     *
     * @param orderId
     */
    @Transactional
    public void checkIfOrderIsDone(int orderId) {
        OrderDTO orderDTO = getOrderById(orderId);
        List<CargoWaypointDTO> points = orderDTO.getPoints();
        int i = 0;
        for (CargoWaypointDTO point : points) {
            if (point.getCargo().getCargoStatus() == Cargo.Status.DELIVERED) {
                i++;
            }
        }
        if (points.size() == i) {
            orderDTO.setOrderIsDone(true);
            orderDao.updateOrder(orderConverter.convertToEntity(orderDTO));
            messageProducer.sendMessage("orders changed");
        }
    }


    /**
     * Takes parameters from request to assign cargo and point
     * to current order and sets it
     *
     * @param formData
     * @param cargoId
     * @param orderId
     */
    @Transactional
    public void getParamsAndSetToOrder(MultiValueMap<String, String> formData, int cargoId, int orderId) {
        CargoDTO cargo = cargoService.findCargoById(cargoId);
        String pointIdString = null;
        Collection<List<String>> values = formData.values();
        for (List<String> list : values) {
            for (String value : list) {
                if (value != null) {
                    pointIdString = value;
                    break;
                }
            }
        }
        if (pointIdString != null) {
            int pointId = Integer.parseInt(pointIdString);
            CargoWaypointDTO CWPdto = pointService.getPointDtoById(pointId);
            CWPdto.setCargo(cargo);
            OrderDTO order = getOrderById(orderId);
            List<CargoWaypointDTO> points = order.getPoints();
            points.add(CWPdto);
            order.setPoints(points);
            updateOrder(order);

        }
    }

    /**
     * Assignes chosen truck to current order
     * If there is 1 driver on order, sets the working hours per driver to current truck
     * If there is more drivers, sets the working hours per driver*number of drivers
     * and set to current truck
     *
     * @param truckDTO
     * @param orderDTO
     */

    @Transactional
    public void assignTruck(TruckDTO truckDTO, OrderDTO orderDTO) {
        Truck truck = truckDao.getTruckById(truckDTO.getId());
        Order order = orderConverter.convertToEntity(orderDTO);
        order.setTruckOnOrder(truck);
        orderDao.updateOrder(order);
        int currentWorkingHours = 0;
        CityDTO currentTruckCity = cityDtoConverter.convertToDto
                (cityService.getCityById(truckDTO.getCurrentCityId()));
        if (truckDTO.getDriverWorkingHours() != null) {
            currentWorkingHours = truckDTO.getDriverWorkingHours();
        }
        if (orderDTO.getDriversOnOrderIds().size() <= 1) {

            truckDTO.setDriverWorkingHours(currentWorkingHours +
                    (int) getRequiredWorkingHoursPerDriver(orderDTO, currentTruckCity));
        } else if (orderDTO.getDriversOnOrderIds().size() > 1) {
            truckDTO.setDriverWorkingHours(currentWorkingHours +
                    (int) getRequiredWorkingHoursPerDriver(orderDTO, currentTruckCity)
                            * orderDTO.getDriversOnOrderIds().size());//multiplies per number of drivers on current order
        }
        truckService.updateTruck(truckDTO);
        messageProducer.sendMessage("orders changed");
    }
}
