package com.mapfre.tron.api.cmn.sr;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapfre.nwt.trn.trn.evn.bo.OTrnEvnS;
import com.mapfre.tron.api.cmn.trn.evs.IBlCmnTrnEvsQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author AMINJOU
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Event",})
public class UpdateEventController implements UpdateEventApi {
	
	@Autowired
	protected IBlCmnTrnEvsQry iBlCmnTrnEvsQry;
	
	
	
	/**
	 * update received event as processed
	 * 
	 * @param cmpVal
	 * @param lngVal
	 * @param usrVal
	 * @param oTrnEvnS
	 * 
	 */
	@Override
	public ResponseEntity<Void> setUpdateEvent(
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
			@ApiParam(value = "Event", required = true) @Valid @RequestBody OTrnEvnS oTrnEvnS) {
        log.info("The setUpdateEvent rest operation had been called...");

        iBlCmnTrnEvsQry.setUpdateEvent(cmpVal, lngVal, usrVal, oTrnEvnS);
		
		
		return new ResponseEntity<>(HttpStatus.OK);
    }


}
