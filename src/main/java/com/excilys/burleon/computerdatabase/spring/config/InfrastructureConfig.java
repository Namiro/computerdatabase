package com.excilys.burleon.computerdatabase.spring.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.burleon.computerdatabase.service.util.PropertiesManager;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.excilys.burleon.computerdatabase.spring.config" })
public class InfrastructureConfig {

    @Autowired
    Environment environment;

    @Autowired
    private HikariDataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(this.dataSource);
        em.setPackagesToScan(new String[] { "com.excilys.burleon.computerdatabase.repository.model" });
        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(this.hibernateProperties());
        return em;
    }

    private Properties hibernateProperties() {
        PropertiesManager.load("hibernate.properties");
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", PropertiesManager.config.getString("hibernate.dialect"));
        properties.put("hibernate.show_sql", PropertiesManager.config.getString("hibernate.show_sql"));
        properties.put("hibernate.format_sql", PropertiesManager.config.getString("hibernate.format_sql"));
        return properties;
    }

    @Bean
    @Autowired
    public JpaTransactionManager txManager(final EntityManagerFactory emf) {
        final JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
}
