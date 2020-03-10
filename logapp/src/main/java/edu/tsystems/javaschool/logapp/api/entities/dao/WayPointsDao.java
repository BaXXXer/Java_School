package edu.tsystems.javaschool.logapp.api.entities.dao;

import edu.tsystems.javaschool.logapp.api.entities.OrderWaypoint;
import org.springframework.stereotype.Repository;

@Repository
public interface WayPointsDao {
    OrderWaypoint getWaypointById(int id);
}
