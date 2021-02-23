package com.hoaxify.ws.configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();  //authentication mekanizmamımız => httpBasic
                            //hangi endpointlerin authentication lı olacağına karar veriyoruz.
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/1.0/auth").authenticated()
                .and()
                .authorizeRequests().anyRequest().permitAll();
    }
}
