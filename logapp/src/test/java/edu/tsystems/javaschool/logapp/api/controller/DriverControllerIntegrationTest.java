package edu.tsystems.javaschool.logapp.api.controller;


import edu.tsystems.javaschool.logapp.api.config.WebMvcConfig;
import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.service.DriverService;
import edu.tsystems.javaschool.logapp.api.service.UserService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebMvcConfig.class})
@WebAppConfiguration
public class DriverControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserService userService;

    @Autowired
    private DriverService driverService;

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
        Assert.assertNotNull(wac.getBean("driverController"));
    }


    @Test
    public void showForm() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/drivers/addDriver")).andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("drivers/addNewDriver"));

    }


    @Test
    public void getAllDrivers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/drivers/allDrivers")).andDo(print())
                .andExpect(MockMvcResultMatchers.view().name("drivers/allDrivers"));
    }

    @Test
    @Transactional
    public void CanCreateAndDeleteDriverInDB() {
        DriverDTO driver = new DriverDTO();
        driver.setDriverFirstName("Misha");
        driver.setDriverSurname("Popov");
        driver.setDriverPrivateNum(98765432);
        driver.setDriverWorkedHours(10);
        driver.setDriverStatus(Driver.Status.DRIVING);

        driver.setDriverCityId(1);

        driver.setDriversTruckId(40);

        int numOfDriversBefore = driverService.getAllDrivers().size();
        driverService.saveDriver(driver);
        int numOfDriversAfter = driverService.getAllDrivers().size();
        int lastId = driverService.getLastDriverId();
        Assert.assertEquals(numOfDriversBefore + 1, numOfDriversAfter);

        driverService.removeDriver(lastId);
        Assert.assertEquals(numOfDriversBefore, numOfDriversAfter-1);
    }

    @Test
    @Transactional
    public void CanReadFromDB() {
        DriverDTO driver = new DriverDTO();
        driver.setDriverFirstName("Misha");
        driver.setDriverSurname("Popov");
        driver.setDriverPrivateNum(98765432);
        driver.setDriverWorkedHours(10);
        driver.setDriverStatus(Driver.Status.DRIVING);

        driver.setDriverCityId(1);

        driver.setDriversTruckId(40);

        driverService.saveDriver(driver);
        int lastId = driverService.getLastDriverId();
        Assert.assertEquals("Misha",driverService.getDriverById(lastId).getDriverFirstName() );
    }

    @Test
    @Transactional
    public void CanUpdateDriverInDB() {
        DriverDTO driver = new DriverDTO();
        driver.setDriverFirstName("Misha");
        driver.setDriverSurname("Popov");
        driver.setDriverPrivateNum(98765432);
        driver.setDriverWorkedHours(10);
        driver.setDriverStatus(Driver.Status.DRIVING);

        driver.setDriverCityId(1);

        driver.setDriversTruckId(40);

        driverService.saveDriver(driver);
        int lastId = driverService.getLastDriverId();
        DriverDTO driverToUpdate = driverService.getDriverById(lastId);
        driverToUpdate.setDriverFirstName("Masha");
        driverService.updateDriver(driverToUpdate);
        Assert.assertEquals("Masha",driverService.getDriverById(lastId).getDriverFirstName());
        driverService.removeDriver(lastId);



    }





}




