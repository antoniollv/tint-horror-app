package com.mapfre.tron.config.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import com.mapfre.dgtp.gaia.commons.env.EnvironmentAttributes;
import com.mapfre.dgtp.gaia.config.annotation.security.DefaultGaiaBackendSecurityLdap;

@Profile(EnvironmentAttributes.SPRING_REAL_SECURITY_PROFILE)
@EnableWebSecurity
public class NwtApiBeSecurityReal extends DefaultGaiaBackendSecurityLdap{

    @Autowired
    NwtApiBeSecurityConfigurer securityConfigurer;

    /**
     * @deprecated
     */
    @Deprecated
    @Override
    protected void configureSecurityHttp(HttpSecurity http) throws Exception {
        securityConfigurer.configure(http, null);
    }

}
