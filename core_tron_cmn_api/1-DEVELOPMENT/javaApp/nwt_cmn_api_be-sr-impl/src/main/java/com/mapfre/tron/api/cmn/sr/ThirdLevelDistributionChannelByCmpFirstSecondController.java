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

import com.mapfre.nwt.trn.dsr.hdc.bo.ODsrHdcS;
import com.mapfre.tron.api.dsr.hdc.bl.IBlDsrHdcQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for ThirdLevelDistributionChannelByCmpFirstSecond.
 * 
 * @author Cristian Saball
 * @version 24/08/2021
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Common",})
public class ThirdLevelDistributionChannelByCmpFirstSecondController implements ThirdLevelDistributionChannelByCmpFirstSecondApi {
    
    @Autowired
    protected IBlDsrHdcQry iBlDsrHdcQry;

    /**
     * Query Third Level Distribution Channel by company, first level and second
     * level.
     *
     * @author Cristian Saball
     * 
     * 
     * 
     * @param cmpVal       -> company code
     * @param frsDstHnlVal -> First Channel Distribution
     * @param scnDstHnlVal -> Second Channel Distribution
     * @param usrVal       -> user value
     * @param lngVal       -> language value
     * 
     * @return List<ODsrHdcS>
     * 
     */
    @Override
    public ResponseEntity<List<ODsrHdcS>> getthirdLevelDistributionChannelByCmpFirstSecond(
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @NotNull @ApiParam(value = "First Channel Distribution", required = true) @Valid @RequestParam(value = "frsDstHnlVal", required = true) String frsDstHnlVal,
	    @NotNull @ApiParam(value = "Second Channel Distribution", required = true) @Valid @RequestParam(value = "scnDstHnlVal", required = true) String scnDstHnlVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal) {

	log.info("The getthirdLevelDistributionChannelByCmpFirstSecond rest operation had been called...");

	List<ODsrHdcS> oDsrHdcSList = iBlDsrHdcQry.tblByCmpFrsScnLvl(cmpVal, frsDstHnlVal, scnDstHnlVal, usrVal, lngVal);

	return new ResponseEntity<>(oDsrHdcSList, HttpStatus.OK);

    }

    
}
