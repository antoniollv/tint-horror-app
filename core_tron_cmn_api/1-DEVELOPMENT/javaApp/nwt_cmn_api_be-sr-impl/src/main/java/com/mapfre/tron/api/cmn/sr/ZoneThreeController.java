package com.mapfre.tron.api.cmn.sr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;
import com.mapfre.tron.api.grs.zot.bl.IBlGrsZotQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The zoneThree controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 3 sept. 2020 11:42:44
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ZoneThreeController implements ZoneThreeApi {

    /**The GrsZotQry business interface.*/
    @Autowired
    protected IBlGrsZotQry iBLGrsZotQry;

    /**
     * Query zone three. It will return the information of a zone three.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param pvcVal -> Province value
     * @return       -> The information of zone three
     */
    @Override
    public ResponseEntity<OGrsZotS> getZoneThree(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Country", required = true) @RequestHeader(value = "cnyVal", required = true) final String cnyVal,
            @ApiParam(value = "Province Value", required = true) @RequestHeader(value = "pvcVal", required = true) final Integer pvcVal) {

        log.info("The getZoneThree rest operation had been called...");

            OGrsZotS lvOGrsZotS = iBLGrsZotQry.cnyPvc(cmpVal, usrVal, lngVal, cnyVal, pvcVal);
            return new ResponseEntity<>(lvOGrsZotS, HttpStatus.OK);

    }
    
}
