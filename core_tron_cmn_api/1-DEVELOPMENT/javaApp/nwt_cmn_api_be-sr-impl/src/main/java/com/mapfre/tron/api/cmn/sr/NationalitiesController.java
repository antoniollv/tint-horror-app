package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.prt.c.CPrt;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypCPT;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypP;
import com.mapfre.tron.api.cmn.typ.bl.IBLCmnTypQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The nationalities controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 4 sept. 2020 8:39:01
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class NationalitiesController implements NationalitiesApi {

    /** The CmnTypQry service interface.*/
    @Autowired
    protected IBLCmnTypQry iBLCmnTypQry;

    /**
     * Query nationalites. It will return the types of nationalities.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return -> The types of nationalities
     */
    @Override
    public ResponseEntity<List<OCmnTypP>> getNationalities(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal) {

        log.info("The getNationalities rest operation had been called...");

        try {
            String getNamTypVal = CTrn.GET_NAM_ALL;
            BigDecimal lobVal = CPrt.GNC_LOB_VAL;
            String fldNam = "TIP_NACIONALIDAD";

            OCmnTypCPT lvOCmnTypCPT = iBLCmnTypQry.prc(fldNam, lobVal, lngVal, getNamTypVal, cmpVal);
            if (lvOCmnTypCPT != null && lvOCmnTypCPT.getOCmnTypPT() != null && !lvOCmnTypCPT.getOCmnTypPT().isEmpty()) {
                return new ResponseEntity<>(lvOCmnTypCPT.getOCmnTypPT(), HttpStatus.OK);
            } else {
                String message = String.format(
                        "TST-20001: Entity with cmpVal: %s, usrVal: %s and lngVal: %s not found", cmpVal, usrVal, lngVal);
                throw new NwtException(message);
            }

        } catch (NwtException nwte) {
            throw nwte;

        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);
        }

    }

}
