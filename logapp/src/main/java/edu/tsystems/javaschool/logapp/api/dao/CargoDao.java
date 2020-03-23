package edu.tsystems.javaschool.logapp.api.dao;

import edu.tsystems.javaschool.logapp.api.entity.Cargo;

public interface CargoDao{
    Cargo findCargoById(int id);
}
