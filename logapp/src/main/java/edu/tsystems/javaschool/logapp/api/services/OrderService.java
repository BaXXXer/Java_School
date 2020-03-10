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

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private OrderDao orderDao;
    private OrderMapper mapper;
    private DriverDao driverDao;
    private TruckDao truckDao;
    private WayPointsDao wayPointsDao;

    @Autowired
    public OrderService(OrderDao orderDao, OrderMapper mapper, DriverDao driverDao, TruckDao truckDao, WayPointsDao wayPointsDao) {
        this.orderDao = orderDao;
        this.mapper = mapper;
        this.driverDao = driverDao;
        this.truckDao = truckDao;
        this.wayPointsDao = wayPointsDao;
    }



    public void saveOrder(OrderDTO dto) throws InvalidStateException {
        Order order = toEntity(dto);
        List<OrderWaypoint> orderWaypointList = order.getWayPoints();
        for (OrderWaypoint point : orderWaypointList) {
            Cargo.Status cargoStatus = point.getCargo().getCargoStatus();
            OrderWaypoint.Operation operationType = point.getOperationType();
            if (operationType == OrderWaypoint.Operation.LOAD && cargoStatus == Cargo.Status.READY ||
                    operationType == OrderWaypoint.Operation.UNLOAD && cargoStatus == Cargo.Status.DELIVERED) {

                orderDao.saveOrder(order);
            } else
                throw new InvalidStateException("Incorrect operation Status is " + cargoStatus + " and operation is " + operationType);

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
        order.setOrderId(dto.getOrderId());
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

    @Transactional
    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> dtos = new ArrayList();

        for (Order d : orderDao.getAllOrders()) {
            dtos.add(mapper.toDto(d));
        }
        return dtos;
    }
}
