package edu.tsystems.javaschool.logapp.api.entities.daoImpl;

import edu.tsystems.javaschool.logapp.api.entities.OrderWaypoint;
import edu.tsystems.javaschool.logapp.api.entities.dao.WayPointsDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WayPointsDaoImpl implements WayPointsDao {
    private SessionFactory sessionFactory;

    @Autowired
    public WayPointsDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public OrderWaypoint getWaypointById(int id) {
        Session session = sessionFactory.getCurrentSession();
        OrderWaypoint point = session.load(OrderWaypoint.class, id);
        return point;
    }
}
