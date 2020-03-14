package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.WayPointsDao;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
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
