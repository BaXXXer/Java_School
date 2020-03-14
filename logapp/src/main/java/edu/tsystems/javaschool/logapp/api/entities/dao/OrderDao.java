package edu.tsystems.javaschool.logapp.api.entities.dao;

import edu.tsystems.javaschool.logapp.api.entities.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getAllOrders();
    void saveOrder(Order order);
}
