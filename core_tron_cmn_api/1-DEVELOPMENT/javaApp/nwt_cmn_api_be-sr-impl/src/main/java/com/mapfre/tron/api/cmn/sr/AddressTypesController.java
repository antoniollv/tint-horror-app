package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.prt.c.CPrt;
import com.mapfre.nwt.thp.c.CThp;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypCPT;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypP;
import com.mapfre.tron.api.cmn.typ.bl.IBLCmnTypQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The addressTypes controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 2 sept. 2020 12:44:03
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class AddressTypesController implements AddressTypesApi {

    /** The iBLCmnTypQry service interface. */
    @Autowired
    protected IBLCmnTypQry iBLCmnTypQry;

    /**
     * Query address types. It will return the types of address.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return       -> The types of address
     */
    @Override
    public ResponseEntity<List<OCmnTypP>> getAddressTypes(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal) {

        log.info("The getAddressTypes rest operation had been called...");

        try {
            String getNamTypVal = CTrn.GET_NAM_ALL;
            BigDecimal lobVal = CPrt.GNC_LOB_VAL;
            String fldNam = CThp.ADR_TYP_RQR;

            OCmnTypCPT lvOCmnTypCPT = iBLCmnTypQry.prc(fldNam, lobVal, lngVal, getNamTypVal, cmpVal);
            if (lvOCmnTypCPT != null && lvOCmnTypCPT.getOCmnTypPT() != null && !lvOCmnTypCPT.getOCmnTypPT().isEmpty()) {
                return new ResponseEntity<>(lvOCmnTypCPT.getOCmnTypPT(), HttpStatus.OK);

            } else {
                String message = String.format(
                        "Entity list with cmpVal: %s, usrVal: %s,  and lngVal: %s not found", cmpVal, usrVal, lngVal);
                throw new NwtException(message);
            }

        } catch (NwtException nwte) {
            throw nwte;

        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);
        }

    }

}
