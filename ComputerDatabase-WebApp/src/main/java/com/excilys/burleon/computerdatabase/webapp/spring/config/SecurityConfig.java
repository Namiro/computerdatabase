package com.excilys.burleon.computerdatabase.webapp.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.burleon.computerdatabase.service.iservice.IUserService;
import com.excilys.burleon.computerdatabase.webapp.constant.Data;
import com.excilys.burleon.computerdatabase.webapp.constant.View;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    IUserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.userService);
        authenticationProvider.setPasswordEncoder(this.passwordEncoder);
        return authenticationProvider;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/computerlist")
        .and()
        	.authorizeRequests()
        	.antMatchers("/").permitAll()
        	.antMatchers("/"+View.VIEW_AUTHENTICATION).permitAll()
        	.antMatchers("/css/**").permitAll()
        	.antMatchers("/fonts/**").permitAll()
            .antMatchers("/i18/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/" + View.VIEW_COMPUTER_LIST).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/" + View.VIEW_COMPUTER_LIST + "?" + Data.POPUP + "=" + Data.POPUP_LOGIN)
            .usernameParameter(Data.USER_USERNAME).passwordParameter(Data.USER_PASSWORD)
            .loginProcessingUrl("/" + View.VIEW_AUTHENTICATION)
            .defaultSuccessUrl("/" + View.VIEW_COMPUTER_LIST + "?" + Data.LOGIN_SUCCESS + "=true").permitAll()
            .failureUrl("/" + View.VIEW_COMPUTER_LIST + "?" + Data.POPUP + "=" + Data.POPUP_LOGIN + "&"
            		+ Data.LOGIN_SUCCESS + "=false")
            .and()
            .logout().logoutUrl("/" + View.VIEW_LOGOUT).logoutSuccessUrl("/" + View.VIEW_COMPUTER_LIST).permitAll();

        // .authorizeRequests().antMatchers("/", "/" +
        // View.VIEW_COMPUTER_LIST)
        // .access("hasRole('USER') or
        // hasRole('ADMIN')").and().formLogin().loginPage("/login")
        // .usernameParameter(Data.USER_USERNAME).passwordParameter(Data.USER_PASSWORD)
    }

    @Autowired
    public void configureGlobalSecurity(
    		final AuthenticationManagerBuilder auth, 
    		final DaoAuthenticationProvider prov) throws Exception {
        auth.userDetailsService(this.userService);
        auth.authenticationProvider(prov);
    }
}
