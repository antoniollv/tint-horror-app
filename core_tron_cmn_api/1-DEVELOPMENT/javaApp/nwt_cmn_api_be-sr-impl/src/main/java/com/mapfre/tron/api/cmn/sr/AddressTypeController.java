package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.exception.NwtException;
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
 * The address type controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 2 sept. 2020 12:29:51
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common", })
public class AddressTypeController implements AddressTypeApi {

    /** The iBLCmnTypQry service interface. */
    @Autowired
    protected IBLCmnTypQry iBLCmnTypQry;

    /**
     * Query address type. It will return the type of address.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param valVal -> Value
     * @return       -> The type of address
     */
    @Override
    public ResponseEntity<OCmnTypS> getAddressType(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Value", required = true) @RequestHeader(value = "valVal", required = true) final String valVal) {

        log.info("The getAddressType rest operation had been called...");

        try {
            String getNamTypVal = CTrn.GET_NAM_ALL;
            BigDecimal lobVal = CPrt.GNC_LOB_VAL;
            String fldNam = CThp.ADR_TYP_RQR;

            OCmnTypP lvOCmnTlvOCmnTypP = iBLCmnTypQry.fld(fldNam, lobVal, lngVal, getNamTypVal, valVal, cmpVal);

            return new ResponseEntity<>(lvOCmnTlvOCmnTypP.getOCmnTypS(), HttpStatus.OK);

        } catch (EmptyResultDataAccessException erdae) {
            String message = String.format(
                    "TST-20001: Entity with cmpVal: %s, usrVal: %s, lngVal: %s and valVal: %s not found", cmpVal, usrVal, lngVal, valVal);

            throw new NwtException(message);

        } catch (NwtException nwte) {
            throw nwte;

        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);
        }

    }

}
