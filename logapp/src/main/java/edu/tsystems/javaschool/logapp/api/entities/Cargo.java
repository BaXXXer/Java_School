package edu.tsystems.javaschool.logapp.api.entities;


import javax.persistence.*;

@Entity
@Table(name="lg_cargo")
public class Cargo {
    @Id
    @GeneratedValue
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
    @Column(name = "title")
//    @NotNull
//    @Size(min = 1)
    private String title;

    @Column(name="cg_weight")
    private int cargoWeightKilos;

    @Column(name = "cg_status")
    @Enumerated(EnumType.STRING)
    private Status cargoStatus;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;

    public enum Status{
        READY, SHIPPED, DELIVERED
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCargoWeightKilos() {
        return cargoWeightKilos;
    }

    public void setCargoWeightKilos(int cargoWeightKilos) {
        this.cargoWeightKilos = cargoWeightKilos;
    }

    public Status getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(Status cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }
}
