package edu.tsystems.javaschool.logapp.api.dto;

import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode

public class CargoDTO {
    private int cargoId;
    /**
     * Unique cargo identifier for given order.
     */
    private String cargoName;
    /**
     * Cargo description.
     */
    private String title;
    private int cargoWeightKilos;
   private Cargo.Status cargoStatus;
   private CityDTO currentCity;


}
