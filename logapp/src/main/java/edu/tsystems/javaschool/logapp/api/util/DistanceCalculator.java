package edu.tsystems.javaschool.logapp.api.util;

import edu.tsystems.javaschool.logapp.api.dao.ShippingCatalogDao;
import edu.tsystems.javaschool.logapp.api.dto.CargoWaypointDTO;
import edu.tsystems.javaschool.logapp.api.dto.CityDTO;
import edu.tsystems.javaschool.logapp.api.entity.ShippingCatalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DistanceCalculator {

    private final ShippingCatalogDao constantsDao;

    @Autowired
    public DistanceCalculator(ShippingCatalogDao constantsDao) {
        this.constantsDao = constantsDao;
    }

    /**
     * Calculates the distance in km between two lat/long points
     * using the haversine formula
     */

    public static double getCityDistance(double lat1, double lng1,
                                         double lat2, double lng2) {
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return r * c;
    }

    /**
     * Return a route length in KM
     *
     * @param waypointCollection Waypoints
     * @return route length in KM
     */

    public Integer getRouteDistance(Collection<CargoWaypointDTO> waypointCollection, CityDTO currentCity) {


        int totalDistance = 0;
        for (CargoWaypointDTO point : waypointCollection) {
            CityDTO cityFrom = point.getCargo().getCurrentCity();
            if (!cityFrom.equals(currentCity)) {
                totalDistance += getCityDistance(cityFrom.getLat(), cityFrom.getLng(), currentCity.getLat(), currentCity.getLng());
            }
            CityDTO cityTo = point.getDestCity();
            if (!cityFrom.equals(cityTo)) {
                totalDistance += getCityDistance(cityFrom.getLat(), cityFrom.getLng(), cityTo.getLat(), cityTo.getLng());
            }
            currentCity = cityTo;
        }
        return totalDistance;

    }

    /**
     * Return aproximation of trip duration with given number of drivers.
     *
     * @param routeLength  Route length in km
     * @param numCoDrivers Number of drivers in truck
     * @return double number of total hours required to trip, include rest time.
     */

    public double getRouteHoursDuration(int routeLength, int numCoDrivers) {
        ShippingCatalog constants = constantsDao.getConstants();

        double distancePerDay = (Math.min(numCoDrivers * constants.getLimitHoursPerDay(), 24) * constants.getTruckAvgSpeed());
        return Math.floor(routeLength / distancePerDay) * 24 + (routeLength % distancePerDay) / constants.getTruckAvgSpeed();
    }

}
