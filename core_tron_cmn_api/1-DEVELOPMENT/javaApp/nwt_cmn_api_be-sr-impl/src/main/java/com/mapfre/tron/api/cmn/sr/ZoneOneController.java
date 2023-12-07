package com.mapfre.tron.api.cmn.sr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;
import com.mapfre.tron.api.grs.zno.bl.IBlGrsZnoQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The zone one controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 3 sept. 2020 10:09:41
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ZoneOneController implements ZoneOneApi {

    /** The GrsZnoQry.*/
    @Autowired
    protected IBlGrsZnoQry iBLGrsZnoQry;

    /**
     * Query zone one. It will return the information of a zone one.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @return       -> The information of zone one
     */
    @Override
    public ResponseEntity<OGrsZnoS> getZoneOne(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Country", required = true) @RequestHeader(value = "cnyVal", required = true) final String cnyVal) {

        log.info("The getZoneOne rest operation had been called...");

            OGrsZnoS oGrsZnoS = iBLGrsZnoQry.cny(cmpVal, usrVal, lngVal, cnyVal);
            return new ResponseEntity<>(oGrsZnoS, HttpStatus.OK);

    }

}
