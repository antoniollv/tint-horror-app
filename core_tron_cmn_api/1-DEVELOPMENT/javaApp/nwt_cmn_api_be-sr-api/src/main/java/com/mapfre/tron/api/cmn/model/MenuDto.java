package com.mapfre.tron.api.cmn.model;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenS;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Newtron Menu object
 */
@AllArgsConstructor
@Getter
@Setter
@ApiModel(description = "Newtron Menu object")
@Validated
public class MenuDto {

    @JsonProperty("header")
    private List<OCmnMenS> header = null;

    @JsonProperty("list")
    private List<OCmnMenS> list = null;
    
}
