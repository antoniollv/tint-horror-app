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

import com.mapfre.nwt.trn.cmn.mvr.bo.OCmnMvrS;
import com.mapfre.tron.api.cmn.mvr.bl.IBlCmnMvrQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Movements Record Api controller.
 *
 * @author ibermatica - Borja Ruiz
 * @since 1.8
 * @version 08 Agost. 2020 12:29:51
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Movement Record",})
public class MovementsRecordApiController implements  MovementsRecordApi {
    /** The iBlCmnMvrCrt service interface. */
    @Autowired
    protected IBlCmnMvrQry iBlCmnMvrQry;
    /**
     * Query movements record by third party
     * 
     * @author Javier Sangil
     *  
     * @param lngVal -> Language code
     * @param usrVal -> User code
     * @param cmpVal --> Company code 
     * @param thpDcmTypVal -> Document type
     * @param thpDcmVal -> document
     * @param thpAcvVal -> Activity 
     * @param mvmIdn -> Third Channel Distribution
     * @param qryDat -> Initial Date
     * @param mvmSttTypVal -> Movement state type
     * @param mvmPbl -> Public Movement
     * @param stsTypVal -> Status Type Value
     * @param prcTypVal -> Process Type value
     * @return -> List<OCmnMvrS>
     */
    @Override
    public ResponseEntity<List<OCmnMvrS>> getMovementRecordList(
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @NotNull @ApiParam(value = "Document type", required = true) @Valid @RequestParam(value = "thpDcmTypVal", required = true) String thpDcmTypVal,
	    @NotNull @ApiParam(value = "document", required = true) @Valid @RequestParam(value = "thpDcmVal", required = true) String thpDcmVal,
	    @NotNull @ApiParam(value = "Activity", required = true) @Valid @RequestParam(value = "thpAcvVal", required = true) Integer thpAcvVal,
	    @NotNull @ApiParam(value = "Movement Identification", required = true) @Valid @RequestParam(value = "mvmIdn", required = true) List<String> mvmIdn,
	    @ApiParam(value = "Query Date") @Valid @RequestParam(value = "qryDat", required = false) Long qryDat,
	    @ApiParam(value = "Movement state type") @Valid @RequestParam(value = "mvmSttTypVal", required = false) String mvmSttTypVal,
	    @ApiParam(value = "Public Movement") @Valid @RequestParam(value = "mvmPbl", required = false) String mvmPbl,
	    @ApiParam(value = "Status Type Value") @Valid @RequestParam(value = "stsTypVal", required = false) List<String> stsTypVal,
	    @ApiParam(value = "Process Type value") @Valid @RequestParam(value = "prcTypVal", required = false) String prcTypVal) {
            
	log.info("The getMovementRecordList rest operation had been called...");

	List<OCmnMvrS> oCmnMvrS = iBlCmnMvrQry.dcmTbl(lngVal, usrVal, cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal,
		mvmIdn, qryDat, mvmSttTypVal, mvmPbl, stsTypVal, prcTypVal);
	
	return new ResponseEntity<>(oCmnMvrS, HttpStatus.OK);

  }

}
