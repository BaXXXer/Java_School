package edu.tsystems.javaschool.logapp.api.dto.mapper;

import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.entity.Driver;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Driver.class);
    }

    public DriverDTO toDto(Driver entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return Objects.isNull(entity) ? null : modelMapper.map(entity, DriverDTO.class);
    }

}
