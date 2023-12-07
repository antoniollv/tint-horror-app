package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.prt.c.CPrt;
import com.mapfre.nwt.thp.c.CThp;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypP;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.tron.api.cmn.typ.bl.IBLCmnTypQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The gender type controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 2 sept. 2020 9:39:48
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class GenderTypeController implements GenderTypeApi {

    /** The iBLCmnTypQry service interface. */
    @Autowired
    protected IBLCmnTypQry iBLCmnTypQry;

    /**
     * Query gender type. It will return the type of gender.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param valVal -> Value
     * @return       -> The type of gender
     */
    @Override
    public ResponseEntity<OCmnTypS> getGenderType(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Value", required = true) @RequestHeader(value = "valVal", required = true) final String valVal) {

        log.info("The getGenderType rest operation had been called...");

            String getNamTypVal = CTrn.GET_NAM_ALL;
            BigDecimal lobVal = CPrt.GNC_LOB_VAL;
            String fldNam = CThp.SEX_THP;

            OCmnTypP lvOCmnTlvOCmnTypP = iBLCmnTypQry.fld(fldNam, lobVal, lngVal, getNamTypVal, valVal, cmpVal);

            return new ResponseEntity<>(lvOCmnTlvOCmnTypP.getOCmnTypS(), HttpStatus.OK);

    }

}
