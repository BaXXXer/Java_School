package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.dao.OrderDao;
import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dao.WayPointsDao;
import edu.tsystems.javaschool.logapp.api.dto.CargoWaypointDTO;
import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.dto.OrderDTO;
import edu.tsystems.javaschool.logapp.api.dto.OrderStatusDTO;
import edu.tsystems.javaschool.logapp.api.dto.mapper.OrderMapper;
import edu.tsystems.javaschool.logapp.api.entity.*;
import edu.tsystems.javaschool.logapp.api.exception.InvalidStateException;
import edu.tsystems.javaschool.logapp.api.util.DistanceCalculator;
import edu.tsystems.javaschool.logapp.api.util.LogappConfig;
import edu.tsystems.javaschool.logapp.api.util.WorkingHoursCalc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {

    private OrderDao orderDao;
    private OrderMapper mapper;
    private DriverDao driverDao;
    private TruckDao truckDao;
    private OrderWayPointService pointService;
    private WayPointsDao wayPointsDao;
    private final HttpServletRequest request;
    private final DriverService driverService;
    private final DistanceCalculator distanceCalculator;
    private final LogappConfig appConfig;
    private final UserService userService;


    @Autowired
    public OrderService(OrderDao orderDao, OrderMapper mapper, DriverDao driverDao, TruckDao truckDao, OrderWayPointService pointService, WayPointsDao wayPointsDao, HttpServletRequest request, DriverService driverService, DistanceCalculator distanceCalculator, LogappConfig appConfig, UserService userService) {
        this.orderDao = orderDao;
        this.mapper = mapper;
        this.driverDao = driverDao;
        this.truckDao = truckDao;
        this.pointService = pointService;
        this.wayPointsDao = wayPointsDao;
        this.request = request;
        this.driverService = driverService;


        this.distanceCalculator = distanceCalculator;
        this.appConfig = appConfig;
        this.userService = userService;
    }


    @Transactional
    public void saveOrder(OrderDTO dto) throws InvalidStateException {
        Order order = toEntity(dto);
        List<Integer> pointIds = dto.getWayPointsIds();
        for (Integer id : pointIds) {
            OrderWaypoint point = pointService.getPointById(id);
            Cargo.Status cargoStatus = point.getCargo().getCargoStatus();
            OrderWaypoint.Operation operationType = point.getOperationType();
            if (operationType == OrderWaypoint.Operation.LOAD && cargoStatus == Cargo.Status.READY ||
                    operationType == OrderWaypoint.Operation.UNLOAD && cargoStatus == Cargo.Status.DELIVERED) {
                point.setOrder(order);
                orderDao.saveOrder(order);

            } else
                throw new InvalidStateException("Incorrect operation Status is "
                        + cargoStatus + " and operation is " + operationType);

        }
    }

    public Order toEntity(OrderDTO dto) {
        Order order = null;
        if (dto.getOrderId() != null) {
            order = orderDao.getOrderById(dto.getOrderId());
        } else {
            order = new Order();
        }
        List<Integer> driverIds = dto.getDriversOnOrderIds();
        List<Driver> drivers = new ArrayList();
        for (Integer id : driverIds) {
            drivers.add(driverDao.getDriverById(id));
        }
        order.setDriversOnOrder(drivers);
        order.setOrderIsDone(dto.isOrderIsDone());
        order.setTruckOnOrder(truckDao.getTruckById(dto.getTruckId()));
        if (dto.getPoints() != null) {
            List<OrderWaypoint> points = new ArrayList();
            for (CargoWaypointDTO cdto : dto.getPoints()) {
                OrderWaypoint orderWaypoint = pointService.toEntity(cdto);
                orderWaypoint.setOrder(order);
                points.add(orderWaypoint);
            }
            Set<OrderWaypoint> set = new HashSet<>(points);
            points.clear();
            points.addAll(set);//remove duplicates if any

            order.setWayPoints(points);
        }

        return order;
    }

    public OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderIsDone(order.isOrderIsDone());
        dto.setTruckId(order.getTruckOnOrder().getId());
        List<Integer> driverIds = new ArrayList<>();
        List<Driver> drivers = order.getDriversOnOrder();
        for (Driver d : drivers) {
            driverIds.add(d.getDriverId());
        }
        dto.setDriversOnOrderIds(driverIds);
        List<Integer> pointIds = new ArrayList<>();
        List<CargoWaypointDTO> pointDtos = new ArrayList<>();
        Collection<OrderWaypoint> points = order.getWayPoints();
        for (OrderWaypoint p : points) {
            pointIds.add(p.getId());
            pointDtos.add(pointService.toDto(p));
        }
        dto.setWayPointsIds(pointIds);
        dto.setPoints(pointDtos);

        return dto;
    }

    @Transactional
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> dtos = new ArrayList();

        for (Order d : orderDao.getAllOrders()) {
            dtos.add(toDto(d));
        }
        return dtos;
    }

    public List<Integer> getListOfPointIds() {
        List<Integer> points = new ArrayList<>();
        points.add(pointService.getPointById(Integer.parseInt(request.getParameter("pointFromId"))).getId());
        points.add(pointService.getPointById(Integer.parseInt(request.getParameter("pointToId"))).getId());
        return points;

    }

    public List<Integer> getListOfDriverIds() {
        List<Integer> drivers = new ArrayList<>();
        drivers.add(driverService.getDriverById(Integer.parseInt(request.getParameter("driverId1"))).getDriverId());
        drivers.add(driverService.getDriverById(Integer.parseInt(request.getParameter("driverId2"))).getDriverId());
        return drivers;

    }

    @Transactional
    public List<OrderStatusDTO> getOrderStatus() {
        List<Order> allOrders = orderDao.getAllOrders();
        List<OrderStatusDTO> status = new ArrayList<>();

        for (Order entity : allOrders) {
            OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
            orderStatusDTO.setOrderId(entity.getOrderId());
            List<OrderWaypoint> points = (List<OrderWaypoint>) entity.getWayPoints();
            List<Cargo> cargoes = new ArrayList<>();
            for (OrderWaypoint point : points) {
                cargoes.add(point.getCargo());
            }
            orderStatusDTO.setOrderIsDone(entity.isOrderIsDone());
            orderStatusDTO.setCargoes(cargoes);
            status.add(orderStatusDTO);
        }
        return status;
    }

    @Transactional
    public OrderDTO getOrderById(int id) {
        Order entity = orderDao.getOrderById(id);
        return toDto(entity);
    }

    @Transactional
    public List<Truck> getReadyToGoTrucks(OrderDTO orderDto) {
        Order order = toEntity(orderDto);
        List<Truck> readyTrucks = truckDao.getReadyToGoTrucks(order);
        List<Truck> readyToGoTrucks = new ArrayList<>();
        for (OrderDTO o : getAllOrders()) {
            if (o.getTruckId() != null) {
                readyTrucks.removeIf(t -> t.getId() == o.getTruckId());
            }
        }
        List<OrderWaypoint> points = (List<OrderWaypoint>) order.getWayPoints();
        for (OrderWaypoint point : points) {
            for (Truck t : readyTrucks) {
                if (t.getCapacityTons() * 1000 >= point.getCargo().getCargoWeightKilos()) {
                    readyToGoTrucks.add(t);
                } else {
                    continue;
                }
            }
        }
        Set<Truck> set = new HashSet<>(readyToGoTrucks);
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
        Order order = toEntity(orderDto);
        List<DriverDTO> driversForTrip = new ArrayList();
        int routeDistance = distanceCalculator.getRouteDistance(order.getWayPoints()); //cumulative distance between all the cities over all the waypoints
        double routeDuration = distanceCalculator.getRouteDuration(routeDistance, 2);//for 2 drivers
        LocalDateTime expectedArrival = LocalDateTime.now().plusHours((long) routeDuration);
        double requiredWorkingHours = WorkingHoursCalc.getRequiredWorkHoursInCurrentMonth(LocalDateTime.now(), expectedArrival);
        for (OrderWaypoint point : order.getWayPoints()) {
            driversForTrip.addAll(driverService.findFreeDriversInCity(point.getCity().getCityId(),
                    (int) (appConfig.getMaxMonthlyDutyHours() - requiredWorkingHours)));
        }

        return driversForTrip;
    }

    @Transactional
    public void assignDriver(DriverDTO driverDTO, OrderDTO orderDTO) {
        Driver driver = driverDao.getDriverById(driverDTO.getDriverId());
        driver.setUser(userService.findUserById(driver.getUser().getId()));
        Order order = toEntity(orderDTO);
        List<Driver> assignedDrivers = order.getDriversOnOrder(); //get current drivers on this order
        assignedDrivers.add(driver); // add a driver to assign
        Set<Driver> set = new HashSet<>(assignedDrivers); //remove duplicates
        assignedDrivers.clear();
        assignedDrivers.addAll(set);
        order.setDriversOnOrder(assignedDrivers);//set to entity
        driver.setOrder(order);
        orderDao.updateOrder(order);//update entity
    }

    @Transactional
    public void updateCargoStatus(int orderId, int pointId) {
        Order order = orderDao.getOrderById(orderId);
        OrderDTO orderDTO = getOrderById(orderId);
        List<CargoWaypointDTO> points = orderDTO.getPoints();
        List<CargoWaypointDTO> waypointDTOSUpdated =
                pointService.setLoadedById(points, pointId);
        List<OrderWaypoint> pointEntities = new ArrayList();//returned an array with updated cargo status
        for (CargoWaypointDTO dto : waypointDTOSUpdated) {
            pointEntities.add(pointService.toEntity(dto));
        }
        for (OrderWaypoint owpoint : pointEntities) {
            owpoint.setOrder(order);
        }
        order.setWayPoints(pointEntities);
        orderDao.updateOrder(order);
    }


    @Transactional
    public void checkIfOrderIsDone(int orderId) {
        OrderDTO orderDTO = getOrderById(orderId);
        List<CargoWaypointDTO> points = orderDTO.getPoints();
        int i = 0;
        for (CargoWaypointDTO point : points) {
            if (point.isCompleted()) {
                i++;
            }
        }
        if (points.size() == i) {
            orderDTO.setOrderIsDone(true);
        }
        orderDao.updateOrder(toEntity(orderDTO));
    }

}
