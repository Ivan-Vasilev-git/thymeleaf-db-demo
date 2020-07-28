package com.company.thymeleafdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Injects the securityDataSource bean defined in DataSourceConfig.java
 * In configure() tell Spring Security to use this data source for JDBC authentication
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  // add a reference to security data source

  @Autowired
  @Qualifier("securityDataSource")
  private DataSource securityDataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(securityDataSource);
  }

  /**
   * Display content based on user role:
   * EMPLOYEE: list employees
   * MANAGER: list, add, update employees
   * ADMIN: list, add, update, delete employees
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .antMatchers("/employees/showForm*").hasAnyRole("MANAGER", "ADMIN")
        .antMatchers("/employees/save*").hasAnyRole("MANAGER", "ADMIN")
        .antMatchers("/employees/delete").hasRole("ADMIN")
        .antMatchers("/employees/**").hasRole("EMPLOYEE")
        .antMatchers("/resources/**").permitAll()
        .and()
        .formLogin()
        .loginPage("/showMyLoginPage")
        .loginProcessingUrl("/authenticateTheUser")
        .permitAll()
        .and()
        .logout().permitAll()
        .and()
        .exceptionHandling().accessDeniedPage("/access-denied");

  }
}
