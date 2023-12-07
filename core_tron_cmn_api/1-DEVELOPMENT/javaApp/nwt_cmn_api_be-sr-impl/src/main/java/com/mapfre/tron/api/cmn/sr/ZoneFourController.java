package com.mapfre.tron.api.cmn.sr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.tron.api.grs.znf.bl.IBlGrsZnfQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The zone four controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 3 sept. 2020 13:04:03
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ZoneFourController implements ZoneFourApi {

    /** The GrsZnfQry business interface.*/
    @Autowired
    protected IBlGrsZnfQry iBLGrsZnfQry;

    /**
     * Query zone four. It will return the information of a zone four.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param pvcVal -> Province value
     * @param twnVal -> Town Value
     * @return       -> The information of zone four
     */
    @Override
    public ResponseEntity<OGrsZnfS> getZoneFour(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Country", required = true) @RequestHeader(value = "cnyVal", required = true) final String cnyVal,
            @ApiParam(value = "Province Value", required = true) @RequestHeader(value = "pvcVal", required = true) final Integer pvcVal,
            @ApiParam(value = "Town Value", required = true) @RequestHeader(value = "twnVal", required = true) final Integer twnVal) {

        log.info("The getZoneFour rest operation had been called...");

            OGrsZnfS lvOGrsZnfS = iBLGrsZnfQry.cnyPvcTwn(cmpVal, usrVal, lngVal, cnyVal, pvcVal, twnVal);
            
            return new ResponseEntity<>(lvOGrsZnfS, HttpStatus.OK);

    }
}
