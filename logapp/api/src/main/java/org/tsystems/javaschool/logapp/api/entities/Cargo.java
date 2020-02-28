package org.tsystems.javaschool.logapp.api.entities;


public class Cargo {
    private long cargoId;
    private String cargoName;
    private int cargoWeightKilos;
    private Status cargoStatus;

    private enum Status{
        READY, SHIPPED, DELIVERED
    }

    public long getCargoId() {
        return cargoId;
    }

    public void setCargoId(long cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public int getCargoWeightKilos() {
        return cargoWeightKilos;
    }

    public void setCargoWeightKilos(int cargoWeightKilos) {
        this.cargoWeightKilos = cargoWeightKilos;
    }

    public Status getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(Status cargoStatus) {
        this.cargoStatus = cargoStatus;
    }
}
