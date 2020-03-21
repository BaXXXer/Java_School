package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.UserDao;
import edu.tsystems.javaschool.logapp.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    @Transactional
    public void createUser(User user){
        userDao.saveUser(user);
    }
}
