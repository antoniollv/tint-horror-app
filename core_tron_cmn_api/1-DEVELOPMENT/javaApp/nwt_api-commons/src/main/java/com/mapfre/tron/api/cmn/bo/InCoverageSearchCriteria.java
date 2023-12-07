package com.mapfre.tron.api.cmn.bo;


import java.io.Serializable;
import java.util.Date;

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
 * The data selection entity.
 *
 * @author pvraul1
 * @since 1.0.0
 * @version 9 ago. 2019 9:55:20
 *
 */
@ApiModel(description = "Data selection")
@Validated
@Data
@GaiaBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("serial")
public class InCoverageSearchCriteria implements Serializable {

    @JsonProperty("lobVal")
    @ApiModelProperty(example = "301", value = "Line of Business")
    private Integer lobVal;

    @JsonProperty("mdtVal")
    @ApiModelProperty(example = "99999", value = "Modality")
    private Integer mdtVal;

    @JsonProperty("vldDat")
    @ApiModelProperty(value = "Valid Date")
    private Date vldDat;

}
