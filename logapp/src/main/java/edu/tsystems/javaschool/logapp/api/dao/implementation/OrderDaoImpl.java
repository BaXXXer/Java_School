package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.OrderDao;
import edu.tsystems.javaschool.logapp.api.entity.Order;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class OrderDaoImpl implements OrderDao {
    private SessionFactory sessionFactory;
    private static final Logger LOG = Logger.getLogger(OrderDaoImpl.class);

    @Autowired
    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Order> getLastTenOrders() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from Order order by id asc ").list();
    }

    @Override
    public List<Order> getAllOrders() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from Order order by id asc ").list();

    }

    @Override
    public int saveOrder(Order order) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(order);
        LOG.info("New order created id:" + order.getOrderId());
        return order.getOrderId();

    }

    @Override
    @Transactional
    public Order getOrderById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.load(Order.class, id);
        return session.load(Order.class, id);
    }

    @Transactional
    public void updateOrder(Order order) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(order);
        LOG.info("Order id#" + order.getOrderId() + " updated");

    }

}
