package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.config.WebMvcConfig;
import edu.tsystems.javaschool.logapp.api.dto.CargoDTO;
import edu.tsystems.javaschool.logapp.api.dto.CargoWaypointDTO;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class})
@WebAppConfiguration
public class OrderWayPointServiceTest {

    @Autowired
    private OrderWayPointService pointService;

    @Autowired
    private CargoService cargoService;


    @Test
    public void getPointById() {
    }

    @Test
    public void getPointDtoById() {
    }

    @Test
    public void getNotAssignedWaypoints() {
        List<CargoWaypointDTO> allWaypoints = pointService.getAllWaypoints();
        int counter = 0;
        for (CargoWaypointDTO point : allWaypoints) {
            if (point.getOperationType().equals(OrderWaypoint.Operation.LOAD) && !point.isCompleted()) {
                counter++;
            }
        }

        Assert.assertEquals(counter, pointService.getNotAssignedWaypoints().size());

    }

    @Test
    public void getCityCoordinates() {
    }

    @Test
    public void getNotAssignedCargoes() {
        List<CargoDTO> allCargoes = cargoService.getAllCargoes();
        List<CargoWaypointDTO> allWaypoints = pointService.getAllWaypoints();
        int assignedCargoes = 0;
        int notAssignedCargoes = allCargoes.size();
        for (CargoWaypointDTO point : allWaypoints) {
            if (point.getCargo() != null) {
                notAssignedCargoes--;
            }
        }
        Assert.assertEquals(notAssignedCargoes, pointService.getNotAssignedCargoes().size());

    }

    @Test
    public void setLoadedById() {
    }
}