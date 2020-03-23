package edu.tsystems.javaschool.logapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityDTO {

    private int cityId;
    private String cityName;
    private double lat;
    private double lng;
}
