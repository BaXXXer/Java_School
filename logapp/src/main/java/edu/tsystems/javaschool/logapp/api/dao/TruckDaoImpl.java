package edu.tsystems.javaschool.logapp.api.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import edu.tsystems.javaschool.logapp.api.config.PersistenceConfig;
import edu.tsystems.javaschool.logapp.api.entities.Truck;

import java.io.IOException;
import java.util.List;

@Repository
public class TruckDaoImpl implements TruckDao {


    @Autowired
    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public TruckDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public TruckDaoImpl() {
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public List<Truck> getAllTrucks() {
        Session session = sessionFactory.openSession();
        List<Truck> truckList = session.createQuery("from Truck").list();
        session.close();
        return truckList;
    }

    public void saveTruck(Truck truck) throws IOException {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.persist(truck);
        tx.commit();
        session.close();

    }


}
