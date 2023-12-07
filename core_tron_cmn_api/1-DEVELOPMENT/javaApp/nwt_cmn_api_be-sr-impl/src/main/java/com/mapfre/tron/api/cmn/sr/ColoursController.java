package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.trn.cmn.col.bo.OCmnColCPT;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColP;
import com.mapfre.tron.api.cmn.col.bl.IBlCmnColQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The colours controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 3 sept. 2020 12:43:06
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ColoursController implements ColoursApi {

    /** The CmnColQry business interface.*/
    @Autowired
    protected IBlCmnColQry iBLCmnColQry;

    /**
     * Query lcolours. It will return the colours.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return       -> The colours
     */
    @Override
    public ResponseEntity<List<OCmnColP>> getColours(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal) {

        log.info("The getColours rest operation had been called...");
        
        OCmnColCPT lvOCmnColPT = iBLCmnColQry.set(cmpVal, usrVal, lngVal);
        
        return new ResponseEntity<>(lvOCmnColPT.getOCmnColPT(), HttpStatus.OK);
        
    }

}
