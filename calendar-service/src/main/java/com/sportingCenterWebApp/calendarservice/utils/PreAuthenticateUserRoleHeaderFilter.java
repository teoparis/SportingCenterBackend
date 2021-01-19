package com.sportingCenterWebApp.calendarservice.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PreAuthenticateUserRoleHeaderFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("Authorization");

        String rolesString = ((HttpServletRequest) servletRequest).getHeader("roles");
        List<String> roles = Arrays.asList(rolesString.split(" "));
        System.out.println("Questi sono i ruoli dell'utente autenticato :" + roles);

        List<SimpleGrantedAuthority> authorities = GeneralUtils.buildSimpleGrantedAuthorities(roles);
        System.out.println(authorities);
        PreAuthenticatedAuthenticationToken authentication
                = new PreAuthenticatedAuthenticationToken(
                "userName", null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
