package edu.tsystems.javaschool.logapp.api.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tsystems.javaschool.logapp.api.dto.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ToJSONConverter {

    public static String convertDriverStatusToJSON(DriverStatusDTO status) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(status);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("driverStatusDTO converting troubles");
        }
    }

    public static String convertTruckStatusToJSON(TruckStatusDTO status){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(status);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("driverStatusDTO converting troubles");
        }
    }

    public static String convertOrderToJSON(OrderDTO order){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("driverStatusDTO converting troubles");
        }
    }


    public static String convertListOfOrderStatusToJSON(List<BoardOrderStatusDTO> statuses){
        ObjectMapper mapper = new ObjectMapper();
        final ByteArrayOutputStream out = new ByteArrayOutputStream();


        try {
            mapper.writeValue(out,statuses);
            final byte[] data = out.toByteArray();
            return new String(data);

        } catch (IOException e) {
            throw new RuntimeException("orderStatusDTO converting troubles");
        }


    }
}
