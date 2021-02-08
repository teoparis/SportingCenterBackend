package com.sportingCenterWebApp.activityservice.security;


import com.sportingCenterWebApp.activityservice.utils.PreAuthenticateUserRoleHeaderFilter;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.ConcurrentSessionFilter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .addFilterBefore(new PreAuthenticateUserRoleHeaderFilter(),
                        BasicAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/all/**").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated();
    }
}
