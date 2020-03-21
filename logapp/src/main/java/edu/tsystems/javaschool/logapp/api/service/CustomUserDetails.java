package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.UserDao;
import edu.tsystems.javaschool.logapp.api.entity.User;
import edu.tsystems.javaschool.logapp.api.model.DriverUserModel;
import edu.tsystems.javaschool.logapp.api.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetails implements UserDetailsService{
    private final UserDao userDao;

    @Autowired
    public CustomUserDetails(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return buildSecurityUserFromUserEntity(userDao.findByEmail(userName));
    }

    private UserDetails buildSecurityUserFromUserEntity(User userEntity) {
        String username = userEntity.getEmail();
        String password = userEntity.getPasswordMd5();
        GrantedAuthority userRole = new SimpleGrantedAuthority(userEntity
                .getRole().name());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(userRole);

        if (userEntity.getRole() == User.UserRole.ROLE_DRIVER) {
            return new DriverUserModel(username, password, authorities,
                    userEntity.getDriver().getDriverId());
        } else {
            return new UserModel(username, password, authorities);
        }
    }
}
