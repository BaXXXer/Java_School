package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.DriverUpdateDTO;

import javax.ejb.Remote;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Remote
public class DriverService {

    public void updateDriverFromJSON(String driverJSON) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DriverUpdateDTO driverUpdated = mapper.readValue(driverJSON, DriverUpdateDTO.class);
    }

    public void getAllDrivers(String allDriversJSON) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<DriverUpdateDTO> drivers = Arrays.asList(mapper.readValue(allDriversJSON,DriverUpdateDTO[].class));
    }

}
