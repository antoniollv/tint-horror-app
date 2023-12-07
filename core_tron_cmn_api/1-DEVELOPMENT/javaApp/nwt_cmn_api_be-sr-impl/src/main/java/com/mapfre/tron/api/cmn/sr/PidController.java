package com.mapfre.tron.api.cmn.sr;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.tron.api.dfq.lod.bl.IBlPidDfqLod;
import com.mapfre.tron.api.pfc.lod.bl.IBlPidPfcLod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The PidController business logic service interface.
 *
 * @author magarafr
 * @since 1.8
 * @version 25 sept. 2020 13:05:08
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Life",})
public class PidController implements PidApi {
    
    /** The CteDefQry business interface.*/
    @Autowired
    protected IBlPidDfqLod iBlPidDfqLod;
    
    /** The PidPfcLod business interface.*/
    @Autowired
    protected IBlPidPfcLod iBlPidPfcLod;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResponseEntity<Void> postSaveDailyFundQuotation(
	    @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) String usrVal,
	    @NotNull @ApiParam(value = "Language code", required = true) @Valid @RequestParam(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal) {
	
	log.info("The postSaveDailyFundQuotation rest operation had been called...");

        try {
            
            iBlPidDfqLod.savBtc(lngVal, usrVal, cmpVal);

            return new ResponseEntity("Operation successfully done!", HttpStatus.CREATED);

        } catch (NwtException nwte) {
            throw nwte;

        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);
        }
    }

    /**
     * PidPfcLod : AMBT-87 - Save portfolio fund composition
     *
     * @author magarafr
     * @purpose Save portfolio fund composition. It will be mandatory send the
     *          parameters defined in the service and the service will response with
     *          the output object defined.
     * 
     * @param lngVal -> Idioma
     * @param usrVal -> usuario
     * @param cmpVal -> Compañia
     *
     * @return -> Registro de movimiento
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResponseEntity<Void> postSavePortfolioFundComposition(
	    @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) String usrVal,
	    @NotNull @ApiParam(value = "Language code", required = true) @Valid @RequestParam(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal) {
	
	log.info("The postSavePortfolioFundComposition rest operation had been called...");

        try {
            
            iBlPidPfcLod.savPffBtc(lngVal, usrVal, cmpVal);

            return new ResponseEntity("Operation successfully done!", HttpStatus.CREATED);

        } catch (NwtException nwte) {
            throw nwte;

        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);
        }
    }

}
