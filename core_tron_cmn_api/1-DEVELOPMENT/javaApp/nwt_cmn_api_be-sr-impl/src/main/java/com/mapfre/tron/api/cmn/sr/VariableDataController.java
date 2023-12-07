package com.mapfre.tron.api.cmn.sr;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapfre.tron.api.var.dat.bl.IBLVarDatQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for VariableData.
 * 
 * @author Javier Sangil
 * @version 27/11/2023
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Common",})
public class VariableDataController implements VariableDataApi {
    
    
    @Autowired
    protected IBLVarDatQry iBLCmnAtrQry;    
    
    /**
     * Post variable data . It will be mandatory send the parameters defined in the
     * service and the service will response with the output object defined.
     * 
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param fldNam    -> Field code
     * @param fldValVal -> Field Value
     * @param qtnVal    -> Quotation value
     * @param cmpVal    -> company
     * 
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ResponseEntity<Void> postVariableData(
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @NotNull @ApiParam(value = "Field code", required = true) @Valid @RequestParam(value = "fldNam", required = true) String fldNam,
	    @NotNull @ApiParam(value = "Field Value", required = true) @Valid @RequestParam(value = "fldValVal", required = true) String fldValVal,
	    @NotNull @ApiParam(value = "Quotation value", required = true) @Valid @RequestParam(value = "qtnVal", required = true) Long qtnVal,
	    @ApiParam(value = "company") @Valid @RequestParam(value = "cmpVal", required = false) Integer cmpVal) {
    
        log.info("The postVariableData rest operation had been called...");
  		
	iBLCmnAtrQry.postVariableData(cmpVal, usrVal, lngVal, fldNam, fldValVal, qtnVal);
		
	return new ResponseEntity("Operation succesfully done!", HttpStatus.OK);
			

	}
         
}        
         
         
         