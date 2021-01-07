package com.thoughtmechanix.zuulsvr.filters;

import com.netflix.zuul.ZuulFilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.thoughtmechanix.zuulsvr.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.jws.soap.SOAPBinding;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class AuthenticationFilter extends ZuulFilter {
    private static final int FILTER_ORDER =  2;
    private static final boolean  SHOULD_FILTER=true;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    FilterUtils filterUtils;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String filterType() {
        return filterUtils.PRE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isAuthTokenPresent() {
        if (filterUtils.getAuthToken() !=null){
            return true;
        }

        return false;
    }

    private String isAuthTokenValid(){
        ResponseEntity<String> restExchange = null;
        try {
            restExchange =
                    restTemplate.exchange(
                            "http://authentication-service/api/auth/{token}",
                            HttpMethod.GET,
                            null, String.class, filterUtils.getAuthToken());
        }
        catch(HttpClientErrorException ex){
            if (ex.getStatusCode()==HttpStatus.UNAUTHORIZED) {
                return null;
            }

            throw ex;
        }
        System.out.println(restExchange.getBody());
        return restExchange.getBody();
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        //If we are dealing with a call to the authentication service, let the call go through without authenticating
        if ( ctx.getRequest().getRequestURI().equals("/authentication-service/api/auth/signin")){
            return null;
        }

        if (isAuthTokenPresent()){
            logger.debug("Authentication token is present.");
        }else{
            logger.debug("Authentication token is not present.");

            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            ctx.setSendZuulResponse(false);
        }

        String userRoles = isAuthTokenValid();
        if (userRoles!=null){
            //filterUtils.setUserId(userInfo.getUserId());
            ctx.addZuulRequestHeader("roles",userRoles);
            logger.debug("Authentication token is valid.");
            return null;
        }

        logger.debug("Authentication token is not valid.");
        ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        ctx.setSendZuulResponse(false);

        return null;

    }
}
