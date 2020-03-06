package edu.tsystems.javaschool.logapp.api.services.mappers;

import edu.tsystems.javaschool.logapp.api.entities.Driver;
import edu.tsystems.javaschool.logapp.api.entities.dto.DriverDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DriverMapper {
    private ModelMapper modelMapper;

    @Autowired
    public DriverMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DriverMapper() {
    }

    public Driver toEntity(DriverDTO dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Driver.class);
    }

    public DriverDTO toDto(Driver entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, DriverDTO.class);
    }
}
