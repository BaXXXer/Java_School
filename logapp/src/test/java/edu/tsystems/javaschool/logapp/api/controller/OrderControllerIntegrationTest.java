package edu.tsystems.javaschool.logapp.api.controller;

import edu.tsystems.javaschool.logapp.api.config.WebMvcConfig;
import edu.tsystems.javaschool.logapp.api.dto.*;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import edu.tsystems.javaschool.logapp.api.service.CargoService;
import edu.tsystems.javaschool.logapp.api.service.CityService;
import edu.tsystems.javaschool.logapp.api.service.OrderService;
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
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebMvcConfig.class})
@WebAppConfiguration
public class OrderControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;


    @Autowired
    private CityService cityService;

    @Autowired
    private CargoService cargoService;


    @Autowired
    private OrderService orderService;

    private DriverDTO driver;

    private List<CargoWaypointDTO> points;

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
        Assert.assertNotNull(wac.getBean("orderController"));
    }




    @Test
    @Transactional
    public void CanCreateOrderInDB() {
        driver = new DriverDTO();
        driver.setDriverFirstName("Misha");
        driver.setDriverSurname("Popov");
        driver.setDriverPrivateNum(98765432);
        driver.setDriverWorkedHours(10);
        driver.setDriverStatus(Driver.Status.DRIVING);
        driver.setDriverCityId(1);
        driver.setDriversTruckId(40);

        points = new ArrayList<>();

        CargoDTO cargo1 = cargoService.getAllCargoes().get(0);
        CityDTO city1 = cityService.getCityDtoById(1) ;//New York
        CargoWaypointDTO point1 = new CargoWaypointDTO();

        CargoDTO cargo2 = cargoService.getAllCargoes().get(1);
        CityDTO city2 = cityService.getCityDtoById(7) ;//Toronto
        CargoWaypointDTO point2 = new CargoWaypointDTO();

        point1.setCargo(cargo1);
        point1.setName("999");
        point1.setOperationType(OrderWaypoint.Operation.LOAD);
        point1.setCompleted(false);
        point1.setDestCity(city1);

        point2.setCargo(cargo2);
        point2.setName("1000");
        point2.setOperationType(OrderWaypoint.Operation.LOAD);
        point2.setCompleted(false);
        point2.setDestCity(city2);

        points.add(point1);
        points.add(point2);

        List<Integer> driverIds = new ArrayList<>();
        driverIds.add(driver.getDriverId());
        int truckId = driver.getDriversTruckId();

        OrderDTO order = new OrderDTO();
        order.setTruckId(truckId);
        order.setPoints(points);
        order.setDriversOnOrderIds(driverIds);

        int numOfOrdersBefore = orderService.getAllOrders().size();
        orderService.saveOrder(order);
        int numOfOrdersAfter = orderService.getAllOrders().size();
        Assert.assertEquals(numOfOrdersBefore + 1, numOfOrdersAfter);
    }

    @Test
    @Transactional
    public void CanReadFromDB() {
        driver = new DriverDTO();
        driver.setDriverFirstName("Misha");
        driver.setDriverSurname("Popov");
        driver.setDriverPrivateNum(98765432);
        driver.setDriverWorkedHours(10);
        driver.setDriverStatus(Driver.Status.DRIVING);
        driver.setDriverCityId(1);
        driver.setDriversTruckId(40);

        points = new ArrayList<>();

        CargoDTO cargo1 = cargoService.getAllCargoes().get(0);
        CityDTO city1 = cityService.getCityDtoById(1) ;//New York
        CargoWaypointDTO point1 = new CargoWaypointDTO();

        CargoDTO cargo2 = cargoService.getAllCargoes().get(1);
        CityDTO city2 = cityService.getCityDtoById(7) ;//Toronto
        CargoWaypointDTO point2 = new CargoWaypointDTO();

        point1.setCargo(cargo1);
        point1.setName("999");
        point1.setOperationType(OrderWaypoint.Operation.LOAD);
        point1.setCompleted(false);
        point1.setDestCity(city1);

        point2.setCargo(cargo2);
        point2.setName("1000");
        point2.setOperationType(OrderWaypoint.Operation.LOAD);
        point2.setCompleted(false);
        point2.setDestCity(city2);

        points.add(point1);
        points.add(point2);

        List<Integer> driverIds = new ArrayList<>();
        driverIds.add(driver.getDriverId());
        int truckId = driver.getDriversTruckId();

        OrderDTO order = new OrderDTO();
        order.setTruckId(truckId);
        order.setPoints(points);
        order.setDriversOnOrderIds(driverIds);


        orderService.saveOrder(order);
        int lastAddedOrderId = orderService.getLastAddedOrderId();
        OrderDTO orderOutOfDB = orderService.getOrderById(lastAddedOrderId);
        Assert.assertEquals("001",
                cargoService.getAllCargoes().get(0).getCargoName());
    }

    @Test
    @Transactional
    public void CanUpdateOrderInDB() {
        driver = new DriverDTO();
        driver.setDriverFirstName("Misha");
        driver.setDriverSurname("Popov");
        driver.setDriverPrivateNum(98765432);
        driver.setDriverWorkedHours(10);
        driver.setDriverStatus(Driver.Status.DRIVING);
        driver.setDriverCityId(1);
        driver.setDriversTruckId(40);

        points = new ArrayList<>();

        CargoDTO cargo1 = cargoService.getAllCargoes().get(0);
        CityDTO city1 = cityService.getCityDtoById(1) ;//New York
        CargoWaypointDTO point1 = new CargoWaypointDTO();

        CargoDTO cargo2 = cargoService.getAllCargoes().get(1);
        CityDTO city2 = cityService.getCityDtoById(7) ;//Toronto
        CargoWaypointDTO point2 = new CargoWaypointDTO();

        point1.setCargo(cargo1);
        point1.setName("999");
        point1.setOperationType(OrderWaypoint.Operation.LOAD);
        point1.setCompleted(false);
        point1.setDestCity(city1);

        point2.setCargo(cargo2);
        point2.setName("1000");
        point2.setOperationType(OrderWaypoint.Operation.LOAD);
        point2.setCompleted(false);
        point2.setDestCity(city2);

        points.add(point1);
        points.add(point2);

        List<Integer> driverIds = new ArrayList<>();
        driverIds.add(driver.getDriverId());
        int truckId = driver.getDriversTruckId();

        OrderDTO order = new OrderDTO();
        order.setTruckId(truckId);
        order.setPoints(points);
        order.setDriversOnOrderIds(driverIds);


        orderService.saveOrder(order);
        OrderDTO orderToUpdate = orderService.getOrderById(orderService.getLastAddedOrderId());
        order.getPoints().get(0).setOperationType(OrderWaypoint.Operation.UNLOAD);
        orderService.updateOrder(orderToUpdate);

        Assert.assertEquals(OrderWaypoint.Operation.UNLOAD,order.getPoints().get(0).getOperationType());
    }


}