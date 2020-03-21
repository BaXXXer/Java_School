package edu.tsystems.javaschool.logapp.api.dao;

import edu.tsystems.javaschool.logapp.api.entity.User;

import java.util.List;

public interface UserDao {
    User findByEmail(String email);
    User findUserById(int id);
    List<User> findAllUsers();
    void saveUser(User user);

}
