package edu.tsystems.javaschool.logapp.api.util;

import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MaxPayloadCalculator {
    /**
     * Returns maximum summary carried weight for segment along the route.
     *
     * @param waypointCollection List of waypoints for given route
     * @return Maximum weight carried for single segment (in kg)
     */
    public Integer getMaxPayload(Collection<OrderWaypoint> waypointCollection) {
        int maxPayload = 0;
        Set<Cargo> cargoOnBoard = new HashSet<>();
        for (OrderWaypoint p : waypointCollection) {
            if (p.getOperationType() == OrderWaypoint.Operation.LOAD) {
                cargoOnBoard.add(p.getCargo());
            } else if (p.getOperationType() == OrderWaypoint.Operation.UNLOAD) {
                cargoOnBoard.remove(p.getCargo());
            }
            maxPayload = Math.max(
                    cargoOnBoard.stream().mapToInt(Cargo::getCargoWeightKilos).sum(),
                    maxPayload);
        }
        return maxPayload;
    }
}
