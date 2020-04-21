package edu.tsystems.javaschool.logapp.api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TruckStatusDTO {
    Long totalTrucksNumber;
    Long totalBrokenNumber;
    Long totalRestNumber;
}
