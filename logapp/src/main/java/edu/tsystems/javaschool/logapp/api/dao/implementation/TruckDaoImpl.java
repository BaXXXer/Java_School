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
    public long getAllTrucksNumber() {
        Session session = this.sessionFactory.openSession();
        Long num = (Long) session.createQuery("select count (*) from Truck").uniqueResult();
        session.close();
        return num;
    }

    @Override
    public long getBrokenTrucksNumber() {
        Session session = this.sessionFactory.openSession();
        Long num = (Long) session.createQuery("select count (*) from Truck where condition='BROKEN'").uniqueResult();
        session.close();
        return num;
    }

    @Override
    public long getTrucksOnOrderNumber() {
        Session session = this.sessionFactory.openSession();
        Long num = (Long) session.createQuery("select count (*) from Order where truckOnOrder is not null").uniqueResult();
        session.close();
        return num;
    }

    @Override
    @Transactional
    public Truck getTruckById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Truck truck = session.load(Truck.class, id);
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
        return session.createQuery("from Truck order by id asc").list();

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
