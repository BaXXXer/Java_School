package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.WayPointsDao;
import edu.tsystems.javaschool.logapp.api.dto.CargoWaypointDTO;
import edu.tsystems.javaschool.logapp.api.dto.mapper.CargoMapper;
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
    private CargoMapper cargoMapper;
    private CityMapper cityMapper;

    @Autowired
    public OrderWayPointService(WayPointsDao dao, CargoService cargoService, CityService cityService, CargoMapper cargoMapper, CityMapper cityMapper) {
        this.dao = dao;
        this.cargoService = cargoService;
        this.cityService = cityService;
        this.cargoMapper = cargoMapper;
        this.cityMapper = cityMapper;
    }

    @Transactional
    public CargoWaypointDTO getCGWPDto(int id){
        OrderWaypoint waypoint = dao.getWaypointById(id);
        return toDto(waypoint);
    }

    @Transactional
    public OrderWaypoint getPointById(int id){
        return dao.getWaypointById(id);
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

    @Transactional
    public CargoWaypointDTO toDto(OrderWaypoint entity){
        CargoWaypointDTO dto = new CargoWaypointDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getPointName());
        dto.setCargo(cargoService.toDto(entity.getCargo()));
        dto.setCity(cityService.toDto(entity.getCity()));
        dto.setCompleted(entity.isCompleted());
        dto.setOperationType(entity.getOperationType());
        return dto;

    }

    public List<CargoWaypointDTO> setLoadedById(List<CargoWaypointDTO> points, int pointId) {
        for(CargoWaypointDTO dto: points){
            if(dto.getId()==pointId){
                dto.setOperationType(OrderWaypoint.Operation.UNLOAD);
                dto.getCargo().setCargoStatus(Cargo.Status.SHIPPED);
            }
        }
        return points;

    }

    public OrderWaypoint toEntity(CargoWaypointDTO cdto) {
        OrderWaypoint entity = getPointById(cdto.getId());
        entity.setCargo(cargoService.toEntity(cdto.getCargo()));
        entity.setCity(cityMapper.toEntity(cdto.getCity()));
        entity.setCompleted(cdto.isCompleted());
        entity.setPointName(cdto.getName());
        entity.setOperationType(cdto.getOperationType());
        return entity;
    }
}
