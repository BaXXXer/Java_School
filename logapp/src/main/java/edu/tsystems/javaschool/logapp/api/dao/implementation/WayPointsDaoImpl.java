package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.WayPointsDao;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WayPointsDaoImpl implements WayPointsDao {
    private SessionFactory sessionFactory;

    @Autowired
    public WayPointsDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public OrderWaypoint getWaypointById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        OrderWaypoint orderWaypoint = session.get(OrderWaypoint.class, id);
        session.flush();
        return orderWaypoint;

    }

    @Override
    public List<OrderWaypoint> getAllWaypoints() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from OrderWaypoint ").list();

    }
}
