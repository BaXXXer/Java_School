package edu.tsystems.javaschool.logapp.api.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tsystems.javaschool.logapp.api.dto.DriverDTO;
import edu.tsystems.javaschool.logapp.api.dto.DriverStatusDTO;

public class DriverDtoToJSONConverter {

//    public static String convertToJSON(DriverDTO driverDTO) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            return mapper.writeValueAsString(driverDTO);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("driverDTO converting troubles");
//        }
//    }
    public static String convertToJSON(DriverStatusDTO status) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(status);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("driverStatusDTO converting troubles");
        }
    }
}
