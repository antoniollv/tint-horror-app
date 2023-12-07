package com.mapfre.tron.api.cmn.sr;

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
import lombok.extern.slf4j.Slf4j;

/**
 * The ZoneFourSubCode controller implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 31 Mar 2022 - 11:25:10
 *
 */
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ZoneFourSubcodeController implements ZoneFourSubcodeApi {

    /** The GrsZonSbdQry service.*/
    @Autowired
    protected IBlGrsZonSbdQry iBlGrsZonSbdQry;

    /**
     * Query Zone Four Subcode.
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country
     * @param sttVal    -> State Value
     * @param pvcVal    -> Province Value
     * @param twnVal    -> Town Value
     * @param ditVal    -> District Value
     * @param reaGrsDit -> Real District
     * @return          -> It will return the zone four subcode (District)
     */
    @Override
    public ResponseEntity<OGrsZnfS> getZoneFourSubcode(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "Country", required = true) @Valid @RequestParam(value = "cnyVal", required = true) final String cnyVal,
            @NotNull @ApiParam(value = "State Value", required = true) @Valid @RequestParam(value = "sttVal", required = true) final Integer sttVal,
            @NotNull @ApiParam(value = "Province Value", required = true) @Valid @RequestParam(value = "pvcVal", required = true) final Integer pvcVal,
            @NotNull @ApiParam(value = "Town Value", required = true) @Valid @RequestParam(value = "twnVal", required = true) final Integer twnVal,
            @NotNull @ApiParam(value = "District Value", required = true) @Valid @RequestParam(value = "ditVal", required = true) final String ditVal,
            @ApiParam(value = "real District") @Valid @RequestParam(value = "reaGrsDit", required = false) String reaGrsDit) {

        log.info("GetZoneFourSubcode rest operation had been called");

        OGrsZnfS lvOGrsZnfS = iBlGrsZonSbdQry.getZoneFourSubcode(cmpVal, usrVal, lngVal, cnyVal, sttVal, pvcVal, twnVal,
                ditVal, reaGrsDit);

        return new ResponseEntity<>(lvOGrsZnfS, HttpStatus.OK);
    }

}
