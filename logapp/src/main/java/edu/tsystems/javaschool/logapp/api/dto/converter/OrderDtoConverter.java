package edu.tsystems.javaschool.logapp.api.dto.converter;

import edu.tsystems.javaschool.logapp.api.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.dao.OrderDao;
import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.dto.CargoWaypointDTO;
import edu.tsystems.javaschool.logapp.api.dto.OrderDTO;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.entity.Order;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class OrderDtoConverter {

    private final CargoWaypointDtoConverter pointConverter;
    private final OrderDao orderDao;
    private final DriverDao driverDao;
    private final TruckDao truckDao;

    public OrderDtoConverter(CargoWaypointDtoConverter pointConverter, OrderDao orderDao, DriverDao driverDao, TruckDao truckDao) {
        this.pointConverter = pointConverter;
        this.orderDao = orderDao;
        this.driverDao = driverDao;
        this.truckDao = truckDao;
    }


    @Transactional
    public OrderDTO convertToDTO(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setOrderIsDone(order.isOrderIsDone());
        if (order.getTruckOnOrder() != null) {

            dto.setTruckId(order.getTruckOnOrder().getId());
        }

        if (order.getDriversOnOrder() != null) {
            List<Integer> driverIds = new ArrayList<>();
            List<Driver> drivers = order.getDriversOnOrder();
            for (Driver d : drivers) {
                driverIds.add(d.getDriverId());
            }
            dto.setDriversOnOrderIds(driverIds);

        }
        if (order.getWayPoints() != null) {
            List<Integer> pointIds = new ArrayList<>();
            List<CargoWaypointDTO> pointDtos = new ArrayList<>();
            Collection<OrderWaypoint> points = order.getWayPoints();
            for (OrderWaypoint p : points) {
                pointIds.add(p.getId());
                pointDtos.add(pointConverter.convertToDTO(p));
            }
            dto.setWayPointsIds(pointIds);
            dto.setPoints(pointDtos);
        }
        return dto;
    }

    @Transactional
    public Order convertToEntity(OrderDTO dto){
        Order order;
        if (dto.getOrderId() != null) {
            order = orderDao.getOrderById(dto.getOrderId());
        } else {
            order = new Order();
        }
        order.setOrderIsDone(dto.isOrderIsDone());
        if (dto.getDriversOnOrderIds() != null) {
            List<Integer> driverIds = dto.getDriversOnOrderIds();
            List<Driver> drivers = new ArrayList<>();
            for (Integer id : driverIds) {
                drivers.add(driverDao.getDriverById(id));
            }
            order.setDriversOnOrder(drivers);
        }
        if (dto.getTruckId() != null) {

            order.setTruckOnOrder(truckDao.getTruckById(dto.getTruckId()));
        }
        if (dto.getPoints() != null) {
            List<OrderWaypoint> points = new ArrayList<>();
            for (CargoWaypointDTO cdto : dto.getPoints()) {
                OrderWaypoint orderWaypoint = pointConverter.convertToEntity(cdto);
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
}
