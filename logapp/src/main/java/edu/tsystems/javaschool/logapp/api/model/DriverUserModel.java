package edu.tsystems.javaschool.logapp.api.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class DriverUserModel extends UserModel {
    private int driverId;

    public DriverUserModel(String username, String password, Collection<? extends GrantedAuthority> authorities,int driverId) {
        super(username, password, authorities);
        this.driverId = driverId;
    }
}
