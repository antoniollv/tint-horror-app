/**
 * 
 */
package com.mapfre.tron.config.api;

import com.mapfre.dgtp.gaia.commons.env.EnvironmentAttributes;
import com.mapfre.dgtp.gaia.dl.connector.datasource.DataSourceManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Profile({EnvironmentAttributes.SPRING_JNDI_CONNECTION_PROFILE})
@Configuration
public class RealDatasourceConfig extends DatasourceConfig {

    @Value("${app.env.nwt.datasource.ref.jndi}")
    private String nwtRefJNDI;

    @Value("${app.env.tw.dl.datasource.ref.jndi}")
    private String twDlRefJNDI;

    @Value("${app.env.tw.pr.datasource.ref.jndi}")
    private String twPrRefJNDI;

    @Bean
    DataSourceManager dataSourceManager(){
      DataSourceManager dsManager = new DataSourceManager();
      dsManager.setDefaultDataSourceName("dataSource");
      return dsManager;
    }

    @Bean(name = "dataSource", destroyMethod = "")
    public DataSource dataSource() {
         JndiDataSourceLookup lookup = new JndiDataSourceLookup();
         return lookup.getDataSource(nwtRefJNDI);
    }

    @Bean(name = "dsTwDl", destroyMethod = "")
    public DataSource dataSourceTwDl() {
         JndiDataSourceLookup lookup = new JndiDataSourceLookup();
         return lookup.getDataSource(twDlRefJNDI);
    }

    @Bean(name = "transactionManagerTwDl")
    public DataSourceTransactionManager transactionManagerTwDl() {
        return new DataSourceTransactionManager(dataSourceTwDl());
    }

    @Bean(name = "dsTwPr", destroyMethod = "")
    public DataSource dataSourceTwPr() {
         JndiDataSourceLookup lookup = new JndiDataSourceLookup();
         return lookup.getDataSource(twPrRefJNDI);
    }

}
