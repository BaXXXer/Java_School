package edu.tsystems.javaschool.logapp.api.entities.dao_impl;


import edu.tsystems.javaschool.logapp.api.entities.Truck;
import edu.tsystems.javaschool.logapp.api.entities.dao.TruckDao;
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

    @Transactional
    public List<Truck> getAllTrucks() {
        try(Session session = this.sessionFactory.openSession()) {
            return session.createQuery("from Truck").list();
        }
    }


    @Transactional
    public void saveTruck(Truck truck) {
        try(Session session = this.sessionFactory.openSession()) {

            Transaction tx = session.beginTransaction();
            session.save(truck);
            tx.commit();
        }


    }


}
