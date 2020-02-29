package edu.tsystems.javaschool.logapp.api.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@Transactional
@EnableTransactionManagement
@ComponentScan(basePackages = {"edu.tsystems.javaschool.logapp.api"})
public class PersistenceConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean getSessionFactory() throws IOException {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(restDataSource());
        factoryBean.setPackagesToScan("edu.tsystems.javaschool.logapp.api");
        factoryBean.setHibernateProperties(hibernateProperties());
        factoryBean.afterPropertiesSet();
        return factoryBean;

    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", "hibernate.dialect.PostgreSQL94Dialect");
        hibernateProperties.put("hibernate.show_sql", false);
        hibernateProperties.put("hibernate.generate_statistics", false);
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.put("hibernate.use_sql_comments", false);

        return hibernateProperties;


//        return new Properties() {
//            {
//                setProperty("hibernate.hbm2ddl.auto",
//                        env.getProperty("update"));
//                setProperty("hibernate.dialect",
//                        env.getProperty("hibernate.dialect.PostgreSQL94Dialect"));
//                setProperty("hibernate.globally_quoted_identifiers",
//                        "true");
//                setProperty("hibernate.c3p0.min_size",env.getProperty("5"));
//            }
//        };
    }

    @Bean
    public DataSource restDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/log_app");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;


//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(env.getProperty("org.postgresql.Driver"));
//        dataSource.setUrl(env.getProperty("jdbc:postgresql://localhost:5432/log_app"));
//        dataSource.setUsername(env.getProperty("postgres"));
//        dataSource.setPassword(env.getProperty("postgres"));
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(
            SessionFactory sessionFactory) {

        HibernateTransactionManager txManager
                = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
