package edu.tsystems.javaschool.logapp.api.dao;

import edu.tsystems.javaschool.logapp.api.entity.Cargo;

import java.util.List;

public interface CargoDao{
    Cargo findCargoById(int id);
    List<Cargo> getAllCargoes();
    List<Cargo> getNotAssignedCargoes();

}
