package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.UserDao;
import edu.tsystems.javaschool.logapp.api.entity.User;
import edu.tsystems.javaschool.logapp.api.exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;
    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    @Transactional
    public void saveUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
        LOG.info("User was created");


    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        User user = null;
        for (User u : findAllUsers()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                user = u;
            }
        }
        if (user != null) {
            return user;
        } else {
            LOG.error("Username was not found");
            throw new UsernameNotFoundException(email);
        }
    }

    @Override
    @Transactional
    public List<User> findAllUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> userList = session.createQuery("from User").list();
        return userList;
    }

    @Override
    @Transactional
    public User findUserById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.load(User.class, id);
        return session.load(User.class, id);
    }
}
