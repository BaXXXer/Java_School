package edu.tsystems.javaschool.logapp.api.dao;


import edu.tsystems.javaschool.logapp.api.entities.Truck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Repository
public class TruckDaoImpl implements TruckDao {


    @Autowired
    SessionFactory sessionFactory;


//    public TruckDaoImpl(SessionFactory sessionFactory) {
//        setSessionFactory(sessionFactory);
//    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public TruckDaoImpl() {
    }

//    protected Session getCurrentSession() {
//
//        return sessionFactory.getSessionFactory;
//    }

    @SuppressWarnings("unchecked")
    public List<Truck> getAllTrucks() {
        Session session = sessionFactory.openSession();
        List<Truck> truckList = session.createQuery("from Truck").list();
        session.close();
        return truckList;
    }

    @Transactional
    public void saveTruck(Truck truck) throws IOException {
//        System.out.println(sessionFactory.get);
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.persist(truck);
        tx.commit();
        session.close();


    }


}
