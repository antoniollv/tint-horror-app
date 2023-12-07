package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.tron.api.grs.zon.bl.IBlGrsZonSbdQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

/**
 * The ZoneFourSubcodeList rest controller implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 31 Mar 2022 - 16:52:27
 *
 */
@Controller
@Api(tags={ "Common",})
public class ZoneFourSubcodeListController implements ZoneFourSubcodeListApi {

    /** The GrsZonSbdQry service.*/
    @Autowired
    protected IBlGrsZonSbdQry iBlGrsZonSbdQry;

    /**
     * Query Zone Four Subcode List.
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country
     * @param sttVal    -> State Value
     * @param pvcVal    -> Province Value
     * @param twnVal    -> Town Value
     * @param reaGrsDit -> Real District
     * @return          -> It will return the list of zone four subcode list (Districts)
     */
    @Override
    public ResponseEntity<List<OGrsZnfS>> getZoneFourSubcodeList(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
            @NotNull @ApiParam(value = "Country", required = true) @Valid @RequestParam(value = "cnyVal", required = true) String cnyVal,
            @NotNull @ApiParam(value = "State Value", required = true) @Valid @RequestParam(value = "sttVal", required = true) String sttVal,
            @NotNull @ApiParam(value = "Province Value", required = true) @Valid @RequestParam(value = "pvcVal", required = true) Integer pvcVal,
            @NotNull @ApiParam(value = "Town Value", required = true) @Valid @RequestParam(value = "twnVal", required = true) Integer twnVal,
            @ApiParam(value = "real District") @Valid @RequestParam(value = "reaGrsDit", required = false) String reaGrsDit) {

        log.info("GetZoneFourSubcodeList rest operation had been called");

        List<OGrsZnfS> lvOGrsZnfST = iBlGrsZonSbdQry.getZoneFourSubcodeList(cmpVal, usrVal, lngVal, cnyVal, sttVal,
                pvcVal, twnVal, reaGrsDit);

        return new ResponseEntity<>(lvOGrsZnfST, HttpStatus.OK);
    }

}
