package com.mapfre.tron.sfv.pgm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mapfre.tron.api.thp.client.api.CommissionChartApi;
import com.mapfre.tron.api.thp.client.api.GenericThirdPartyApi;
import com.mapfre.tron.api.thp.client.api.InsuredPartyApi;
import com.mapfre.tron.api.thp.client.invoker.ApiClient;

/**
 * The THP API Client configuration.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 12 May 2023 - 09:17:40
 *
 */
@Configuration
public class ThpClientApiConfig {

    @Value("${app.env.tron.api.thp.basePath:https://trnic.desa.mapfre.net/nwt_thp_api_be-web/newtron/api/thirdparty}")
    private String basePath;

    @Value("${app.env.tron.api.thp.userName:APITRON}")
    private String userName;

    @Value("${app.env.tron.api.thp.password:Mapfre2019}")
    private String password;

    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(basePath);
        apiClient.setUsername(userName);
        apiClient.setPassword(password);
        return apiClient;
    }

    @Bean
    public InsuredPartyApi insuredPartyApi() {
        return new InsuredPartyApi(apiClient());
    }
    
    @Bean
    public GenericThirdPartyApi genericThirdPartyApi() {
        return new GenericThirdPartyApi(apiClient());
    }
    
    @Bean
    public CommissionChartApi commissionChartApi() {
        return new CommissionChartApi(apiClient());
    }
}
