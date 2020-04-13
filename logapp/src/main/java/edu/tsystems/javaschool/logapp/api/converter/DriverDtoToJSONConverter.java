package edu.tsystems.javaschool.logapp.api.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;

public class DriverDtoToJSONConverter {

    public static String convertToJSON(DriverDTO driverDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(driverDTO);
    }
}
