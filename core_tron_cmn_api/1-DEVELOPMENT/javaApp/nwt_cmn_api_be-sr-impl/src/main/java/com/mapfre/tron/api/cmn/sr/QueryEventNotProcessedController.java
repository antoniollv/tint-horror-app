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

import com.mapfre.nwt.trn.trn.evn.bo.OTrnEvnS;
import com.mapfre.tron.api.trn.evn.bl.IBlTrnEvnQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The QueryEventNotProcessed business logic service interface.
 *
 * @author Cristian Saball
 * @since 1.8
 * @version 31 ene. 2021 13:05:08
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Event",})
public class QueryEventNotProcessedController implements QueryEventNotProcessedApi {
    
    /** The iBlTrnEvnQry service interface. */
    @Autowired
    IBlTrnEvnQry iBlTrnEvnQry;
    
    
    /**
     * Query message list for event not processed.
     *
     * @author Cristian Saball
     *
     * @param cmpVal 	-> Company code
     * @param usrVal 	-> User code
     * @param lngVal 	-> Language code
     * @param qryDat 	-> Query Date
     * @param evnIdnVal -> Event Identifier
     * @return       	-> The message for event
     */
    @Override
    public ResponseEntity<List<OTrnEvnS>> getQueryEventNotProcessed(
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @NotNull @ApiParam(value = "Query Date", required = true) @Valid @RequestParam(value = "qryDat", required = true) Long qryDat,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Event Identifier") @Valid @RequestParam(value = "evnIdnVal", required = false) Integer evnIdnVal) {

    log.info("The getQueryEventNotProcessed rest operation had been called...");
    
    List<OTrnEvnS> lvOTrnEvnSList = iBlTrnEvnQry.getQueryEventNotProcessed(cmpVal, lngVal, usrVal, qryDat, evnIdnVal);
    //ISrTrnEvnQry
    return new ResponseEntity<>(lvOTrnEvnSList, HttpStatus.OK);

    }

}
