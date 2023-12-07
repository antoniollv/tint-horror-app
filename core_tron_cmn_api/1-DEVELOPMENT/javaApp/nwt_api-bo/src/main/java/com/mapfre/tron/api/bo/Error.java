package com.mapfre.tron.api.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
 * The error entity.
 *
 * @author pvraul1
 * @since 1.0.0
 * @version 28 may. 2019 15:30:31
 *
 */
@Data
@GaiaBean
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ApiModel(description = "Report")
@Validated
@SuppressWarnings("serial")
public class Error implements Serializable {

    //@JsonProperty("code")
    //@ApiModelProperty(example = "4003", required = true, value = "Error Code")
    @NotNull
    private String code;

    //@JsonProperty("message")
    //@ApiModelProperty(example = "Error interno en el servicio", required = true, value = "Descriptive error message")
    @NotNull
    private String message;

    //@JsonProperty("type")
    //@ApiModelProperty(example = "Puntero nulo", required = true, value = "Error Type")
    @NotNull
    private String type;

    //@JsonProperty("context")
    //@ApiModelProperty(example = "Búsqueda de clientes", value = "Error Context")
    private String context;

    //@JsonProperty("exception")
    //@ApiModelProperty(example = "NullPointerException", value = "Exception")
    private String exception;

    //@JsonProperty("component")
    //@ApiModelProperty(example = "ConsultarDatosClienteBLImpl", required = true, value = "Component that generates the error")
    @NotNull
    private String component;

    //@JsonProperty("application")
    //@ApiModelProperty(example = "MRC_Backend", required = true, value = "Application that generates the error")
    @NotNull
    private String application;

    //@JsonProperty("timestamp")
    //@ApiModelProperty(required = true, value = "Error Timestamp")
    @NotNull
    private Date timestamp;

    //@JsonProperty("errors")
    //@ApiModelProperty(required = true, value = "Errors List")
    @NotNull
    @Valid
    @Builder.Default
    private List<ErrorComponent> errors = new ArrayList<>();

}
