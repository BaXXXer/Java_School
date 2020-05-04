package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.config.WebMvcConfig;
import edu.tsystems.javaschool.logapp.api.dto.*;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import edu.tsystems.javaschool.logapp.api.entity.Truck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class})
@WebAppConfiguration
public class OrderServiceTest {

    @Autowired
    private TruckService truckService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private OrderWayPointService pointService;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private CityService cityService;

    @Autowired
    private OrderService orderService;

    private DriverDTO driver;
    private OrderDTO order;

    private List<CargoWaypointDTO> points;




    @Test
    public void testGetReadyToGoTrucks() {
        points = new ArrayList<>();

        CargoDTO cargo1 = cargoService.getAllCargoes().get(0);
        CityDTO city1 = cityService.getCityDtoById(1) ;//New York
        CargoWaypointDTO point1 = pointService.getAllWaypoints().get(0) ;;

        CargoDTO cargo2 = cargoService.getAllCargoes().get(1);
        CityDTO city2 = cityService.getCityDtoById(7) ;//Toronto
        CargoWaypointDTO point2 = pointService.getAllWaypoints().get(1) ;;

        point1.setCargo(cargo1);
        point1.setOperationType(OrderWaypoint.Operation.LOAD);
        point1.setCompleted(false);
        point1.setDestCity(city1);

        point2.setCargo(cargo2);
        point2.setOperationType(OrderWaypoint.Operation.LOAD);
        point2.setCompleted(false);
        point2.setDestCity(city2);

        points.add(point1);
        points.add(point2);

        TruckDTO truck = truckService.getTruckById(40); //Must be in result list
        truck.setCondition(Truck.Condition.OK);
        truck.setCurrentCityId(5);
        truck.setCapacityTons(15);
        truck.setDriverWorkingHours(123);

        OrderDTO order = orderService.getOrderById(40);
        order.setPoints(points);
        order.setTruckId(null);
        orderService.updateOrder(order);

        TruckDTO truckToFind = new TruckDTO();
        List<TruckDTO> readyToGoTrucks = orderService.getReadyToGoTrucks(order);
        for(TruckDTO t: readyToGoTrucks){
            if(t.getRegNumber().equals(truck.getRegNumber())){
                truckToFind=t;
            }
        }

        Assert.assertEquals(true,orderService.getReadyToGoTrucks(order).contains(truckToFind));

        order.setPoints(points);
        order.setTruckId(40);

        Assert.assertEquals(false,orderService.getReadyToGoTrucks(order).contains(truck)); //false as the truck is assigned

        TruckDTO truck1 = truckService.getTruckById(40); //Must not be in result list (low capacity)
        truck1.setCondition(Truck.Condition.OK);
        truck1.setCurrentCityId(5);
        truck1.setCapacityTons(7);
        truck1.setDriverWorkingHours(123);
        Assert.assertEquals(false,orderService.getReadyToGoTrucks(order).contains(truck1)); // false as the truck with low capacity
        order.setTruckId(null);

    }

    @Test
    public void testFindDriversForTrip() {
        points = new ArrayList<>();

        CargoDTO cargo1 = cargoService.getAllCargoes().get(0);
        CityDTO city1 = cityService.getCityDtoById(1) ;
        CargoWaypointDTO point1 = pointService.getAllWaypoints().get(0) ;

        CargoDTO cargo2 = cargoService.getAllCargoes().get(1);
        CityDTO city2 = cityService.getCityDtoById(7) ;
        CargoWaypointDTO point2 = pointService.getAllWaypoints().get(1) ;

        point1.setCargo(cargo1);
        point1.setOperationType(OrderWaypoint.Operation.LOAD);
        point1.setCompleted(false);
        point1.setDestCity(city1);

        point2.setCargo(cargo2);
        point2.setOperationType(OrderWaypoint.Operation.LOAD);
        point2.setCompleted(false);
        point2.setDestCity(city2);

        points.add(point1);
        points.add(point2);

        OrderDTO order = new OrderDTO();
        order.setPoints(points);
        order.setTruckId(40);

        driver = new DriverDTO();
        driver.setDriversTruckId(40);
        driver.setDriverFirstName("Misha");
        driver.setDriverSurname("Popov");
        driver.setPassword("password");
        driver.setDriverPrivateNum(111112);
        driver.setDriverWorkedHours(0);
        driver.setDriverStatus(Driver.Status.REST);
        driver.setDriverCityId(5);
        driverService.saveDriver(driver);


        List<Integer> driverIds=new ArrayList<>();

        order.setDriversOnOrderIds(driverIds);

        List<DriverDTO> driversForTrip = orderService.findDriversForTrip(order);
        Assert.assertEquals(false,driversForTrip.contains(driver));

        driver.setDriverStatus(Driver.Status.CO_DRIVER);
        Assert.assertEquals(false,driversForTrip.contains(driver));

        driverService.removeDriver(driverService.getLastDriverId());
    }

