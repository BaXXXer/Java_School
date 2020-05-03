package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.config.WebMvcConfig;
import edu.tsystems.javaschool.logapp.api.dto.CityDTO;
import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.dto.converter.DriverDtoConverter;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.entity.User;
import edu.tsystems.javaschool.logapp.api.util.WorkingHoursCalc;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class})
@WebAppConfiguration
public class DriverServiceTest {

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;


    @Autowired
    private DriverDtoConverter driverDtoConverter;


    @Autowired
    private DriverService driverService;

    @Autowired
    private TruckService truckService;


    @Test
    @Transactional
    public void countHoursForTripOverlapMonthsAndFinddrivers() throws Exception {

        // 2020-01-31 20:00 Only 4 hours left till month end
        // 2020-02-10 01:00
        LocalDateTime time1 = LocalDateTime.of(2020, 1, 31, 20, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2020, 2, 10, 1, 0, 0);

        int requiredWorkingHours = (int) WorkingHoursCalc.getRequiredWorkHoursInCurrentMonth(time1, time2);

        Assert.assertEquals(4,requiredWorkingHours);


        DriverDTO driver1 = new DriverDTO();//less than 172 hours worked, should be in result list
        driver1.setDriverFirstName("Misha");
        driver1.setDriverSurname("Popov");
        driver1.setDriverPrivateNum(98765432);
        driver1.setDriverWorkedHours(10);
        driver1.setDriverStatus(Driver.Status.DRIVING);
        driver1.setDriverCityId(1);
        driver1.setDriversTruckId(40);


        DriverDTO driver2 = new DriverDTO();//more than 172 hours worked, should not be in result list
        driver2.setDriverFirstName("Masha");
        driver2.setDriverSurname("Popova");
        driver2.setDriverPrivateNum(98765431);
        driver2.setDriverWorkedHours(175);
        driver2.setDriverStatus(Driver.Status.DRIVING);
        driver2.setDriverCityId(1);
        driver2.setDriversTruckId(40);

        CityDTO requiredCityForDriver = cityService.getCityDtoById(truckService.getTruckById(40).getCurrentCityId());
        String requiredCityName = requiredCityForDriver.getCityName();

        driverService.saveDriver(driver1);
        int lastId = driverService.getLastDriverId();
        driverService.saveDriver(driver2);
        int lastId2 = driverService.getLastDriverId();

        List<DriverDTO> freeDriversInCity = driverService.findFreeDriversInCity(2, 176 - requiredWorkingHours);//In current month we need to find driver with 176-4 = 172 worked hours
        for (DriverDTO driver : freeDriversInCity) {
            Assert.assertEquals(true,driver.getDriverWorkedHours() < 172);
            Assert.assertEquals("Chicago",requiredCityName );
        }

    }


    @Test
    @Transactional
    public void getUserByEmailWhenDriverCreated() {
        DriverDTO driver1 = new DriverDTO();
        driver1.setDriverFirstName("Misha");
        driver1.setDriverSurname("Popov");
        driver1.setDriverPrivateNum(98765432);
        driver1.setDriverWorkedHours(10);
        driver1.setDriverStatus(Driver.Status.DRIVING);
        driver1.setDriverCityId(1);
        driver1.setDriversTruckId(40);

        driverService.saveDriver(driver1);

        int driverId = driverService.getLastDriverId();

        Driver driverOutOfDB = driverDtoConverter.convertToEntity(driverService.getDriverById(driverId));

        Integer userId = driverOutOfDB.getUser().getId();

        User user = userService.findUserById(userId);

        String email = user.getEmail();

        Assert.assertEquals("misha.popov@logapp.com",email);
    }

}