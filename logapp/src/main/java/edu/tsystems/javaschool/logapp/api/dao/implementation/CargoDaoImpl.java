package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.CargoDao;
import edu.tsystems.javaschool.logapp.api.entity.Cargo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CargoDaoImpl implements CargoDao {

    private SessionFactory sessionFactory;

    @Autowired
    public CargoDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Cargo findCargoById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Cargo cargo= session.load(Cargo.class, id);
        return cargo;
    }
}
