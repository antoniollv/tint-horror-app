package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotCPT;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotP;
import com.mapfre.tron.api.grs.zot.bl.IBlGrsZotQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The zone three list .
 *
 * @author pvraul1
 * @since 1.8
 * @version 2 sept. 2020 13:01:17
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ZoneThreeListController implements ZoneThreeListApi {

    /** The BrsZotQry business interface.*/
    @Autowired
    protected IBlGrsZotQry iBLGrsZotQry;

    /**
     * Query zone three list. It will return the list of zone three.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param sttVal -> State Value
     * @return       -> The list of zone three
     */
    @Override
    public ResponseEntity<List<OGrsZotP>> getZoneThreeList(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Country", required = true) @RequestHeader(value = "cnyVal", required = true) final String cnyVal,
            @ApiParam(value = "State Value", required = true) @RequestHeader(value = "sttVal", required = true) final Integer sttVal) {

        log.info("The getZoneThreeList rest operation had been called...");

            OGrsZotCPT lvOGrsZnfCPT = iBLGrsZotQry.zonZntSet(cmpVal, usrVal, lngVal, cnyVal, sttVal);

            return new ResponseEntity<>(lvOGrsZnfCPT.getOGrsZotPT(), HttpStatus.OK);
            
    }

}
