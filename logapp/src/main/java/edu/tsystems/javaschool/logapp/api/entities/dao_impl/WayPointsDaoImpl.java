package edu.tsystems.javaschool.logapp.api.entities.dao_impl;

import edu.tsystems.javaschool.logapp.api.entities.OrderWaypoint;
import edu.tsystems.javaschool.logapp.api.entities.dao.WayPointsDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class WayPointsDaoImpl implements WayPointsDao {
    private SessionFactory sessionFactory;

    @Autowired
    public WayPointsDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public OrderWaypoint getWaypointById(int id) {
        try(Session session = this.sessionFactory.openSession()) {
            return session.load(OrderWaypoint.class, id);
        }
    }

    @Override
    public List<OrderWaypoint> getAllWaypoints() {
        try(Session session = this.sessionFactory.openSession()) {
            return session.createQuery("from OrderWaypoint ").list();
        }
    }
}
