package edu.tsystems.javaschool.logapp.api.controller.primefaces;

import edu.tsystems.javaschool.logapp.api.entities.Truck;
import edu.tsystems.javaschool.logapp.api.services.TruckService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name = "allTrucks")
@SessionScoped
public class PrimefacesBean {
    private List<Truck> trucks;

    @Autowired
    private TruckService service;

    public PrimefacesBean(List<Truck> trucks) {
        trucks=service.getAllTrucks();
    }

    public List<Truck> getTrucks() {
        return trucks;
    }
}
