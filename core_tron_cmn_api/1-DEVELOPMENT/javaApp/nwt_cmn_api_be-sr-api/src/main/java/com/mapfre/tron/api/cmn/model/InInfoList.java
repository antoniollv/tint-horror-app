package com.mapfre.tron.api.cmn.model;

import java.util.Map;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * Options values list input
 */
@Getter
@Setter
@ApiModel(description = "Options values list input")
@Validated
public class InInfoList {

    @JsonProperty("lstIdn")
    private String lstIdn = null;

    @JsonProperty("lstTyp")
    private String lstTyp = null;

    @JsonProperty("insVal")
    private String insVal = null;

    @JsonProperty("lstVrs")
    private String lstVrs = null;
    
    @JsonProperty("lstVal")
    private Map<String,Object> lstVal = null;

    @JsonProperty("cchChc")
    private boolean cchChc = false;
    
    
}
