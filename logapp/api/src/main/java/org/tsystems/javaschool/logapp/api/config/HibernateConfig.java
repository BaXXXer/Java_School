package org.tsystems.javaschool.logapp.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.tsystems.javaschool.logapp.api.entities.Truck;

public class HibernateConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        //TODO: classpath? CHECK
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:hibernate.cfg.xml"));
        factoryBean.setAnnotatedClasses(Truck.class);
        return factoryBean;
    }
}
