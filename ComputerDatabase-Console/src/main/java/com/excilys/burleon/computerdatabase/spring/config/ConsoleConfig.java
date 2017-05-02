package com.excilys.burleon.computerdatabase.spring.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 *
 * @author Junior Burleon
 *
 */
@Configuration
@ComponentScan(basePackages = { "com.excilys.burleon.computerdatabase" })
public class ConsoleConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleConfig.class);

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
        ConsoleConfig.LOGGER.debug("Looking for Spring profiles...");
        if (this.environment.getActiveProfiles().length == 0) {
            ConsoleConfig.LOGGER.info("No Spring profile configured, running with default configuration.");
        } else {
            for (final String profile : this.environment.getActiveProfiles()) {
                ConsoleConfig.LOGGER.info("Detected Spring profile: {}", profile);
            }
        }
    }
}
