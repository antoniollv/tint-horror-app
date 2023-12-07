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
 * The input token request.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 25 may. 2021 - 12:43:12
 *
 */
@Data
@GaiaBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "SFMC - Token")
@Validated
public class RequestSFMCToken {

    @JsonProperty("client_id")
    @ApiModelProperty(example = "p2qugbpes4dmx4nc6ia0d4r2", required = true, value = "Client identifier")
    private String client_id;

    @JsonProperty("client_secret")
    @ApiModelProperty(example = "VENqRWh5aRgxTD3V10hYJK3O", required = true, value = "Client secret")
    private String client_secret;

    @JsonProperty("grant_type")
    @ApiModelProperty(example = "client_credentials", required = true, value = "Client credentials")
    private String grant_type;

}
