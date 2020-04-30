package edu.tsystems.javaschool.logapp.api.dto.converter;

import edu.tsystems.javaschool.logapp.api.dao.DriverDao;
import edu.tsystems.javaschool.logapp.api.dto.DriverUserDTO;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DriverUserDtoConverter {
    private final OrderDtoConverter orderConverter;
    private final DriverDao driverDao;

    public DriverUserDtoConverter(OrderDtoConverter orderConverter, DriverDao driverDao) {
        this.orderConverter = orderConverter;
        this.driverDao = driverDao;
    }

    @Transactional
    public DriverUserDTO convertToDTO(Driver entity){
        DriverUserDTO dto = new DriverUserDTO();
        dto.setDriverId(entity.getDriverId());
        dto.setDriverPrivateNum(entity.getDriverPrivateNum());

        DriverUserDTO.Status status = Driver.Status.switchFromDriverToDtoStatus(entity.getDriverStatus());
        dto.setDriverStatus(status);

        dto.setDriverFirstName(entity.getDriverFirstName());
        dto.setDriverSurname(entity.getDriverSurname());
        if (entity.getOrder() != null) {
            dto.setAssignedOrder(orderConverter.convertToDTO(entity.getOrder()));
        }
        if (entity.getDriversTruck() != null) {
            dto.setTruckRegNumber(entity.getDriversTruck().getRegNumber());
        }
        return dto;

    }

    @Transactional
    public Driver convertToEntity(DriverUserDTO dto){
        Driver entity = driverDao.getDriverById(dto.getDriverId());
        Driver.Status status = Driver.Status.switchFromDtoToStatus(dto.getDriverStatus());
        entity.setDriverStatus(status);
        return entity;

    }
}
