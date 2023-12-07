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
 * The SFMC event response.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 25 may. 2021 - 16:43:36
 *
 */
@Data
@GaiaBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "SFMC - Event")
@Validated
public class ResponseSFMCEvent {

    @JsonProperty("eventInstanceId")
    @ApiModelProperty(example = "48308c16-8657-488b-b463-929002028c28", required = true, value = "Event Instance Identifier")
    private String eventInstanceId;

}
