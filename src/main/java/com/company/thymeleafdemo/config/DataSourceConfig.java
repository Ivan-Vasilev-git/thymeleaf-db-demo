package com.company.thymeleafdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

/**
 * @EnableJpaRepositories tells app to use JPA repositories defined in the given package
 * Package name is read from resources/application.properties file
 */
@Configuration
@EnableJpaRepositories(basePackages = {"${spring.data.jpa.repository.packages}"})
public class DataSourceConfig {

  /**
   * Create a datasource for main app data. Read data "employee" from database.
   * Datasource is configured with @ConfigurationProperties.
   * Read properties from the file resources/application.properties with prefix "spring.datasource"
   *
   * @return DataSource
   */
  @Primary
  @Bean
  @ConfigurationProperties(prefix = "app.datasource")
  public DataSource appDataSource() {
    return DataSourceBuilder.create().build();
  }

  /**
   * The entity manager factory tells Spring Data JPA which packages to scan for JPA entities.
   * The @ConfigurationProperties will read properties from the config file (application.properties).
   * Read the properties from the file with the prefix: "spring.data.jpa.entity".
   */
  @Bean
  @ConfigurationProperties(prefix = "spring.data.jpa.entity")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                     DataSource appDataSource) {
    return builder
        .dataSource(appDataSource)
        .build();
  }

  /**
   * Configure datasource to access the security database.
   * By default, Spring Security uses JDBC(no JPA) - need only a datasource.
   * Read properties from the file with prefix "security.datasource": jdbc-url, username, password.
   */
  @Bean
  @ConfigurationProperties(prefix = "security.datasource")
  public DataSource securityDataSource() {
    return DataSourceBuilder.create().build();
  }
}
