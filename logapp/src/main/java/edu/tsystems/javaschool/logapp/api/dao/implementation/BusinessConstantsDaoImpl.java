package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.BusinessConstantsDao;
import edu.tsystems.javaschool.logapp.api.entity.BusinessLogicConstants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessConstantsDaoImpl implements BusinessConstantsDao {


    private final SessionFactory sessionFactory;

    @Autowired
    public BusinessConstantsDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public BusinessLogicConstants getConstants() {
        Session session = sessionFactory.getCurrentSession();
        BusinessLogicConstants constants = session.load(BusinessLogicConstants.class, 1);
        return constants;

    }
}
