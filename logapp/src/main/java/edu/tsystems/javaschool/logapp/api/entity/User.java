package edu.tsystems.javaschool.logapp.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lg_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    public enum UserRole {
        ROLE_MANAGER,
        ROLE_DRIVER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name="user_sequence",sequenceName = "user_sequence",allocationSize = 1)
    @Column(name = "user_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String email;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "user_password", nullable = false)
    private String passwordMd5;


    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,optional = false)
//    @MapsId
    private Driver driver;



}
