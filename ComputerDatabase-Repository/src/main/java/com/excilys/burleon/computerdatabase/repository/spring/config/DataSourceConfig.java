package com.excilys.burleon.computerdatabase.repository.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.excilys.burleon.computerdatabase.core.util.PropertiesManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author Junior Burleon
 *
 */
@Configuration
public class DataSourceConfig {

    static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    @Profile("javaee")
    public HikariDataSource dataSource() throws IllegalArgumentException {

        PropertiesManager.load("datasource.properties");
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(PropertiesManager.config.getString("dburl"));
        config.setUsername(PropertiesManager.config.getString("dbuser"));
        config.setPassword(PropertiesManager.config.getString("dbpassword"));
        config.setDriverClassName(PropertiesManager.config.getString("dbdriver"));
        config.setMaximumPoolSize(Integer.valueOf(PropertiesManager.config.getString("dbmaxpoolsize")));
        config.setLeakDetectionThreshold(2010);
        config.setMinimumIdle(5);
        config.setAutoCommit(false);

        return new HikariDataSource(config);
    }

    @Bean
    @Profile("test")
    public HikariDataSource testDataSource() {
        PropertiesManager.load("datasource.properties");
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(PropertiesManager.config.getString("dburl"));
        config.setUsername(PropertiesManager.config.getString("dbuser"));
        config.setPassword(PropertiesManager.config.getString("dbpassword"));
        config.setDriverClassName(PropertiesManager.config.getString("dbdriver"));
        config.setMaximumPoolSize(Integer.valueOf(PropertiesManager.config.getString("dbmaxpoolsize")));
        config.setLeakDetectionThreshold(2010);
        config.setMinimumIdle(5);

        return new HikariDataSource(config);
    }
}