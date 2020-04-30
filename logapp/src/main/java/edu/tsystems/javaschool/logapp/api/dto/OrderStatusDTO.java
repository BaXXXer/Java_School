package edu.tsystems.javaschool.logapp.api.dto;

import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatusDTO {
    @GeneratedValue
    private Integer orderId;

    private boolean orderIsDone;

    private List<Cargo> cargoes;

}
