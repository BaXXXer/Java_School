package edu.tsystems.javaschool.logapp.api.dto.mapper;

import edu.tsystems.javaschool.logapp.api.dto.CityDTO;
import edu.tsystems.javaschool.logapp.api.entity.City;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CityMapper {
    private ModelMapper modelMapper;

    @Autowired
    public CityMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CityMapper() {
    }

    public City toEntity(CityDTO dto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return Objects.isNull(dto) ? null : modelMapper.map(dto, City.class);
    }

    public CityDTO toDto(City entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return Objects.isNull(entity) ? null : modelMapper.map(entity, CityDTO.class);
    }
}
