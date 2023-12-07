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

import com.mapfre.nwt.trn.dsr.sdc.bo.ODsrSdcS;
import com.mapfre.tron.api.dsr.sdc.bl.IBlDsrSdcQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for FirstLevelDistributionChannel.
 * 
 * @author Javier Sangil
 * @version 12/08/2021
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Common",})
public class SecondLevelDistributionChannelByCmpFirstController implements SecondLevelDistributionChannelByCmpFirstApi {

    @Autowired
    protected IBlDsrSdcQry iBlDsrSdcQry;

    /**
     * Second Level Distribution Channel by company and first level
     *
     * @author Cristian S.
     * 
     * 
     * @param cmpVal       -> company code
     * @param frsDstHnlVal -> first Channel Distribution
     * @param usrVal       -> user value
     * @param lngVal       -> language value
     * 
     * @return List<ODsrSdcS>
     * 
     */
    @Override
    public ResponseEntity<List<ODsrSdcS>> getsecondLevelDistributionChannelByCmpFirst(
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @NotNull @ApiParam(value = "First Channel Distribution", required = true) @Valid @RequestParam(value = "frsDstHnlVal", required = true) String frsDstHnlVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal) {

	log.info("The getsecondLevelDistributionChannelByCmpFirst rest operation had been called...");

	List<ODsrSdcS> lvODsrSdcSList = iBlDsrSdcQry.tblByCmpFrsLvl(cmpVal, frsDstHnlVal, lngVal, usrVal);

	return new ResponseEntity<>(lvODsrSdcSList, HttpStatus.OK);

    }
    
    

}
