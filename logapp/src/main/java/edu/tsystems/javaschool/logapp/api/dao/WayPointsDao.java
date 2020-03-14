package edu.tsystems.javaschool.logapp.api.dao;

import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;

import java.util.List;


public interface WayPointsDao {
    OrderWaypoint getWaypointById(int id);
    List<OrderWaypoint> getAllWaypoints();
}
