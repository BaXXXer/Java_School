package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Repository
public class DriverDaoImpl implements DriverDao {

    private SessionFactory sessionFactory;
    private static final Logger LOG = Logger.getLogger(DriverDaoImpl.class);

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
    @Transactional
    public void saveDriver(Driver driver) throws IOException {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(driver);

    }

    @Override
    @Transactional
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
    @Transactional
    public Driver getDriverById(int id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Driver driver = session.load(Driver.class, id);
        } catch (ObjectNotFoundException ex) {
            LOG.error("Driver Entity with id#" + id + " does not exist");
            throw new EntityNotFoundException();
        }
        return session.load(Driver.class, id);
    }

    @Override
    public List<Driver> findFreeDriversInCity(int cityId, int maxHours) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Driver> driverList = session.createQuery("from Driver d where driverCityId =: cityId " +
                "and driverStatus='REST' and driverWorkedHours< :maxHours and order is null and not exists (from Order o join o.driversOnOrder od " +
                "where od = d and o.orderIsDone = false)")
                .setParameter("cityId",cityId).setParameter("maxHours",maxHours)
                .list();
        return driverList;
    }
}
