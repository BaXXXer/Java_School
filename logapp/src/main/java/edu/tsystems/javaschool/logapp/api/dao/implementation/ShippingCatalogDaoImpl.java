package edu.tsystems.javaschool.logapp.api.dao.implementation;

import edu.tsystems.javaschool.logapp.api.dao.ShippingCatalogDao;
import edu.tsystems.javaschool.logapp.api.entity.ShippingCatalog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShippingCatalogDaoImpl implements ShippingCatalogDao {


    private final SessionFactory sessionFactory;

    @Autowired
    public ShippingCatalogDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ShippingCatalog getConstants() {
        Session session = sessionFactory.getCurrentSession();
        ShippingCatalog constants = session.load(ShippingCatalog.class, 1);
        return constants;
    }
}
