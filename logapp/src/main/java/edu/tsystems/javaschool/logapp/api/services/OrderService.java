package edu.tsystems.javaschool.logapp.api.services;

import edu.tsystems.javaschool.logapp.api.entities.Cargo;
import edu.tsystems.javaschool.logapp.api.entities.Driver;
import edu.tsystems.javaschool.logapp.api.entities.Order;
import edu.tsystems.javaschool.logapp.api.entities.OrderWaypoint;
import edu.tsystems.javaschool.logapp.api.entities.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.entities.dao.OrderDao;
import edu.tsystems.javaschool.logapp.api.entities.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.entities.dao.WayPointsDao;
import edu.tsystems.javaschool.logapp.api.entities.dto.OrderDTO;
import edu.tsystems.javaschool.logapp.api.exceptions.InvalidStateException;
import edu.tsystems.javaschool.logapp.api.services.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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


    @Autowired
    public OrderService(OrderDao orderDao, OrderMapper mapper, DriverDao driverDao, TruckDao truckDao, OrderWayPointService pointService, WayPointsDao wayPointsDao, HttpServletRequest request, DriverService driverService) {
        this.orderDao = orderDao;
        this.mapper = mapper;
        this.driverDao = driverDao;
        this.truckDao = truckDao;
        this.pointService = pointService;
        this.wayPointsDao = wayPointsDao;
        this.request = request;
        this.driverService = driverService;


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

    private Order toEntity(OrderDTO dto) {
        Order order = new Order();
        List<Integer> driverIds = dto.getDriversOnOrderIds();
        List<Driver> drivers = new ArrayList();
        for (Integer id : driverIds) {
            drivers.add(driverDao.getDriverById(id));
        }
        order.setDriversOnOrder(drivers);
//        order.setOrderId(dto.getOrderId());
        order.setOrderIsDone(dto.isOrderIsDone());
        order.setTruckOnOrder(truckDao.getTruckById(dto.getTruckId()));
        List<Integer> waypointsIds = dto.getWayPointsIds();
        List<OrderWaypoint> points = new ArrayList();
        for (Integer id : waypointsIds) {
            points.add(wayPointsDao.getWaypointById(id));
        }
        order.setWayPoints(points);
        return order;
    }

    private OrderDTO toDto(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setTruckId(order.getTruckOnOrder().getId());
        List<Integer> driverIds = new ArrayList<>();
        List<Driver> drivers = order.getDriversOnOrder();
        for(Driver d: drivers){
            driverIds.add(d.getDriverId());
        }
        dto.setDriversOnOrderIds(driverIds);
        List<Integer>pointIds = new ArrayList<>();
        Collection<OrderWaypoint> points = order.getWayPoints();
        for(OrderWaypoint p: points){
            pointIds.add(p.getId());
        }
        dto.setWayPointsIds(pointIds);
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
}
