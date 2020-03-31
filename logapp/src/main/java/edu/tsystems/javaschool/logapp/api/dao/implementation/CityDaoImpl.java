package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.CityDao;
import edu.tsystems.javaschool.logapp.api.entity.City;
import edu.tsystems.javaschool.logapp.api.exception.EntityNotFoundException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CityDaoImpl implements CityDao {

    private SessionFactory sessionFactory;
    private static final Logger LOG = Logger.getLogger(CityDaoImpl.class);

    @Autowired
    public CityDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<City> getAllCities() {
        Session session = this.sessionFactory.getCurrentSession();
        List<City> cityList = session.createQuery("from City").list();
        return cityList;
    }

    @Override
    @Transactional
    public City getCityById(int id) {
        Session session = sessionFactory.getCurrentSession();
        try{

            session.load(City.class, id);
        }catch(NullPointerException ex){
            LOG.error("City not found. Id: " + id + ex.getStackTrace());
            throw new EntityNotFoundException();
        }
        return session.load(City.class, id);
    }
}
