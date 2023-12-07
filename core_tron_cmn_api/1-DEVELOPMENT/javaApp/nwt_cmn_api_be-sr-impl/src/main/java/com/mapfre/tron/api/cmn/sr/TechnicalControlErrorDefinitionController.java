package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapfre.nwt.trn.tcd.tce.bo.OTcdTceS;
import com.mapfre.tron.api.tcd.tce.bl.IBlTcdTce;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for TechnicalControlErrorDefinition.
 * 
 * @author Javier Sangil
 * @version 13/10/2023
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Technical Control Error Definition",})
public class TechnicalControlErrorDefinitionController implements TechnicalControlErrorDefinitionApi {
    
    @Autowired
    protected IBlTcdTce iBlTcdTce;
    
    /**
     * Query Technical Control Definition
     *
     * @author Javier Sangil
     * 
     * 
     * @param cmpVal -> company code
     * @param usrVal -> user value
     * @param lngVal -> language value
     * @param errValList -> Error List Value
     * 
     * @return List<OTcdTceS>
     * 
     */
    @Override
    public ResponseEntity<List<OTcdTceS>> getTechnicalControlDefinition(
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @NotNull @ApiParam(value = "Error List Value", required = true) @Valid @RequestParam(value = "errValList", required = true) List<String> errValList,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal) {

	log.info("The getTechnicalControlDefinition rest operation had been called...");

	List<OTcdTceS> lvOTcdTceS = iBlTcdTce.getTechnicalControlDefinition(cmpVal, lngVal, usrVal, errValList);

	return new ResponseEntity<>(lvOTcdTceS, HttpStatus.OK);

    }
    
}
