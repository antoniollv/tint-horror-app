package com.mapfre.tron.api.cmn.sr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntP;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;
import com.mapfre.tron.api.grs.znt.bl.IBlGrsZntQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The zone two controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 3 sept. 2020 12:54:59
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ZoneTwoController implements ZoneTwoApi {

    /** The GrsZtnQry business interface.*/
    @Autowired
    protected IBlGrsZntQry iBLGrsZntQry;

    /**
     * Query zone two. It will return the information of a zone two.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param sttVal -> State
     * @return -> The information of zone two
     */
    @Override
    public ResponseEntity<OGrsZntS> getZoneTwo(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
            @ApiParam(value = "Country", required = true) @RequestHeader(value = "cnyVal", required = true) String cnyVal,
            @ApiParam(value = "State", required = true) @RequestHeader(value = "sttVal", required = true) Integer sttVal) {

        log.info("The getZoneTwo rest operation had been called...");

            OGrsZntP lvOGrsZntP = iBLGrsZntQry.cnyStt(cmpVal, usrVal, lngVal, cnyVal, sttVal);
            
            return new ResponseEntity<>(lvOGrsZntP.getOGrsZntS(), HttpStatus.OK);
  
    }

}
