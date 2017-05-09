package com.excilys.burleon.computerdatabase.service.spring.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = { "com.excilys.burleon.computerdatabase" })
public class ServiceConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceConfig.class);

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
        ServiceConfig.LOGGER.debug("Looking for Spring profiles...");
        if (this.environment.getActiveProfiles().length == 0) {
            ServiceConfig.LOGGER.info("No Spring profile configured, running with default configuration.");
        } else {
            for (final String profile : this.environment.getActiveProfiles()) {
                ServiceConfig.LOGGER.info("Detected Spring profile: {}", profile);
            }
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}