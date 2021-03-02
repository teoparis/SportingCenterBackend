package com.sportingCenterWebApp.calendarservice.security;


import com.sportingCenterWebApp.calendarservice.utils.PreAuthenticateUserRoleHeaderFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .addFilterBefore(new PreAuthenticateUserRoleHeaderFilter(),
                        BasicAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/user/**").hasRole("USER")
                    .antMatchers("/trainer/**").hasRole("MODERATOR")
                .anyRequest()
                .authenticated();
    }
}
