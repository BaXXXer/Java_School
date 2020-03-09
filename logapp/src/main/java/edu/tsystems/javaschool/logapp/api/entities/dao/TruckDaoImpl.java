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

    @Override
    @Transactional
    public Truck getTruckById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Truck truck = session.load(Truck.class, id);
        return truck;
    }

    @Override
    @Transactional
    public void updateTruck(Truck truck) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(truck);
    }

    @Override
    @Transactional
    public void removeTruck(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Truck p = (Truck) session.load(Truck.class, id);
        if (null != p) {
            session.delete(p);
        }

    }


    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;


    @Autowired
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
        return truckList;
    }


    @Transactional
    public void saveTruck(Truck truck) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(truck);
        tx.commit();
//        session.close();

    }


}
