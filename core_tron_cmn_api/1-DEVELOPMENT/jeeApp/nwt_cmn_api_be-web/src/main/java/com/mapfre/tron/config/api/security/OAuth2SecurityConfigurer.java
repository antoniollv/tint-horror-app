package com.mapfre.tron.config.api.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.mapfre.dgtp.gaia.commons.env.EnvironmentAttributes;

/**
 * The oauth2 security configuration class.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Feb 2023 - 13:02:02
 *
 */
@Order(2)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile({EnvironmentAttributes.SPRING_DEMO_SECURITY_PROFILE, EnvironmentAttributes.SPRING_DEMO_SECURITY_PROFILE})
public class OAuth2SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    protected Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .requestMatcher(new BasicRequestMatcher())
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .formLogin().disable()
        .httpBasic().disable()
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .oauth2ResourceServer().jwt();
    }

    @Bean
    public NimbusJwtDecoder mimbusJwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri(env.getProperty("spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
                        "https://login.microsoftonline.com/5cc6c66d-ffb2-469f-9385-cda840e57836/discovery/keys"))
                .restOperations(SecurityUtils.getRestOperations(env))
                .build();
    }

    private static class BasicRequestMatcher implements RequestMatcher {
        @Override
        public boolean matches(HttpServletRequest request) {
            String auth = request.getHeader("Authorization");
            return (auth != null && auth.startsWith("Bearer"));
        }
    }

}