package edu.tsystems.javaschool.logapp.api.dto.mapper;

import edu.tsystems.javaschool.logapp.api.dto.CargoDTO;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CargoMapper {
    private ModelMapper modelMapper;

    @Autowired
    public CargoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CargoMapper() {
    }

    public Cargo toEntity(CargoDTO dto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return Objects.isNull(dto) ? null : modelMapper.map(dto, Cargo.class);
    }

    public CargoDTO toDto(Cargo entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return Objects.isNull(entity) ? null : modelMapper.map(entity, CargoDTO.class);
    }
}
