package org.tsystems.javaschool.logapp.api.entities;

import java.util.List;

public class Order {
    private long orderId;
    private boolean orderIsDone;
    private List<DestinationPoint> destinationPoints;
    private Truck truckOnOrder;
    private List <Driver> driversOnOrder;

    private static class DestinationPoint {
        private City destCity;
        private Cargo destCargo;
        private LoadType loadType;

        private enum LoadType{
            LOAD,UNLOAD
        }

        public DestinationPoint(City destCity, Cargo destCargo, LoadType loadType) {
            this.destCity = destCity;
            this.destCargo = destCargo;
            this.loadType = loadType;
        }
    }


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public boolean isOrderIsDone() {
        return orderIsDone;
    }

    public void setOrderIsDone(boolean orderIsDone) {
        this.orderIsDone = orderIsDone;
    }

    public List<DestinationPoint> getDestinationPoints() {
        return destinationPoints;
    }

    public void setDestinationPoints(List<DestinationPoint> destinationPoints) {
        this.destinationPoints = destinationPoints;
    }

    public Truck getTruckOnOrder() {
        return truckOnOrder;
    }

    public void setTruckOnOrder(Truck truckOnOrder) {
        this.truckOnOrder = truckOnOrder;
    }

    public List<Driver> getDriversOnOrder() {
        return driversOnOrder;
    }

    public void setDriversOnOrder(List<Driver> driversOnOrder) {
        this.driversOnOrder = driversOnOrder;
    }
}
