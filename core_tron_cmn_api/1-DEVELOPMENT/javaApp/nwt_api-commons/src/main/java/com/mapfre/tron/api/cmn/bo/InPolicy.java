package com.mapfre.tron.api.cmn.bo;

import java.io.Serializable;
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
 * The document input entity.
 *
 * @author pvraul1
 * @since 1.0.0
 * @version 10 jun. 2019 8:43:53
 *
 */
@Data
@GaiaBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("serial")
@ApiModel(description = "Document input")
@Validated
public class InPolicy implements Serializable {
    @JsonProperty("enrSqn")
    @ApiModelProperty(example = "0", value = "Endorsement sequence")
    private String enrSqn;
    @JsonProperty("aplVal")
    @ApiModelProperty(example = "0", required = true, value = "Application value")
    private Integer aplVal;
    @JsonProperty("aplEnrSqn")
    @ApiModelProperty(example = "0", value = "Application endorsement value")
    private Integer aplEnrSqn;
}
