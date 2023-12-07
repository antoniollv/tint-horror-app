package com.mapfre.tron.api.cmn.sr;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;
import com.mapfre.nwt.trn.prt.sec.bo.OPrtSecS;
import com.mapfre.tron.api.prt.sec.bl.IBlPrtSec;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for FirstLevelDistributionChannel.
 * 
 * @author Cristian Saball
 * @version 17/09/2021
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "SubSector list by sector",})
public class SectorController implements SectorApi {
    
    @Autowired
    protected IBlPrtSec iBlPrtSec;
    
    /**
     * Query sector. It will return the sector
     *
     * @author Cristian Saball
     *
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param secVal -> Sector
     * @return OPrtSecS -> the sector
     */
    @Override
    public ResponseEntity<OPrtSecS> getsector(
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Sector", required = true) @PathVariable("secVal") String secVal) {

	log.info("The getsector rest operation had been called...");

	OPrtSecS lvOPrtSecS = iBlPrtSec.getSecQry(cmpVal, lngVal, usrVal, secVal);

	return new ResponseEntity<>(lvOPrtSecS, HttpStatus.OK);

    }
    
    
    /**
     * Query sector. It will return the sector
     *
     * @author Amin Joudi
     *
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
	 * @param secVal 	-> Sector
	 * @param sbsVal 	-> SubSector value
     * @return OPrtSecS -> the sector
     */
    @Override
	public ResponseEntity<OPrtSbsS> getSubSector(
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
			@ApiParam(value = "Sector value", required = true) @PathVariable("secVal") Integer secVal,
			@ApiParam(value = "subSector value", required = true) @PathVariable("sbsVal") Integer sbsVal,
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal) {
    	log.info("The getsector rest operation had been called...");

    	OPrtSbsS lvOPrtSbsS = iBlPrtSec.getSubSector(cmpVal, secVal, sbsVal, lngVal, usrVal);

    	return new ResponseEntity<>(lvOPrtSbsS, HttpStatus.OK);
    }


    
}
