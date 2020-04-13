package controller;

import dto.DriverDTO;
import service.DriverEJB;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.annotation.WebServlet;
import java.util.List;

@Named(value = "driverController")
@RequestScoped
@ManagedBean
public class DriverController {

    @EJB
    DriverEJB driverEJB;

    public List<DriverDTO> getSavedDriver(){
        return driverEJB.getSavedDriver();
    }

    public String viewDrivers(){
        return "home.xhtml";
    }

}
