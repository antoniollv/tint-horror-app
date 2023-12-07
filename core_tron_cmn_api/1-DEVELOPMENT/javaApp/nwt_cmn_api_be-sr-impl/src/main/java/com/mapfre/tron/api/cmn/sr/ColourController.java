package com.mapfre.tron.api.cmn.sr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.trn.cmn.col.bo.OCmnColP;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColS;
import com.mapfre.tron.api.cmn.col.bl.IBlCmnColQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The colour controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 3 sept. 2020 12:34:16
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ColourController implements ColourApi {
    
    @Autowired
    protected IBlCmnColQry iBlCmnColQry;

    /**
     * Query colour. It will return the information of a colour.
     * 
     * @param cmpVal -> Company code*
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param colVal -> Colour
     * @return       -> The information of a colour
     */
    @Override
    public ResponseEntity<OCmnColS> getColour(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Colour", required = true) @RequestHeader(value = "colVal", required = true) final Integer colVal) {

        log.info("The getColour rest operation had been called...");

            OCmnColP lvOCmnColP = iBlCmnColQry.col(cmpVal, usrVal, lngVal, colVal);
            
            return new ResponseEntity<>(lvOCmnColP.getOCmnColS(), HttpStatus.OK);

    }

}
