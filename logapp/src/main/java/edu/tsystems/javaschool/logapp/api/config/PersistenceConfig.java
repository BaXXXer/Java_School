package edu.tsystems.javaschool.logapp.api.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;


@Configuration
//@Transactional
@EnableTransactionManagement

//@ComponentScan(basePackages = {"edu.tsystems.javaschool.logapp.api"})
public class PersistenceConfig {

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(restDataSource());
        sessionFactory.setPackagesToScan(new String[]{"edu.tsystems.javaschool.logapp.api"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.afterPropertiesSet();
//        System.out.println(factoryBean.getConfiguration());

        return sessionFactory;

    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto",
                "create-drop");
        hibernateProperties.setProperty("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQL94Dialect");

        return hibernateProperties;
    }

//        return new Properties() {
//            {
//                setProperty("hibernate.hbm2ddl.auto",
//                        "update");
//                setProperty("hibernate.dialect",
//                        "org.hibernate.dialect.PostgreSQL94Dialect");
//                setProperty("hibernate.globally_quoted_identifiers",
//                        "true");
//                setProperty("hibernate.show_sql",
//                        "false");
////                setProperty("hibernate.c3p0.min_size",env.getProperty("5"));
//            }
//        };


//        Properties hibernateProperties = new Properties();
//        hibernateProperties.put("hibernate.dialect", "hibernate.dialect.PostgreSQL94Dialect");
//        hibernateProperties.put("hibernate.show_sql", false);
//        hibernateProperties.put("hibernate.generate_statistics", false);
//        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
//        hibernateProperties.put("hibernate.use_sql_comments", false);
//
//        return hibernateProperties;

    @Bean
    public DataSource restDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/log_app");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/log_app");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("postgres");



    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(
            SessionFactory sessionFactory) {

        HibernateTransactionManager txManager
                = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }

//    @Bean
//    public PlatformTransactionManager hibernateTransactionManager() throws Exception {
//        HibernateTransactionManager transactionManager
//                = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory());
//        return transactionManager;
//    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
