package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.WayPointsDao;
import edu.tsystems.javaschool.logapp.api.dto.CargoDTO;
import edu.tsystems.javaschool.logapp.api.dto.CargoWaypointDTO;
import edu.tsystems.javaschool.logapp.api.dto.mapper.CityMapper;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderWayPointService {

    private WayPointsDao dao;
    private CargoService cargoService;
    private CityService cityService;
    private CityMapper cityMapper;
    private TruckService truckService;

    @Autowired
    public OrderWayPointService(WayPointsDao dao, CargoService cargoService, CityService cityService, CityMapper cityMapper, TruckService truckService) {
        this.dao = dao;
        this.cargoService = cargoService;
        this.cityService = cityService;
        this.cityMapper = cityMapper;
        this.truckService = truckService;
    }


    @Transactional
    public OrderWaypoint getPointById(int id){
        return dao.getWaypointById(id);
    }


    @Transactional
    public CargoWaypointDTO getPointDtoById(int id){
        return toDto(dao.getWaypointById(id));
    }



    @Transactional
    public List<CargoWaypointDTO> getAllWaypoints() {
        List<OrderWaypoint> waypoints = dao.getAllWaypoints();
        List<CargoWaypointDTO> WPdtos = new ArrayList<>();
        for(OrderWaypoint p: waypoints){
            WPdtos.add(toDto(p));
        }
        return WPdtos;

    }

    /**
     * The new-added waypoint cannot be with types "UNLOAD" or "DELIVERED"
     * So, we assume that new waypoints, which are not assigned somewhere - are with type "LOAD"
     *
     * @return list of not assigned points
     */
    @Transactional
    public List<CargoWaypointDTO> getNotAssignedWaypoints(){
        List<CargoWaypointDTO> allWaypoints = getAllWaypoints();
        List<CargoWaypointDTO> notAssignedPoints = new ArrayList();
        for(CargoWaypointDTO point: allWaypoints){
            if(point.getOperationType()== OrderWaypoint.Operation.LOAD){
                notAssignedPoints.add(point);
            }
        }
        return notAssignedPoints;
    }


    /**
     * Takes all the cargoes and all the waypoints
     * Checks if current point has cargo assigned
     * If yes, removes that cargo by id from the list
     * Otherwise it is in the list
     * @return
     */
    @Transactional
    public List<CargoDTO> getNotAssignedCargoes(){
        List <CargoDTO> allCargo = cargoService.getAllCargoes();
        List<CargoWaypointDTO> allWaypoints = getAllWaypoints();
        for(CargoWaypointDTO point: allWaypoints){
            int cargoId=0;
            if(point.getCargo()!=null){
                cargoId = point.getCargo().getCargoId();
                CargoDTO cargo = cargoService.findCargoById(cargoId);
                allCargo.remove(cargo);
            }
        }

        return allCargo;


    }

    public CargoWaypointDTO toDto(OrderWaypoint entity){
        CargoWaypointDTO dto = new CargoWaypointDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getPointName());
        if(entity.getOrder()!=null){
            if(entity.getOrder().getTruckOnOrder()!=null) {
                dto.setAssignedTruck(truckService.toDTO(entity.getOrder().getTruckOnOrder()));
            }
        }
        if(entity.getCargo()!=null) {
            dto.setCargo(cargoService.toDto(entity.getCargo()));
        }
        if(entity.getCity()!=null) {
            dto.setDestCity(cityService.toDto(entity.getCity()));
        }
        dto.setCompleted(entity.isCompleted());
        dto.setOperationType(entity.getOperationType());
        return dto;

    }


    /**
     * Changes the cargo status when a driver had set it
     * "Loaded" or "Unloaded"
     * @param points
     * @param pointId
     * @return
     */
    public List<CargoWaypointDTO> setLoadedById(List<CargoWaypointDTO> points, int pointId) {
        for(CargoWaypointDTO dto: points){
            if(dto.getId()==pointId){
                if(dto.getOperationType()== OrderWaypoint.Operation.LOAD) {
                    dto.setOperationType(OrderWaypoint.Operation.UNLOAD);
                    dto.getCargo().setCargoStatus(Cargo.Status.SHIPPED);
                }else if(dto.getOperationType()== OrderWaypoint.Operation.UNLOAD){
                    dto.getCargo().setCargoStatus(Cargo.Status.DELIVERED);
                    dto.setCompleted(true);
                }
            }
        }
        return points;

    }

    @Transactional
    public OrderWaypoint toEntity(CargoWaypointDTO cdto) {
        OrderWaypoint entity;
        if(cdto.getId()!=null){
            entity = getPointById(cdto.getId());
        }else{
            entity = new OrderWaypoint();
        }
        if(cdto.getCargo()!=null){

            entity.setCargo(cargoService.toEntity(cdto.getCargo()));
        }
        if(cdto.getAssignedTruck()!=null){
            entity.getOrder().setTruckOnOrder(truckService.toEntity(cdto.getAssignedTruck()));
        }
        if(cdto.getDestCity()!=null) {
            entity.setCity(cityService.toEntity(cdto.getDestCity()));
        }
        entity.setCompleted(cdto.isCompleted());
        entity.setPointName(cdto.getName());
        entity.setOperationType(cdto.getOperationType());
        return entity;
    }
}
