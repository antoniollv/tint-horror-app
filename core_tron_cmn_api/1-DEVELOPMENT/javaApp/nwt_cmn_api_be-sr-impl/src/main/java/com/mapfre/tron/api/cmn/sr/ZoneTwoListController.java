package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntCPT;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntP;
import com.mapfre.tron.api.grs.znt.bl.IBlGrsZntQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The zone two list controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 2 sept. 2020 14:11:54
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ZoneTwoListController implements ZoneTwoListApi {

    /** The GrsZntQry business interface.*/
    @Autowired
    protected IBlGrsZntQry iBLGrsZntQry;

    /**
     * Query zone two list. It will return the list of zone two.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @return       -> The list of zone two
     */
    @Override
    public ResponseEntity<List<OGrsZntP>> getZoneTwoList(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Country", required = true) @RequestHeader(value = "cnyVal", required = true) final String cnyVal) {

        log.info("The getZoneTwoList rest operation had been called...");
       
        OGrsZntCPT lvOGrsZntCPT = iBLGrsZntQry.set(cmpVal, usrVal, lngVal, cnyVal);
        
        return new ResponseEntity<>(lvOGrsZntCPT.getOGrsZntPT(), HttpStatus.OK);
     
    }

}
