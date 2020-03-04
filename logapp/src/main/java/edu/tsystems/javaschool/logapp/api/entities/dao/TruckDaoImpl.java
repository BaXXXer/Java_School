package edu.tsystems.javaschool.logapp.api.entities.dao;


import edu.tsystems.javaschool.logapp.api.entities.Truck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TruckDaoImpl implements TruckDao {


    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;


    public TruckDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public TruckDaoImpl() {
    }

    public List<Truck> getAllTrucks() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Truck> truckList = session.createQuery("from Truck").list();
//        session.close();
        return truckList;
    }


    @Transactional
    public void saveTruck(Truck truck){

        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(truck);
        tx.commit();
        session.close();

    }


}
