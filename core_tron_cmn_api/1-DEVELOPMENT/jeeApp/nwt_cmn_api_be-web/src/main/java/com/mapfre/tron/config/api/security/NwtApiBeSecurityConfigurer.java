package com.mapfre.tron.config.api.security;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.mapfre.dgtp.gaia.commons.security.annotation.GaiaSecurityConfigHttp;

@Order(3)
@EnableGlobalMethodSecurity(
	      prePostEnabled = true, 
	      securedEnabled = true, 
	      jsr250Enabled = true)
public class NwtApiBeSecurityConfigurer implements GaiaSecurityConfigHttp {

    @Override
    public void configure(HttpSecurity http, AuthenticationSuccessHandler obtainUserInfoAuthenticationSuccessHandler)
            throws Exception {
        // TODO Auto-generated method stub
    }

}
