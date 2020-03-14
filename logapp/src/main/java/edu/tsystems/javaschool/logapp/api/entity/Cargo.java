package edu.tsystems.javaschool.logapp.api.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="lg_cargo")
@AllArgsConstructor
@NoArgsConstructor
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargo_seq")
    @SequenceGenerator(name="cargo_seq",sequenceName = "cargo_seq",allocationSize = 1)
    @Column(name = "cg_id")
    @Getter
    @Setter
    private int cargoId;

    /**
     * Unique cargo identifier for given order.
     */

    @Column(name="cg_name")
    @Getter
    @Setter
    private String cargoName;

    /**
     * Cargo description.
     */
    @Column(name = "cg_title")
    @Getter
    @Setter
//    @NotNull
//    @Size(min = 1)
    private String title;

    @Column(name="cg_weight")
    @Getter
    @Setter
    private int cargoWeightKilos;

    @Column(name = "cg_status")
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Status cargoStatus;

    public enum Status{
        READY, SHIPPED, DELIVERED
    }
}
