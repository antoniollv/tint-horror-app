package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoCPT;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoP;
import com.mapfre.tron.api.grs.zno.bl.IBlGrsZnoQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The zone one list controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 2 sept. 2020 14:25:23
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ZoneOneListConstroller implements ZoneOneListApi {

    /** The GrsZnoQry business interface.*/
    @Autowired
    protected IBlGrsZnoQry iBLGrsZnoQry;

    /**
     * Query zone one list. It will return the list of zone one.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return       -> The list of zone one
     */
    @Override
    public ResponseEntity<List<OGrsZnoP>> getZoneOneList(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal) {

        log.info("The getZoneOneList rest operation had been called...");
        
        OGrsZnoCPT lvOGrsZnoCPT = iBLGrsZnoQry.set(cmpVal, usrVal, lngVal);
        
        return new ResponseEntity<>(lvOGrsZnoCPT.getOGrsZnoPT(), HttpStatus.OK);

    }

}
