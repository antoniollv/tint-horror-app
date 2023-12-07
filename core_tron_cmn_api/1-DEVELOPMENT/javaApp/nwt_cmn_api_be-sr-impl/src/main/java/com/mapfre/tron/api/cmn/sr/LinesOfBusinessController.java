package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobCPT;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobP;
import com.mapfre.tron.api.prt.lob.bl.IBlPrtLobQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The lines of business controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 3 sept. 2020 8:21:19
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class LinesOfBusinessController implements LinesOfBusinessApi {

    /** The PrtLobQry business interface.*/
    @Autowired
    protected IBlPrtLobQry iBLPrtLobQry;

    /**
     * Query lines of business. It will return the lines of business.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return       -> The lines of business
     */
    @Override
    public ResponseEntity<List<OPrtLobP>> getLinesOfBusiness(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal) {

        log.info("The getLinesOfBusiness rest operation had been called...");
        
            OPrtLobCPT lvOPrtLobCPT = iBLPrtLobQry.isuTbl(cmpVal, usrVal, lngVal);
            
            return new ResponseEntity<>(lvOPrtLobCPT.getOPrtLobPT(), HttpStatus.OK);

    }

}
