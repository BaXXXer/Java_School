package edu.tsystems.javaschool.logapp.api.dao;

import edu.tsystems.javaschool.logapp.api.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getAllOrders();
    int saveOrder(Order order);
    Order getOrderById(int id);
    void updateOrder(Order order);
    List<Order> getLastTenOrders();
//    void removeOrder(int id);
}
