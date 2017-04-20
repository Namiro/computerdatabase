package com.excilys.burleon.computerdatabase.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
// @PropertySource({"file:/home/excilys/Programs/eclipse/workspace/ComputerDatabase/src/main/resources/infrastructure.properties"
// })
public class InfrastructureConfig {

    /*
     * @Autowired Environment environment;
     *
     * @Autowired private DataSource dataSource;
     *
     * private Map<String, Object> additionalProperties() { final Map<String,
     * Object> properties = new HashMap<>();
     * properties.put("hibernate.validator.apply_to_ddl", "false");
     * properties.put("hibernate.validator.autoregister_listeners", "false");
     * properties.put("hibernate.dialect",
     * this.environment.getProperty("hibernate.dialect"));
     * properties.put("hibernate.generate_statistics",
     * this.environment.getProperty("hibernate.generate_statistics")); //
     * Second level cache configuration and so on. return properties; }
     *
     * @Bean public EntityManagerFactory entityManagerFactory() { final
     * LocalContainerEntityManagerFactoryBean em = new
     * LocalContainerEntityManagerFactoryBean();
     * em.setDataSource(this.dataSource);
     * em.setPersistenceUnitName("javaconfigSamplePersistenceUnit");
     * em.setPackagesToScan("com.javaetmoi.sample.domain");
     * em.setJpaVendorAdapter(this.jpaVendorAdaper());
     * em.setJpaPropertyMap(this.additionalProperties());
     * em.afterPropertiesSet(); return em.getObject(); }
     *
     * @Bean public JpaVendorAdapter jpaVendorAdaper() { final
     * HibernateJpaVendorAdapter vendorAdapter = new
     * HibernateJpaVendorAdapter();
     * vendorAdapter.setDatabase(this.env.getProperty("jpa.database",
     * Database.class));
     * vendorAdapter.setShowSql(this.env.getProperty("jpa.showSql",
     * Boolean.class));
     * vendorAdapter.setGenerateDdl(this.env.getProperty("jpa.generateDdl",
     * Boolean.class)); return vendorAdapter; }
     *
     * @Bean public JpaTransactionManager transactionManager() { final
     * JpaTransactionManager jpaTransactionManager = new
     * JpaTransactionManager();
     * jpaTransactionManager.setEntityManagerFactory(this.entityManagerFactory
     * ()); return jpaTransactionManager; }
     *
     * @Bean public TransactionTemplate transactionTemplate() { final
     * TransactionTemplate transactionTemplate = new TransactionTemplate();
     * transactionTemplate.setTransactionManager(this.transactionManager());
     * return transactionTemplate; }
     */
}
