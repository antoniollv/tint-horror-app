package com.mapfre.tron.config.api.security;

import com.mapfre.dgtp.gaia.commons.env.EnvironmentAttributes;
import com.mapfre.dgtp.gaia.commons.security.annotation.AbstractGaiaSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * The demo security config class.
 *
 * @author pvraul1
 * @since 1.0.0
 * @version 21 jun. 2019 12:53:43
 *
 */
@Profile(EnvironmentAttributes.SPRING_DEMO_SECURITY_PROFILE)
@EnableWebSecurity
public class NwtCmnApiBeSecurityDemo extends AbstractGaiaSecurity {

    @Autowired
    NwtApiBeSecurityConfigurer securityConfigurer;

    
    /**
     * @deprecated
     */
    @Deprecated
    @Override
    protected void configureSecurityHttp(HttpSecurity http) throws Exception {
        if (securityConfigurer != null) {
            securityConfigurer.configure(http, null);
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("APITRON").password("{noop}Mapfre2019").roles("ROL_APINWT");
    }

}
