package com.mapfre.tron.config.api.cmn;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mapfre.dgtp.gaia.webmvc.GaiaBeWebMvcConfigurerAdapter;
import com.mapfre.tron.api.commons.http.converter.TronApiHttpMessageConverter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@Configuration
@EnableSwagger2
public class NwtCmnApiBeWebConfig extends GaiaBeWebMvcConfigurerAdapter{
    
    @Value("${prop.swagger.enabled:true}")
    private boolean enableSwagger;

    @Bean
    public Docket swaggerConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .tags(
                        new Tag("Cache", ""),
                        new Tag("Common", ""),
                        new Tag("Constant", ""),
                        new Tag("Constant Definition", ""),
                        new Tag("Default", ""),
                        new Tag("Event", ""),
                        new Tag("File", ""),
                        new Tag("Flow-md", ""),
                        new Tag("Help", ""),
                        new Tag("Life", ""),
                        new Tag("Menu", ""),
                        new Tag("Movement Record", ""),
                        new Tag("Movement Definition", ""),
                        new Tag("Newtron Operation", ""),
                        new Tag("Reason event locking definition", ""),
                        new Tag("Self-Service", ""),
                        new Tag("Settings", ""),
                        new Tag("SubSector list by sector", ""),
                        new Tag("Traductions", ""),
                        new Tag("User info", ""),
                        new Tag("Variable Concepts Definition", ""),
                        new Tag("Variable Definition", ""),
                        new Tag("Validate Step", ""),
                        new Tag("Validation", ""),
                        new Tag("Values/options list", ""))
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mapfre.tron.api.cache")
                        .or(RequestHandlerSelectors.basePackage("com.mapfre.tron.api.cmn.sr")
                                .or(RequestHandlerSelectors.basePackage("com.mapfre.tron.gdc")
                                        .or(RequestHandlerSelectors.basePackage("com.mapfre.tron.sfv")))))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new TronApiHttpMessageConverter());
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }

    // added to resolve Use of @EnableWebMvc in app breaks swagger-ui.html redirect
    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
   }

}
