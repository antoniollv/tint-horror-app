package com.mapfre.tron.api.cmn.bo;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapfre.dgtp.gaia.commons.annotation.GaiaBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The input token response.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 25 may. 2021 - 12:49:23
 *
 */
@Data
@GaiaBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "SFMC - Token")
@Validated
public class ResponseSFMCToken {

    @JsonProperty("access_token")
    @ApiModelProperty(example = "eyJhbGciOiJIUzI1NiIsImtpZCI6IjQiLCJ2ZXIiOiIxIiwidHlwIjoiSldUIn0.", required = true, value = "Access token")
    private String access_token;

    @JsonProperty("token_type")
    @ApiModelProperty(example = "Bearer", required = true, value = "Token type")
    private String token_type;

    @JsonProperty("expires_in")
    @ApiModelProperty(example = "1079", required = true, value = "Expire time")
    private Integer expires_in;

    @JsonProperty("scope")
    @ApiModelProperty(example = "documents_and_images_read", required = true, value = "Scope")
    private String scope;

    @JsonProperty("soap_instance_url")
    @ApiModelProperty(example = "https://mcq6zgrtgr273f2ky047vrt3hz1y.soap.marketingcloudapis.com/", required = true, value = "Soap instance url type")
    private String soap_instance_url;

    @JsonProperty("rest_instance_url")
    @ApiModelProperty(example = "https://mcq6zgrtgr273f2ky047vrt3hz1y.rest.marketingcloudapis.com/", required = true, value = "Rest instance url type")
    private String rest_instance_url;

}
