package com.mapfre.tron.sfv.pgm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mapfre.tron.api.tsy.client.api.TreasuryApi;
import com.mapfre.tron.api.tsy.client.invoker.ApiClient;

/**
 * The THP API Client configuration.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 12 May 2023 - 09:17:40
 *
 */
@Configuration
public class TsyClientApiConfig {

    @Value("${app.env.tron.api.tsy.basePath:https://trnic.desa.mapfre.net/nwt_tsy_api_be-web/newtron/api/thirdparty}")
    private String basePath;

    @Value("${app.env.tron.api.tsy.userName:APITRON}")
    private String userName;

    @Value("${app.env.tron.api.tsy.password:Mapfre2019}")
    private String password;

    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(basePath);
        apiClient.setUsername(userName);
        apiClient.setPassword(password);
        return apiClient;
    }

    @Bean
    public TreasuryApi treasuryApi() {
        return new TreasuryApi(apiClient());
    }
}
