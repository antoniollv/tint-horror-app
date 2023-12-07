package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;
import com.mapfre.tron.api.cmn.atr.bl.IBLCmnAtrQry;
import com.mapfre.tron.api.cmn.model.InVariableConceptsInformation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for types services.
 * 
 * @author MAJOGUAM
 * @since 1.8
 * @version 24/02/2021
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Variable Concepts Definition",})
public class VariableConceptsDefinitionController implements VariableConceptsDefinitionApi {
    
    
    @Autowired
    protected IBLCmnAtrQry iBLCmnAtrQry;
	
	
    
    
	/**
	 * 
	 * @param usrVal -> user value
	 * @param lngVal -> language value
	 * @param inConstantInformation 
	 * @return List<OPlyAtrS>
	 * 
	 */
	@Override
    public ResponseEntity<List<OPlyAtrS>> postVariableConceptsDefinition(@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,@ApiParam(value = "Language code" ,required=true) @RequestHeader(value="lngVal", required=true) String lngVal,@ApiParam(value = "Input Data to query constants" ,required=true )  @Valid @RequestBody InVariableConceptsInformation inConstantInformation) {
    
        log.info("The postVariableConceptsDefinition rest operation had been called...");
  		
	List<OPlyAtrS> oPlyAtrSList = iBLCmnAtrQry.atr(usrVal, lngVal, inConstantInformation);
		
	return new ResponseEntity<>(oPlyAtrSList, HttpStatus.OK);
			

	}

}
