package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.config.WebMvcConfig;
import edu.tsystems.javaschool.logapp.api.dto.TruckDTO;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import edu.tsystems.javaschool.logapp.api.service.TruckService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebMvcConfig.class})
@WebAppConfiguration
public class TruckControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TruckService truckService;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void contextTest() {
        ServletContext servletContext = wac.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("truckController"));
    }

    @Test
    @Transactional
    public void CanCreateAndDeleteTruckInDB() {
        TruckDTO truck = new TruckDTO();
        truck.setCondition(Truck.Condition.OK);
        truck.setCurrentCityId(5);
        truck.setCapacityTons(15);
        truck.setDriverWorkingHours(123);
        truck.setRegNumber("AA98765");

        int numOfTrucksBefore = truckService.getAllTrucks().size();
        truckService.saveTruck(truck);
        int numOfTrucksAfter = truckService.getAllTrucks().size();
        Assert.assertEquals(numOfTrucksBefore + 1, numOfTrucksAfter);
        int lastId = truckService.getLastAddedTruckIndex();
        truckService.removeTruck(lastId);
        Assert.assertEquals(numOfTrucksBefore, numOfTrucksAfter-1);
    }

    @Test
    @Transactional
    public void CanReadFromDB() {
        TruckDTO truck = new TruckDTO();
        truck.setCondition(Truck.Condition.OK);
        truck.setCurrentCityId(5);
        truck.setCapacityTons(15);
        truck.setDriverWorkingHours(123);
        truck.setRegNumber("AA98765");


        truckService.saveTruck(truck);
        int lastId = truckService.getLastAddedTruckIndex();
        Assert.assertEquals("AA98765",truckService.getTruckById(lastId).getRegNumber());
        truckService.removeTruck(lastId);
    }

    @Test
    @Transactional
    public void CanUpdateDriverInDB() {
        TruckDTO truck = new TruckDTO();
        truck.setCondition(Truck.Condition.OK);
        truck.setCurrentCityId(5);
        truck.setCapacityTons(15);
        truck.setDriverWorkingHours(123);
        truck.setRegNumber("AA98765");


        truckService.saveTruck(truck);

        int lastId = truckService.getLastAddedTruckIndex();
        TruckDTO truckToUpdate = truckService.getTruckById(lastId);
        truckToUpdate.setRegNumber("BB98765");
        truckService.updateTruck(truckToUpdate);
        Assert.assertEquals("BB98765",truckService.getTruckById(lastId).getRegNumber());
        truckService.removeTruck(lastId);



    }


}