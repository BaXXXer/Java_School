package edu.tsystems.javaschool.logapp.api.util;

import edu.tsystems.javaschool.logapp.api.config.WebMvcConfig;
import edu.tsystems.javaschool.logapp.api.dao.ShippingCatalogDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebMvcConfig.class})
@WebAppConfiguration
public class DistanceCalculatorTest {

    private DistanceCalculator calculator;

    @Autowired
    private ShippingCatalogDao constantsDao;

    public DistanceCalculatorTest() {
    }

    @Before
    public void createCalculator() {

        calculator = new DistanceCalculator(constantsDao);
    }


    @Test
    @Transactional
    public void checkDurationOfLongRouteWithOneDriver(){
        // 1000km: 1 driver. 8 hours (drive 640 km) + 14 hours (sleep) + 4.5 hours (360 km)
        int distanceKm = 1000;
        assertEquals(28.5, calculator.getRouteHoursDuration(distanceKm, 1));
    }

    @Test
    @Transactional
    public void calcSmallRoutes(){
        // 80km: 1 driver = 1 hour
        assertEquals(1.0, calculator.getRouteHoursDuration(80, 1));
        // 80km: 2 driver = 1 hour
        assertEquals(1.0, calculator.getRouteHoursDuration(80, 2));
    }

    @Test
    @Transactional
    public void checkDurationOfLongRouteWithTwoDrivers(){
        // 1000km: 2 drivers. 12.5 hours (drive 1000km)
        int distanceKm = 1000;
        assertEquals(12.5, calculator.getRouteHoursDuration(distanceKm, 2));
    }
}