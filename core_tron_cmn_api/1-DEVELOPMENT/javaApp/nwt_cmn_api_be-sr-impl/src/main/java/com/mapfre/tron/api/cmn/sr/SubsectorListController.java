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

import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;
import com.mapfre.tron.api.prt.sbs.bl.IBlPrtSbsQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for SubsectorList.
 * 
 * @author Javier Sangil
 * @version 30/08/2021
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Common",})
public class SubsectorListController implements SubsectorListApi {
    
    @Autowired
    protected IBlPrtSbsQry iBlPrtSbsQry;
    
    /**
     * Query subsectors by company and sector value
     *
     * @author Cristian Saball
     * 
     * 
     * @param cmpVal -> company code
     * @param secVal -> Sector value
     * @param usrVal -> user value
     * @param lngVal -> language value
     * 
     * @return List<OPrtSbsS>
     * 
     */
    @Override
    public ResponseEntity<List<OPrtSbsS>> getsubsectorList(
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @NotNull @ApiParam(value = "Sector value", required = true) @Valid @RequestParam(value = "secVal", required = true) Integer secVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal) {

	log.info("The getsubsectorList rest operation had been called...");

	List<OPrtSbsS> oPrtSbsSList = iBlPrtSbsQry.sbsTbl(cmpVal, secVal, lngVal, usrVal);

	return new ResponseEntity<>(oPrtSbsSList, HttpStatus.OK);

    }

}
