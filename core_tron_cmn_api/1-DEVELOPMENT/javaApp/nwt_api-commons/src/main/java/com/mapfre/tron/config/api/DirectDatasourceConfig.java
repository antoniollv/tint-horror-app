package com.mapfre.tron.config.api;

import com.mapfre.dgtp.gaia.commons.env.EnvironmentAttributes;
import com.mapfre.dgtp.gaia.dl.connector.datasource.DataSourceManager;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.pool.OracleDataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Profile(EnvironmentAttributes.SPRING_DIRECT_CONNECTION_PROFILE)
@Configuration
@Slf4j
public class DirectDatasourceConfig extends DatasourceConfig {

    // Access data to local DB.
    // Newtron
    @Value("${app.env.nwt.datasource.url}")
    private String nwtDataSourceUrl;
    @Value("${app.env.nwt.datasource.u}")
    private String nwtDataSourceU;
    @Value("${app.env.nwt.datasource.p}")
    private String nwtDataSourceP;

    // Tronweb DL
    @Value("${app.env.tw.dl.datasource.url}")
    private String twDlDataSourceUrl;
    @Value("${app.env.tw.dl.datasource.u}")
    private String twDlDataSourceU;
    @Value("${app.env.tw.dl.datasource.p}")
    private String twDlDataSourceP;

    // Tronweb Procedure
    @Value("${app.env.tw.pr.datasource.url}")
    private String twPrDataSourceUrl;
    @Value("${app.env.tw.pr.datasource.u}")
    private String twPrDataSourceU;
    @Value("${app.env.tw.pr.datasource.p}")
    private String twPrDataSourceP;

    @Override
    @SuppressWarnings("deprecation")
    @Bean(name = "dataSource")
    public javax.sql.DataSource dataSource() {
        OracleDataSource dataSource = null;
        try {
            dataSource = new OracleDataSource();
            dataSource.setURL(nwtDataSourceUrl);
            dataSource.setUser(nwtDataSourceU);
            dataSource.setPassword(nwtDataSourceP);
            //dataSource.setConnectionCachingEnabled(true);
        } catch (SQLException e) {
            log.error("There was an error while creating the datasource " + e.getMessage());
        }
        return dataSource;
    }

    @SuppressWarnings("deprecation")
    @Bean(name = "dsTwDl")
	    public DataSource dataSourceTwDl() {
        OracleDataSource dataSource = null;
        try {
            dataSource = new OracleDataSource();
            dataSource.setURL(twDlDataSourceUrl);
            dataSource.setUser(twDlDataSourceU);
            dataSource.setPassword(twDlDataSourceP);
            //dataSource.setConnectionCachingEnabled(true);
        } catch (SQLException e) {
            log.error("There was an error while creating the datasource (dsTwDl):" + e.getMessage());
        }
        return dataSource;
    }

    @Bean(name = "transactionManagerTwDl")
    public DataSourceTransactionManager transactionManagerTwDl() {
        return new DataSourceTransactionManager(dataSourceTwDl());
    }

    @SuppressWarnings("deprecation")
    @Bean(name = "dsTwPr")
    public javax.sql.DataSource dataSourceTwPr() {
        OracleDataSource dataSource = null;
        try {
            dataSource = new OracleDataSource();
            dataSource.setURL(twPrDataSourceUrl);
            dataSource.setUser(twPrDataSourceU);
            dataSource.setPassword(twPrDataSourceP);
            //dataSource.setConnectionCachingEnabled(true);
        } catch (SQLException e) {
            log.error("There was an error while creating the datasource (dsTwPr): " + e.getMessage());
        }
        return dataSource;
    }

}
