package com.mapfre.tron.api.bo;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import com.mapfre.dgtp.gaia.commons.annotation.GaiaBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The errorInfo entity.
 *
 * @author pvraul1
 * @since 1.0.0
 * @version 28 may. 2019 15:56:34
 *
 */

@Data
@GaiaBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ApiModel(description = "Error Info")
@Validated
@SuppressWarnings("serial")
public class ErrorInfo implements Serializable {

    //@JsonProperty("key")
    //@ApiModelProperty(example = "key", value = "Key")
    private String key;

    //@JsonProperty("value")
    //@ApiModelProperty(example = "value", value = "Value")
    private String value;

}
