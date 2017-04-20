package com.excilys.burleon.computerdatabase.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.excilys.burleon.computerdatabase.persistence" })
public class RepositoryConfig {

}
