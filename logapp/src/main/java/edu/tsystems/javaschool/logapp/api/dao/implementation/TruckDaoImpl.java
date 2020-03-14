package edu.tsystems.javaschool.logapp.api.dao.implementation;


import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TruckDaoImpl implements TruckDao {

    @Override
    @Transactional
    public Truck getTruckById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.load(Truck.class, id);

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
        Truck p = session.load(Truck.class, id);
        if (null != p) {
            session.delete(p);
        }


    }


    private SessionFactory sessionFactory;


    @Autowired
    public TruckDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public TruckDaoImpl() {
    }

    @Transactional
    public List<Truck> getAllTrucks() {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("from Truck").list();
        }
    }


    @Transactional
    public void saveTruck(Truck truck) {
        try (Session session = this.sessionFactory.openSession()) {

            Transaction tx = session.beginTransaction();
            session.save(truck);
            tx.commit();
        }


    }


}
