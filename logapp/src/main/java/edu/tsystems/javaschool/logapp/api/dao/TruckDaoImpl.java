package edu.tsystems.javaschool.logapp.api.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import edu.tsystems.javaschool.logapp.api.config.PersistenceConfig;
import edu.tsystems.javaschool.logapp.api.entities.Truck;

import java.io.IOException;
import java.util.List;

@Service
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
        List<Truck> truckList = session.createQuery("All Trucks").list();
        session.close();
        return truckList;
    }

    public void saveTruck(Truck truck) throws IOException {
        Session session = (Session) new PersistenceConfig().getSessionFactory().getObject();
        Transaction tx = session.beginTransaction();
        session.persist(truck);
        tx.commit();
        session.close();

    }


}
