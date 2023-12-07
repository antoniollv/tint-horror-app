package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

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
 * The bank account types controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 3 sept. 2020 13:16:10
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class BankAccountTypesController implements BankAccountTypesApi {

    /** The CmnTypQry service interface.*/
    @Autowired
    protected IBLCmnTypQry iBLCmnTypQry;

    /**
     * Query bank account types. It will return the types of bank accounts.
     *
     * @author pvraul1
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return -> The types of bank accounts
     */
    @Override
    public ResponseEntity<List<OCmnTypP>> getBankAccountTypes(
	    @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @ApiParam(value = "Company code") @Valid @RequestParam(value = "cmpVal", required = false) Integer cmpVal) {

        log.info("The getBankAccountTypes rest operation had been called...");

        try {
            OCmnTypCPT lvOCmnTypCPT = iBLCmnTypQry.prc(CThp.CUC_TYP_RQR, CPrt.GNC_LOB_VAL, lngVal, CTrn.GET_NAM_ALL, cmpVal);

            if (lvOCmnTypCPT != null && lvOCmnTypCPT.getOCmnTypPT() != null && !lvOCmnTypCPT.getOCmnTypPT().isEmpty()) {
                return new ResponseEntity<>(lvOCmnTypCPT.getOCmnTypPT(), HttpStatus.OK);

            } else {
                String message = String.format(
                        "TST-20001: Entity with usrVal: %s and lngVal: %s not found", usrVal, lngVal);
                throw new NwtException(message);
            }

        } catch (NwtException nwte) {
            throw nwte;

        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);
        }

    }

}
