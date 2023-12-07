package com.mapfre.tron.api.bo;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.mapfre.dgtp.gaia.commons.annotation.GaiaBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The error component entity.
 *
 * @author pvraul1
 * @since 1.0.0
 * @version 28 may. 2019 15:47:14
 *
 */

@Data
@GaiaBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ApiModel(description = "Error Component")
@Validated
@SuppressWarnings("serial")
public class ErrorComponent implements Serializable {

    //@JsonProperty("code")
    //@ApiModelProperty(example = "4003", required = true, value = "Error Code")
    @NotNull
    private String code;

    //@JsonProperty("message")
    //@ApiModelProperty(example = "Error interno en el servicio", value = "Descriptive error message")
    private String message;

    //@JsonProperty("componenent")
    //@ApiModelProperty(example = "ConsultarDatosClienteBLImpl", value = "Component that generates the error")
    private String component;

    //@JsonProperty("rootcase")
    //@ApiModelProperty(example = "NullPointerException", value = "Error Cause")
    private String rootcase;

    //@JsonProperty("info")
    //@ApiModelProperty(value = "Error Information")
    @Valid
    private List<ErrorInfo> info;

}
