package com.commerce.config;

import com.commerce.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class that takes care of the configuration for the authentication process
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService detailService;

    private final CustomAuthenticationProvider authenticationProvider;

    @Autowired
    public WebSecurityConfig(CustomUserDetailService detailService,
                             CustomAuthenticationProvider customAuthenticationProvider){
        this.detailService = detailService;
        this.authenticationProvider = customAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/frontoffice", "/frontoffice/signup", "/frontoffice/signup/submit").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/backoffice/**").hasRole("USER")
                .antMatchers("/frontoffice/shop").hasRole("CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .defaultSuccessUrl("/frontoffice")
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied")
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}