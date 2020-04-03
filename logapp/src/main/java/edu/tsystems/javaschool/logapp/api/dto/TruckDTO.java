package edu.tsystems.javaschool.logapp.api.dto;

import edu.tsystems.javaschool.logapp.api.entity.Truck;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TruckDTO {
    @GeneratedValue
    private Integer id;

    @NotNull
    @Pattern(regexp = "[A-Z]{2}\\d{5}",message = "Number should match format")
    private String regNumber;

    private Integer driverWorkingHours;


    @NotNull
    @Min(5)
    @Max(25)
    private int capacityTons;

    private Truck.Condition condition;

    @NotNull(message = "Should not be empty")
    private Integer currentCityId;

}
