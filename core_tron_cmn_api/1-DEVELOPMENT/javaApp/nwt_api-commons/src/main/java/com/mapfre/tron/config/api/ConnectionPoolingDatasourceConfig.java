package com.mapfre.tron.config.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@Profile({"pooling-connection"})
public class ConnectionPoolingDatasourceConfig extends DatasourceConfig{


    @Bean(name = "transactionManagerDM")
    public DataSourceTransactionManager transactionManagerTwDl() {
        return new DataSourceTransactionManager(dataSourceTwDl());
    }

    @Bean(name="dataSource", destroyMethod = "")
    @ConfigurationProperties(prefix = "spring.datasource.nwt")
    public DataSource dataSource() {

        return DataSourceBuilder.create().build();
    }

    @Bean(name= "dsTwDl", destroyMethod = "")
    @ConfigurationProperties(prefix = "spring.datasource.twdl")
    public DataSource dataSourceTwDl() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name= "dsTwPr", destroyMethod = "")
    @ConfigurationProperties(prefix = "spring.datasource.twpr")
    public DataSource dataSourceTwPr() {
        return DataSourceBuilder.create().build();
    }


}