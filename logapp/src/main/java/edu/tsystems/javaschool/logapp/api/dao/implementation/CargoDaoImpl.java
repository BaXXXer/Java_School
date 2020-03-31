package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.CargoDao;
import edu.tsystems.javaschool.logapp.api.dao.WayPointsDao;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import edu.tsystems.javaschool.logapp.api.exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class CargoDaoImpl implements CargoDao {

    private SessionFactory sessionFactory;
    private WayPointsDao pointsDao;
    private static final Logger LOG = Logger.getLogger(CargoDaoImpl.class);

    @Autowired
    public CargoDaoImpl(SessionFactory sessionFactory, WayPointsDao pointsDao) {
        this.sessionFactory = sessionFactory;
        this.pointsDao = pointsDao;
    }

    @Override
    @Transactional
    public Cargo findCargoById(int id) {
        Session session = sessionFactory.getCurrentSession();
        try{

            Cargo cargo= session.load(Cargo.class, id);
        }catch(NullPointerException ex){
            LOG.error("Cargo not found" + ex.getStackTrace());
            throw new EntityNotFoundException();
        }
        return session.load(Cargo.class, id);
    }

    @Override
    public List<Cargo> getAllCargoes() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Cargo> cargoList = session.createQuery("from Cargo").list();
        return cargoList;
    }

    @Override
    @Transactional
    public List<Cargo> getNotAssignedCargoes() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Cargo> notAssignedCargoes = session.createQuery("from Cargo c join OrderWaypoint ow " +
                "where ow.cargo is null and c.currentCity.id=ow.city.id")
                .list();

        return notAssignedCargoes;
    }
}
