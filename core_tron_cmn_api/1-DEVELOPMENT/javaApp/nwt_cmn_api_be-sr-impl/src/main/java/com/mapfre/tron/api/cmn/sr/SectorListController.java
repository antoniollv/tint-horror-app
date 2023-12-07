/**
 * 
 */
package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.prt.sec.bo.OPrtSecS;
import com.mapfre.tron.api.prt.sec.bl.IBlPrtSec;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * SectorListController
 * 
 * @author AMINJOU
 *
 */

@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class SectorListController implements SectorListApi {
	
	
	@Autowired
	protected IBlPrtSec iBlPrtSec;
	
	
	
	/**
	 * Query sectors by company. 
	 * It will return the types of languages. 
	 * It will be mandatory send the parameters defined in the service and the service will response 
	 * with the output object defined. operationId: getsectorList
	 * 
	 * 
	 * @param cmpVal
	 * @param lngVal
	 * @param usrVal
	 * 
	 * @return List<OPrtSecS>
	 * 
	 */
	@Override
	public ResponseEntity<List<OPrtSecS>> sectorListGet(
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal) {

		log.info("The getContactFromSecondLevel rest operation had been called...");
		
		
		List<OPrtSecS> lvOThpCntS = iBlPrtSec.getSec(cmpVal, lngVal, usrVal);
	    
        return new ResponseEntity<>(lvOThpCntS, HttpStatus.OK);
	
    }

	

}
