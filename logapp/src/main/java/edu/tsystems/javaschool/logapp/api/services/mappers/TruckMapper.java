package edu.tsystems.javaschool.logapp.api.services.mappers;

import edu.tsystems.javaschool.logapp.api.entities.Truck;
import edu.tsystems.javaschool.logapp.api.entities.dto.TruckDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TruckMapper {
    private ModelMapper modelMapper;

    @Autowired
    public TruckMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TruckMapper() {
    }


    public Truck toEntity(TruckDTO dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Truck.class);
    }

    public TruckDTO toDto(Truck entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, TruckDTO.class);
    }
}
