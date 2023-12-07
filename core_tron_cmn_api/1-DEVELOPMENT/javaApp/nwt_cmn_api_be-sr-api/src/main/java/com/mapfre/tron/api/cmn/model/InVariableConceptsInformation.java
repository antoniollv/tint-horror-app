package com.mapfre.tron.api.cmn.model;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Input Data to query constants")
@Validated
public class InVariableConceptsInformation {
    
    @JsonProperty("cmpVal")
    private Integer cmpVal = null;
    
    @JsonProperty("lobVal")
    private Integer lobVal = null;
    
    @JsonProperty("mdtVal")
    private Integer mdtVal = null;
    
    @JsonProperty("crnVal")
    private Integer crnVal = null;
    
    @JsonProperty("cvrVal")
    private Integer cvrVal = null;
    
    @JsonProperty("frsLvlVal")
    private Integer frsLvlVal = null;
    
    @JsonProperty("scnLvlVal")
    private Integer scnLvlVal = null;
    
    @JsonProperty("thrLvlVal")
    private Integer thrLvlVal = null;
    
    @JsonProperty("agnVal")
    private Integer agnVal = null;
    
    @JsonProperty("delVal")
    private Integer delVal = null;
    
    @JsonProperty("sblVal")
    private Integer sblVal = null;
    
    @JsonProperty("frsDstHnlVal")
    private String frsDstHnlVal = null;
    
    @JsonProperty("scnDstHnlVal")
    private String scnDstHnlVal = null;
    
    @JsonProperty("thrDstHnlVal")
    private String thrDstHnlVal = null;
    
    @JsonProperty("gppVal")
    private String gppVal = null;
    
    @JsonProperty("plyVal")
    private String plyVal = null;
    
    @JsonProperty("vrbNam")
    private String vrbNam = null;

    
}
