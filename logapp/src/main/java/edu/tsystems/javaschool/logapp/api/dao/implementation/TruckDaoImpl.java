package edu.tsystems.javaschool.logapp.api.dao.implementation;


import edu.tsystems.javaschool.logapp.api.dao.TruckDao;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import edu.tsystems.javaschool.logapp.api.exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TruckDaoImpl implements TruckDao {

    private SessionFactory sessionFactory;
    private static final Logger LOG = Logger.getLogger(TruckDaoImpl.class);

    @Override
    @Transactional
    public Truck getTruckById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            Truck truck = session.load(Truck.class, id);
        } catch (ObjectNotFoundException ex) {
            LOG.error("Truck Entity with id#" + id + " does not exist");
            throw new EntityNotFoundException();
        }
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


    @Autowired
    public TruckDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public TruckDaoImpl() {
    }


    public List<Truck> getAllTrucks() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from Truck").list();

    }


    public void saveTruck(Truck truck) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(truck);
    }

    public List<Truck> getReadyToGoTrucks() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("select t from Truck t " +
                "where t.condition='OK'").list();

    }


}
