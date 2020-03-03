package edu.tsystems.javaschool.logapp.api.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
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

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(restDataSource());
        sessionFactory.setPackagesToScan(new String[]{"edu.tsystems.javaschool.logapp.api"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.afterPropertiesSet();
        return sessionFactory;

    }


    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto",
                "update");
        hibernateProperties.setProperty("hibernate.dialect",
                "org.hibernate.dialect.PostgreSQL94Dialect");

        return hibernateProperties;
    }

    @Bean
    public DataSource restDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/log_app");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }


    @Bean
    public PlatformTransactionManager hibernateTransactionManager() throws Exception {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

//    @Bean
//    public EntityManager entityManager() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("logapp");
//        return emf.createEntityManager();
//    }
}
