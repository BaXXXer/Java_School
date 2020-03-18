package edu.tsystems.javaschool.logapp.api.dao;

import edu.tsystems.javaschool.logapp.api.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getAllOrders();
    void saveOrder(Order order);
    Order getOrderById(int id);
    public void updateOrder(Order order);
//    void removeOrder(int id);
}
