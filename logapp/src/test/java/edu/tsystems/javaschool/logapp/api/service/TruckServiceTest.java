package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.config.WebMvcConfig;
import edu.tsystems.javaschool.logapp.api.dto.TruckDTO;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebMvcConfig.class})
@WebAppConfiguration
public class TruckServiceTest {

    @Autowired
    private TruckService truckService;


    @Test
    @Transactional
    public void getReadyToGoTrucks() {
        int initialReadyTruckNum = truckService.getReadyToGoTrucks().size();

        TruckDTO truck = new TruckDTO(); //Must be in result list
        truck.setCondition(Truck.Condition.OK);
        truck.setCurrentCityId(5);
        truck.setCapacityTons(15);
        truck.setDriverWorkingHours(123);
        truck.setRegNumber("AA98765");
        truckService.saveTruck(truck);

        TruckDTO truck1 = new TruckDTO(); //Must not be in result list
        truck1.setCondition(Truck.Condition.BROKEN);
        truck1.setCurrentCityId(5);
        truck1.setCapacityTons(15);
        truck1.setDriverWorkingHours(123);
        truck1.setRegNumber("AA98764");
        truckService.saveTruck(truck1);



        int updatedReadyTruckNum = truckService.getReadyToGoTrucks().size();

        Assert.assertEquals(initialReadyTruckNum+1,updatedReadyTruckNum);




    }
}