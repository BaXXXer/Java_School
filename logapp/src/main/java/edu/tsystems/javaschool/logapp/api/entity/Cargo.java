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
@Getter
@Setter
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargo_seq")
    @SequenceGenerator(name="cargo_seq",sequenceName = "cargo_seq",allocationSize = 1)
    @Column(name = "cg_id")
    private int cargoId;

    /**
     * Unique cargo identifier for given order.
     */

    @Column(name="cg_name")
    private String cargoName;

    /**
     * Cargo description.
     */
    @Column(name = "cg_title")
//    @NotNull
//    @Size(min = 1)
    private String title;

    @Column(name="cg_weight")
    private int cargoWeightKilos;

    @Column(name = "cg_status")
    @Enumerated(EnumType.STRING)
    private Status cargoStatus;

    public enum Status{
        READY, SHIPPED, DELIVERED
    }

    @ManyToOne()
    @JoinColumn(name = "cargo_currentCity_id", nullable = false)
    private City currentCity;
}
