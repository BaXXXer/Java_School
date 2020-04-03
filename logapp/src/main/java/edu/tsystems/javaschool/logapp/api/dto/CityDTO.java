package edu.tsystems.javaschool.logapp.api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CityDTO {

    private Integer cityId;
    private String cityName;
    private double lat;
    private double lng;

    @Override
    public String toString() {
        return cityName;
    }
}
