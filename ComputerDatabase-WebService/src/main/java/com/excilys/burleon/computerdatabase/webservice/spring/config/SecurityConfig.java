package com.excilys.burleon.computerdatabase.webservice.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;


import com.excilys.burleon.computerdatabase.service.iservice.IUserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    IUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    

    @Bean
    public AuthenticationEntryPoint entryPoint()
    {
      BasicAuthenticationEntryPoint authenticationEntryPoint = new BasicAuthenticationEntryPoint();
      authenticationEntryPoint.setRealmName("Computer Realm");
      return authenticationEntryPoint;
    }
    

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.userService);
        authenticationProvider.setPasswordEncoder(this.passwordEncoder);
        return authenticationProvider;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
        .httpBasic()
        	.authenticationEntryPoint(entryPoint())
        	.and()
        	.authorizeRequests()
        	.antMatchers(HttpMethod.GET, "/**").permitAll()
            .anyRequest().authenticated();
        

    }

    @Autowired
    public void configureGlobalSecurity(
    		final AuthenticationManagerBuilder auth, 
    		final DaoAuthenticationProvider prov) throws Exception {
        auth.userDetailsService(this.userService);
        auth.authenticationProvider(prov);
    }
    

}
