package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapfre.nwt.prt.c.CPrt;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.tron.api.cmn.typ.bl.IBLCmnTypQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for types services.
 * 
 * @author Javier Sangil
 * @since 1.8
 * @version 30 sep 2021
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Common",})
public class TypeController implements TypeApi {

    @Autowired
    protected IBLCmnTypQry iBLCmnTypQry;

    /**
     * Query Types
     * 
     * @author Javier Sangil
     * 
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param fldNam -> Field code
     * @param itcVal -> Intermediary Action Type
     * @param lobVal -> Line of Business
     * @param cmpVal -> Company code
     * @return OCmnTypP -> Type
     */
    
    @Override
    public ResponseEntity<OCmnTypS> getType(
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "Field code", required = true) @Valid @RequestParam(value = "fldNam", required = true) String fldNam,
	    @ApiParam(value = "Intermediary Action Type", required = true) @PathVariable("itcVal") String itcVal,
	    @ApiParam(value = "Line of Business") @Valid @RequestParam(value = "lobVal", required = false) Integer lobVal,
	    @ApiParam(value = "Company code") @Valid @RequestParam(value = "cmpVal", required = false) Integer cmpVal) {

	log.info("The getTypes rest operation had been called...");

	BigDecimal pmLobVal = (lobVal != null) ? BigDecimal.valueOf(lobVal) : CPrt.GNC_LOB_VAL;

	OCmnTypS oCmnTypS = iBLCmnTypQry.getType(usrVal, lngVal, fldNam, itcVal, pmLobVal, cmpVal);

	return new ResponseEntity<>(oCmnTypS, HttpStatus.OK);

    }
}
