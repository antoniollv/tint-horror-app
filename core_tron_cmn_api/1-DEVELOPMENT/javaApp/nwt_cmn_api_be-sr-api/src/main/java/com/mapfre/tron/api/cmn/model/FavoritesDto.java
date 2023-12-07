package com.mapfre.tron.api.cmn.model;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@ApiModel(description = "Newtron Favorite object")
@Validated
public class FavoritesDto {

    @JsonProperty("identifier")
    private String oprIdnVal = null;
    
}
