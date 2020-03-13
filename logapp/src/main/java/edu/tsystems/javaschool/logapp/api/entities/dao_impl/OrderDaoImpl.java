package edu.tsystems.javaschool.logapp.api.entities.dao_impl;

import edu.tsystems.javaschool.logapp.api.entities.Order;
import edu.tsystems.javaschool.logapp.api.entities.dao.OrderDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
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
    @Transactional
    public void saveOrder(Order order) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(order);
//        Transaction tx = session.beginTransaction();
//        tx.commit();


    }
}
