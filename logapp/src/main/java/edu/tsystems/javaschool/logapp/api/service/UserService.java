package edu.tsystems.javaschool.logapp.api.service;

import edu.tsystems.javaschool.logapp.api.dao.UserDao;
import edu.tsystems.javaschool.logapp.api.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    @Transactional
    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    @Transactional
    public void createUser(User user){
        userDao.saveUser(user);
    }

    @Transactional
    public User findByEmail(String email){
        return userDao.findByEmail(email);
    }
}
