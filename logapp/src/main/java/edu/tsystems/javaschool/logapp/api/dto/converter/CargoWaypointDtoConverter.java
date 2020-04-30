package edu.tsystems.javaschool.logapp.api.dto.converter;

import edu.tsystems.javaschool.logapp.api.dao.WayPointsDao;
import edu.tsystems.javaschool.logapp.api.dto.CargoWaypointDTO;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CargoWaypointDtoConverter {

    private final CityDtoConverter cityConverter;
    private final CargoDtoConverter cargoConverter;
    private final TruckDtoConverter truckConverter;
    private final WayPointsDao dao;

    @Autowired
    public CargoWaypointDtoConverter(CityDtoConverter cityConverter, CargoDtoConverter cargoConverter, TruckDtoConverter truckConverter, WayPointsDao dao) {
        this.cityConverter = cityConverter;
        this.cargoConverter = cargoConverter;
        this.truckConverter = truckConverter;
        this.dao = dao;
    }

    @Transactional
    public CargoWaypointDTO convertToDTO(OrderWaypoint entity){
        CargoWaypointDTO dto = new CargoWaypointDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getPointName());
        if(entity.getOrder()!=null){
            if(entity.getOrder().getTruckOnOrder()!=null) {
                dto.setAssignedTruck(truckConverter.convertToDto(entity.getOrder().getTruckOnOrder()));
            }
        }
        if(entity.getCargo()!=null) {
            dto.setCargo(cargoConverter.convertToDto(entity.getCargo()));
        }
        if(entity.getCity()!=null) {
            dto.setDestCity(cityConverter.convertToDto(entity.getCity()));
        }
        dto.setCompleted(entity.isCompleted());
        dto.setOperationType(entity.getOperationType());
        return dto;
    }

    @Transactional
    public OrderWaypoint convertToEntity(CargoWaypointDTO cdto){
        OrderWaypoint entity;
        if(cdto.getId()!=null){
            entity = dao.getWaypointById(cdto.getId());
        }else{
            entity = new OrderWaypoint();
        }
        if(cdto.getCargo()!=null){

            entity.setCargo(cargoConverter.convertToEntity(cdto.getCargo()));
        }
        if(cdto.getAssignedTruck()!=null){
            entity.getOrder().setTruckOnOrder(truckConverter.convertToEntity(cdto.getAssignedTruck()));
        }
        if(cdto.getDestCity()!=null) {
            entity.setCity(cityConverter.convertToEntity(cdto.getDestCity()));
        }
        entity.setCompleted(cdto.isCompleted());
        entity.setPointName(cdto.getName());
        entity.setOperationType(cdto.getOperationType());
        return entity;
    }
}