    @Test
    public void assignDriver() {

        points = new ArrayList<>();

        CargoDTO cargo1 = cargoService.getAllCargoes().get(0);
        CityDTO city1 = cityService.getCityDtoById(1) ;//New York
        CargoWaypointDTO point1 = pointService.getAllWaypoints().get(0) ;

        CargoDTO cargo2 = cargoService.getAllCargoes().get(1);
        CityDTO city2 = cityService.getCityDtoById(7) ;//Toronto
        CargoWaypointDTO point2 = pointService.getAllWaypoints().get(1) ;

        point1.setCargo(cargo1);
        point1.setOperationType(OrderWaypoint.Operation.LOAD);
        point1.setCompleted(false);
        point1.setDestCity(city1);

        point2.setCargo(cargo2);
        point2.setOperationType(OrderWaypoint.Operation.LOAD);
        point2.setCompleted(false);
        point2.setDestCity(city2);

        points.add(point1);
        points.add(point2);

        order = orderService.getOrderById(40);
        order.setOrderIsDone(false);
        order.setWayPointsIds(new ArrayList<>());
        order.setTruckId(40);
        order.setPoints(points);
        order.setDriversOnOrderIds(new ArrayList<>());

        orderService.assignDriver(driverService.getDriverById(40),order);


        List<Integer> driversOnOrderIds = order.getDriversOnOrderIds();
        System.out.println(driversOnOrderIds);


        Assert.assertEquals(false,order.getDriversOnOrderIds().contains(40));
        driversOnOrderIds.add(40);
        order.setDriversOnOrderIds(driversOnOrderIds);
        orderService.assignDriver(driverService.getDriverById(41),order);
        Assert.assertEquals(false,order.getDriversOnOrderIds().contains(41));

    }


    @Test
    public void testAssignTruck(){
        points = new ArrayList<>();

        CargoDTO cargo1 = cargoService.getAllCargoes().get(0);
        CityDTO city1 = cityService.getCityDtoById(1) ;//New York
        CargoWaypointDTO point1 = pointService.getAllWaypoints().get(0) ;

        CargoDTO cargo2 = cargoService.getAllCargoes().get(1);
        CityDTO city2 = cityService.getCityDtoById(7) ;//Toronto
        CargoWaypointDTO point2 = pointService.getAllWaypoints().get(1) ;

        point1.setCargo(cargo1);
        point1.setOperationType(OrderWaypoint.Operation.LOAD);
        point1.setCompleted(false);
        point1.setDestCity(city1);

        point2.setCargo(cargo2);
        point2.setOperationType(OrderWaypoint.Operation.LOAD);
        point2.setCompleted(false);
        point2.setDestCity(city2);

        points.add(point1);
        points.add(point2);

        order = orderService.getOrderById(40);
        order.setOrderIsDone(false);
        order.setWayPointsIds(new ArrayList<>());
        order.setPoints(points);
        order.setDriversOnOrderIds(new ArrayList<>());

        orderService.assignTruck(truckService.getTruckById(40),order);

        Assert.assertEquals(true,order.getTruckId().equals(40));

    }

    @Test
    public void testCheckIfOrderIsDone() {
        points = new ArrayList<>();

        CargoDTO cargo1 = cargoService.getAllCargoes().get(0);
        cargo1.setCargoStatus(Cargo.Status.DELIVERED);
        CityDTO city1 = cityService.getCityDtoById(1) ;//New York
        CargoWaypointDTO point1 = pointService.getAllWaypoints().get(0) ;

        CargoDTO cargo2 = cargoService.getAllCargoes().get(1);
        cargo2.setCargoStatus(Cargo.Status.DELIVERED);
        CityDTO city2 = cityService.getCityDtoById(7) ;//Toronto
        CargoWaypointDTO point2 = pointService.getAllWaypoints().get(1) ;

        point1.setCargo(cargo1);
        point1.setOperationType(OrderWaypoint.Operation.LOAD);
        point1.setCompleted(true);
        point1.setDestCity(city1);

        point2.setCargo(cargo2);
        point2.setOperationType(OrderWaypoint.Operation.LOAD);
        point2.setCompleted(true);
        point2.setDestCity(city2);

        points.add(point1);
        points.add(point2);

        order = orderService.getOrderById(40);
        order.setOrderIsDone(false);
        order.setWayPointsIds(new ArrayList<>());
        order.setPoints(points);
        order.setDriversOnOrderIds(new ArrayList<>());

        orderService.checkIfOrderIsDone(40);

        Assert.assertEquals(true,orderService.getOrderById(40).isOrderIsDone());
        order.setOrderIsDone(false);
        cargo1.setCargoStatus(Cargo.Status.READY);
        cargo2.setCargoStatus(Cargo.Status.READY);

    }

    @Test
    public void checkIfOrderIsDone() {
    }

    @Test
    public void getParamsAndSetToOrder() {
    }

    @Test
    public void testGetLastOrderStatus(){
        int totalOrdersBefore = orderService.getLastOrdersStatus().size();

        driver = new DriverDTO();
        driver.setDriverFirstName("Misha");
        driver.setDriverSurname("Popov");
        driver.setDriverPrivateNum(18765432);
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

        int ordersAfter = orderService.getLastOrdersStatus().size();
        Assert.assertEquals(1,ordersAfter-numOfOrdersBefore);

    }
}