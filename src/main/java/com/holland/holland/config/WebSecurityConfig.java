package com.holland.holland.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .addFilterAfter(tokenFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable();
//                .authorizeRequests()
//                .antMatchers("/**").access("hasRole('ROLE_USER')")
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .anyRequest().authenticated().and() // access after login
//                .rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7).key("")
//                .and()
//                .formLogin().loginProcessingUrl("user/login").permitAll().and()
//                .logout().permitAll();
    }

}
