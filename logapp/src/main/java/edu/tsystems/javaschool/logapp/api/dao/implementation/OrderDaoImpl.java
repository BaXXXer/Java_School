package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.OrderDao;
import edu.tsystems.javaschool.logapp.api.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class OrderDaoImpl implements OrderDao {
    private SessionFactory sessionFactory;

    @Autowired
    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Order> getAllOrders() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from Order").list();

    }

    @Override
    public void saveOrder(Order order) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(order);
    }

    @Override
    @Transactional
    public Order getOrderById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.load(Order.class, id);
        return order;
    }

    @Transactional
    public void updateOrder(Order order){
        Session session = this.sessionFactory.getCurrentSession();
        session.update(order);
    }

//    @Override
//    public void removeOrder(int id) {
//        Session session = this.sessionFactory.getCurrentSession();
//        Order order = session.load(Order.class, id);
//            order.getWayPoints().removeIf((OrderWaypoint point) -> {
//                if(point.getId())
//            })
//
//            if (null != p) {
//        session.delete(p);
//    }
//}
}
