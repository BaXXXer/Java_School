package edu.tsystems.javaschool.logapp.api.entities.dao;

import edu.tsystems.javaschool.logapp.api.entities.OrderWaypoint;

import java.util.List;


public interface WayPointsDao {
    OrderWaypoint getWaypointById(int id);
    List<OrderWaypoint> getAllWaypoints();
}
