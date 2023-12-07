package com.mapfre.tron.config.api.cmn;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapfre.dgtp.gaia.dl.connector.datasource.DataSourceManager;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.config.api.security.NwtApiBeSecurityConfigurer;

@Configuration
public class NwtCmnApiBeConfig {

    @Bean
    public NwtApiBeSecurityConfigurer securityConfigurer() {
        return new NwtApiBeSecurityConfigurer();
    }

    @Bean
    @ConditionalOnMissingBean
    DataSourceManager dataSourceManager() {
        DataSourceManager dsManager = new DataSourceManager();
        dsManager.setDefaultDataSourceName("dataSource");
        return dsManager;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);

        return mapper;
    }
    
    @Autowired
    @Qualifier("dsTwDl")
    private DataSource tablesDatasource;

    @Primary
    @Bean(name = "npJdbcTemplate")
    NamedParameterJdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(tablesDatasource);
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Primary
    @Bean(name = "jdbcTemplate")
    JdbcTemplate jdbcTemplate0() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(tablesDatasource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
        return jdbcTemplate;
    }

    @Primary
    @Bean(name = "jdbcFetch1")
    NamedParameterJdbcTemplate jdbcTemplate1() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(tablesDatasource);
        jdbcTemplate.setFetchSize(1);
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Primary
    @Bean(name = "jdbcFetch1000")
    NamedParameterJdbcTemplate jdbcTemplate1000() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(tablesDatasource);
        jdbcTemplate.setFetchSize(1000);
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

}
