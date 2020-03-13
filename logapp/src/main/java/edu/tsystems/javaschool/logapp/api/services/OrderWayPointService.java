package edu.tsystems.javaschool.logapp.api.services;

import edu.tsystems.javaschool.logapp.api.entities.OrderWaypoint;
import edu.tsystems.javaschool.logapp.api.entities.dao.WayPointsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderWayPointService {

    private WayPointsDao dao;

    @Autowired
    public OrderWayPointService(WayPointsDao dao) {
        this.dao = dao;
    }

    @Transactional
    public OrderWaypoint getPointById(int id){
        return dao.getWaypointById(id);
    }

    public List<OrderWaypoint> getAllWaypoints() {
        return dao.getAllWaypoints();

    }
}
