package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.config.WebMvcConfig;
import edu.tsystems.javaschool.logapp.api.dto.*;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class})
@WebAppConfiguration
public class OrderServiceTest {

    @Autowired
    private TruckService truckService;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private CityService cityService;

    @Autowired
    private OrderService orderService;

    private List<CargoWaypointDTO> points;




    @Test
    @Transactional
    public void testGetReadyToGoTrucks() {
        points = new ArrayList<>();

        CargoDTO cargo1 = cargoService.getAllCargoes().get(0);
        int weight1 = cargo1.getCargoWeightKilos(); //10000 kg
        CityDTO city1 = cityService.getCityDtoById(1) ;//New York
        CargoWaypointDTO point1 = new CargoWaypointDTO();

        CargoDTO cargo2 = cargoService.getAllCargoes().get(1);
        int weight2 = cargo1.getCargoWeightKilos(); //8000 kg
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

        TruckDTO truck = new TruckDTO(); //Must be in result list
        truck.setCondition(Truck.Condition.OK);
        truck.setCurrentCityId(5);
        truck.setCapacityTons(15);
        truck.setDriverWorkingHours(123);
        truck.setRegNumber("AA98765");
        truckService.saveTruck(truck);

        int lastIndex1 = truckService.getLastAddedTruckIndex();
        TruckDTO truckFromDB = truckService.getTruckById(lastIndex1);

        OrderDTO order = new OrderDTO();
        order.setPoints(points);
        orderService.saveOrder(order);
        int lastAddedOrderId = orderService.getLastAddedOrderId();
        OrderDTO orderFromDB = orderService.getOrderById(lastAddedOrderId);

        Assert.assertEquals(orderService.getReadyToGoTrucks(orderFromDB).contains(truckFromDB),true);

        OrderDTO order2 = new OrderDTO(); //Second order, which sets truck to order. So now we expect false.(truck has order assigned)
        order.setPoints(points);
        order2.setTruckId(lastIndex1);
        orderService.saveOrder(order2);
        int lastAddedOrder2Id = orderService.getLastAddedOrderId();
        OrderDTO order2FromDB = orderService.getOrderById(lastAddedOrder2Id);

        Assert.assertEquals(false,orderService.getReadyToGoTrucks(order2FromDB).contains(truckFromDB)); //false as the truck is assigned

        TruckDTO truck1 = new TruckDTO(); //Must not be in result list (low capacity)
        truck1.setCondition(Truck.Condition.OK);
        truck1.setCurrentCityId(5);
        truck1.setCapacityTons(7);
        truck1.setDriverWorkingHours(123);
        truck1.setRegNumber("AA98764");
        truckService.saveTruck(truck1);

        int lastIndex2 = truckService.getLastAddedTruckIndex();

        TruckDTO truck2FromDB = truckService.getTruckById(lastIndex2);

        Assert.assertEquals(false,orderService.getReadyToGoTrucks(orderFromDB).contains(truck2FromDB)); // false as the truck with low capacity

    }

    @Test
    public void findDriversForTrip() {
    }

    @Test
    public void assignDriver() {
    }

    @Test
    public void updateCargoStatus() {
    }

    @Test
    public void checkIfOrderIsDone() {
    }

    @Test
    public void getParamsAndSetToOrder() {
    }
}