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
    @Transactional
    public List<Order> getAllOrders() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from Order").list();

    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(order);
//        Transaction tx = session.beginTransaction();
//        tx.commit();


    }
}
