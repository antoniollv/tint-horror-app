package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.tron.api.cmn.typ.bl.IBLCmnTypQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The Collector Type controller.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 sept. 2021
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class CollectorTypeController implements CollectorTypeApi {

    /** The CmnCmpQry business interface.*/
    @Autowired
    protected IBLCmnTypQry iBLCmnTypQry;

    /**
     * Query companies. It will return The Collector Type.
     *
     * @author Javier Sangil
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cmpVal -> Company code
     * @param valVal -> value
     * @return OCmnTypS      -> The Collector Type
     */
    //@Override
    public ResponseEntity<OCmnTypS> getCollectorType(
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @ApiParam(value = "Value", required = true) @PathVariable("valVal") String valVal) {

        log.info("The getCollectorType rest operation had been called...");
        
        OCmnTypS oCmnTypS = iBLCmnTypQry.getCollectorType(usrVal, lngVal, new BigDecimal(cmpVal), valVal);
        
        return new ResponseEntity<>(oCmnTypS, HttpStatus.OK);
        
    }

}
