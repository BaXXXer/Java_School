package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.WayPointsDao;
import edu.tsystems.javaschool.logapp.api.dto.CargoDTO;
import edu.tsystems.javaschool.logapp.api.dto.CargoWaypointDTO;
import edu.tsystems.javaschool.logapp.api.dto.CityDTO;
import edu.tsystems.javaschool.logapp.api.dto.converter.CargoWaypointDtoConverter;
import edu.tsystems.javaschool.logapp.api.dto.converter.CityDtoConverter;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import edu.tsystems.javaschool.logapp.api.entity.OrderWaypoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderWayPointService {

    private final WayPointsDao dao;
    private final CargoService cargoService;
    private final CityDtoConverter cityConverter;
    private final CargoWaypointDtoConverter pointConverter;


    @Transactional
    public OrderWaypoint getPointById(int id){
        return dao.getWaypointById(id);
    }


    @Transactional
    public CargoWaypointDTO getPointDtoById(int id){
        return pointConverter.convertToDTO(dao.getWaypointById(id));
    }



    @Transactional
    public List<CargoWaypointDTO> getAllWaypoints() {
        List<OrderWaypoint> waypoints = dao.getAllWaypoints();
        List<CargoWaypointDTO> wayPointdtos = new ArrayList<>();
        for(OrderWaypoint p: waypoints){
            wayPointdtos.add(pointConverter.convertToDTO(p));
        }
        return wayPointdtos;

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
        List<CargoWaypointDTO> notAssignedPoints = new ArrayList<>();
        for(CargoWaypointDTO point: allWaypoints){
            if(point.getOperationType()== OrderWaypoint.Operation.LOAD && !point.isCompleted()){
                notAssignedPoints.add(point);
            }
        }
        return notAssignedPoints;
    }

    @Transactional
    public List<CityDTO> getCityCoordinates(List<Integer> pointIds){
        List<CityDTO> cityList = new ArrayList<>();
        for(Integer id: pointIds){
            CityDTO city = cityConverter.convertToDto(getPointById(id).getCity());
            cityList.add(city);
        }
        return cityList;
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
}
