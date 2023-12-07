package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
 * The rest controller for types services.
 * 
 * @author HRCARL1
 * @since 1.8
 * @version 25 jun 2020
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Common",})
public class TypesController implements TypesApi {
    
    @Autowired
    protected IBLCmnTypQry iBLCmnTypQry;

    /**
     * Query Types
     * @author HRCARL1
     * 
     * @param  usrVal         -> User code
     * @param  lngVal         -> Language code
     * @param  fldNam         -> Field code
     * @param  lobVal         -> Line of Business
     * @return List<OCmnTypP> -> Type List
     */
    @Override
    public ResponseEntity<List<OCmnTypP>> getTypes(
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "Field code", required = true) @Valid @RequestParam(value = "fldNam", required = true) String fldNam,
	    @ApiParam(value = "Line of Business") @Valid @RequestParam(value = "lobVal", required = false) Integer lobVal,
	    @ApiParam(value = "Company code") @Valid @RequestParam(value = "cmpVal", required = false) Integer cmpVal) {

        log.info("The getTypes rest operation had been called...");

        try {
            String getNamTypVal = CTrn.GET_NAM_ALL;
            BigDecimal pmLobVal = (lobVal != null) ? BigDecimal.valueOf(lobVal) : CPrt.GNC_LOB_VAL;

            OCmnTypCPT lvOCmnTypCPT = iBLCmnTypQry.prc(fldNam, pmLobVal, lngVal, getNamTypVal, cmpVal);

            if (lvOCmnTypCPT != null && !lvOCmnTypCPT.getOCmnTypPT().isEmpty()) {
                return new ResponseEntity<>(lvOCmnTypCPT.getOCmnTypPT(), HttpStatus.OK);
            } else {
                String message = String.format(
                        "TST-20001: Entity list NOT FOUND with lngVal : %s, fldNam: %s, usrVal: %s, lobVal: %s", lngVal,
                        fldNam, usrVal, lobVal);
                throw new NwtException(message);
            }

        } catch (NwtException nwte) {
            throw nwte;

        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);
        }

    }

}
