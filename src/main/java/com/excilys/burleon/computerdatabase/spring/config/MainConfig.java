package com.excilys.burleon.computerdatabase.spring.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

/**
 *
 * @author Junior Burleon
 *
 */
@Configuration
@Import(value = { DataSourceConfig.class, InfrastructureConfig.class, RepositoryConfig.class, ServiceConfig.class,
        SecurityConfig.class })
public class MainConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainConfig.class);

    @Autowired
    private Environment environment;

    /**
     * Application custom initialization code.
     * <p/>
     * Spring profiles can be configured with a system property
     * -Dspring.profiles.active=javaee
     * <p/>
     */
    @PostConstruct
    public void initApp() {
        MainConfig.LOGGER.debug("Looking for Spring profiles...");
        if (this.environment.getActiveProfiles().length == 0) {
            MainConfig.LOGGER.info("No Spring profile configured, running with default configuration.");
        } else {
            for (final String profile : this.environment.getActiveProfiles()) {
                MainConfig.LOGGER.info("Detected Spring profile: {}", profile);
            }
        }
    }
}
