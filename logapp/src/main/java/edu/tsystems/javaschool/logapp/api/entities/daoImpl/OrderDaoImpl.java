package edu.tsystems.javaschool.logapp.api.entities.daoImpl;

import edu.tsystems.javaschool.logapp.api.entities.Order;
import edu.tsystems.javaschool.logapp.api.entities.dao.OrderDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List <Order> orders = session.createQuery("from Order").list();
        return orders;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(order);
        tx.commit();

    }
}
