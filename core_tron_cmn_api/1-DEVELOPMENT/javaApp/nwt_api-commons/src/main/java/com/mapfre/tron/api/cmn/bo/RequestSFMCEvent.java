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
 * The input event request.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 25 may. 2021 - 16:26:53
 *
 */
@Data
@GaiaBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "SFMC - Event")
@Validated
public class RequestSFMCEvent {

    @JsonProperty("ContactKey")
    @ApiModelProperty(example = "CIP-12345678", required = true, value = "Contact Key")
    private String contactKey;

    @JsonProperty("EventDefinitionKey")
    @ApiModelProperty(example = "APIEvent-92a715f0-efd1-bc24-8d5f-37159034ffc2", required = true, value = "Event Definition Key")
    private String eventDefinitionKey;

    @JsonProperty("Data")
    @ApiModelProperty(required = true, value = "Data")
    private EventData data;

}
