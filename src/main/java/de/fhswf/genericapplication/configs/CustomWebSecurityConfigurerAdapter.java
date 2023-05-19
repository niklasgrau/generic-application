package de.fhswf.genericapplication.configs;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Basic implementation of web security config. Mainly to disable basic authentication.
 *
 * @author Niklas Grau
 */
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/javascript/**", "/webjars/**", "/images/**").permitAll()
                .antMatchers("/api/users/**").permitAll()
                .anyRequest().permitAll()
                .and()
                // Necessary to provide JDBC console
                .headers().frameOptions().disable()
                .and()
                // Makes other http methods (post, put, delete...) possible
                .csrf().disable()
        ;
    }
}
