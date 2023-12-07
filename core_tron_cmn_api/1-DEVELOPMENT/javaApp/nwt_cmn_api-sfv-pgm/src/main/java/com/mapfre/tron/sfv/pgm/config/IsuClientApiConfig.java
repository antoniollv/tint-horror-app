package com.mapfre.tron.sfv.pgm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mapfre.tron.api.isu.client.api.AttributeDefinitionApi;
import com.mapfre.tron.api.isu.client.api.IssueApi;
import com.mapfre.tron.api.isu.client.api.SimulationApi;
import com.mapfre.tron.api.isu.client.invoker.ApiClient;


@Configuration
public class IsuClientApiConfig {

    @Value("${app.env.tron.api.isu.basePath:https://trnic.desa.mapfre.net/nwt_isu_api_be-web/newtron/api/issue}")
    private String basePath;

    @Value("${app.env.tron.api.isu.userName:APITRON}")
    private String userName;

    @Value("${app.env.tron.api.isu.password:Mapfre2019}")
    private String password;

    public ApiClient apiClient() {
        ApiClient isuApiClient = new ApiClient();
        isuApiClient.setBasePath(basePath);
        isuApiClient.setUsername(userName);
        isuApiClient.setPassword(password);
        return isuApiClient;
    }

    @Bean
    public SimulationApi simulationApi() {
        return new SimulationApi(apiClient());
    }
    
    @Bean
    public IssueApi issueApi() {
        return new IssueApi(apiClient());
    }
    
    @Bean
    public AttributeDefinitionApi attributeDefinitionApi() {
        return new AttributeDefinitionApi(apiClient());
    }
}
