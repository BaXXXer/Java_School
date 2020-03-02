package edu.tsystems.javaschool.logapp.api.dao;


import edu.tsystems.javaschool.logapp.api.entities.Truck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;

@Repository
@Transactional
public class TruckDaoImpl implements TruckDao {


    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;


    @Autowired
    @PersistenceContext
    private EntityManager entityManager;


    public TruckDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public TruckDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public TruckDaoImpl() {
    }



    public List<Truck> getAllTrucks() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Truck> truckList = session.createQuery("from Truck").list();
        session.close();
        return truckList;
    }


//    @Transactional
    public void saveTruck(Truck truck) throws IOException {
//        Session session = this.sessionFactory.getCurrentSession();


        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(truck);
        tx.commit();
        session.close();

    }


}
