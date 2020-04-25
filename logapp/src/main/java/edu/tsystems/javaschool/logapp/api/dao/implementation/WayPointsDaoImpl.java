package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.WayPointsDao;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import edu.tsystems.javaschool.logapp.api.exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WayPointsDaoImpl implements WayPointsDao {
    private SessionFactory sessionFactory;
    private static final Logger LOG = Logger.getLogger(WayPointsDaoImpl.class);

    @Autowired
    public WayPointsDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public OrderWaypoint getWaypointById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
           session.get(OrderWaypoint.class, id);
        }catch(NullPointerException ex){
            LOG.error("WayPoint not found. Id: " + id + ex.getStackTrace());
            throw new EntityNotFoundException();
        }
        session.flush();
        return session.get(OrderWaypoint.class, id);

    }

    @Override
    public List<OrderWaypoint> getAllWaypoints() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from OrderWaypoint order by id asc").list();

    }
}
