package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.UserDao;
import edu.tsystems.javaschool.logapp.api.entity.User;
import edu.tsystems.javaschool.logapp.api.model.DriverUserModel;
import edu.tsystems.javaschool.logapp.api.model.UserModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao, UserDetailsManager {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public User findByEmail(String email) {
        User user = null;
        for(User u: findAllUsers()){
            if(u.getEmail().equals(email)){
                user=u;
            }
        }
        if (user != null){
            return user;
        }
        else{
            throw new UsernameNotFoundException(email);
        }
    }

    @Override
    public List<User> findAllUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> userList = session.createQuery("from User").list();
        return userList;
    }

    @Override
    public void createUser(UserDetails userDetails) {

    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String s) {
        return false;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
            return buildSecurityUserFromUserEntity(findByEmail(userName));
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
