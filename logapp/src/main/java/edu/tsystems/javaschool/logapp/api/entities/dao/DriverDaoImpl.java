package edu.tsystems.javaschool.logapp.api.entities.dao;

import edu.tsystems.javaschool.logapp.api.entities.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Repository
@Transactional
public class DriverDaoImpl implements DriverDao {
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Autowired
    public DriverDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public DriverDaoImpl() {
    }

    @Override
    public List<Driver> getAllDrivers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Driver> driverList = session.createQuery("from Driver").list();
        return driverList;
    }

    @Override
    public void saveDriver(Driver driver) throws IOException {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(driver);
        tx.commit();
        session.close();

    }

    @Override
    public void updateDriver(Driver driver) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(driver);
    }

    @Override
    public void removeDriver(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Driver p = session.load(Driver.class, id);
        if (null != p) {
            session.delete(p);
        }

    }

    @Override
    public Driver getDriverById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Driver driver = session.load(Driver.class, id);
        return driver;
    }
}
