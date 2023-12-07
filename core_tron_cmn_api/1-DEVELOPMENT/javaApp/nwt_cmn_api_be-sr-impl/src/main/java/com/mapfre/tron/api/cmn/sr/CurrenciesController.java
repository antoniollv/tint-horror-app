package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnP;
import com.mapfre.tron.api.crn.crn.bl.IBlCrnCrnQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The currencies controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 3 sept. 2020 13:57:46
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class CurrenciesController implements CurrenciesApi {

    /** The CrnCrnQry business interface.*/
    @Autowired
    protected IBlCrnCrnQry iBlCrnCrnQry;

    /**
     * Query currencies. It will return the list of currencies.
     *
     * @author pvraul1
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return       -> The list of currencies
     */
    @Override
    public ResponseEntity<List<OCrnCrnP>> getCurrencies(
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Company", required = false) @RequestParam(value = "cmpVal", required = false) final Integer cmpVal) {

        log.info("The getCurrencies rest operation had been called...");
        
        List<OCrnCrnP> lvOCrnCrnPT = iBlCrnCrnQry.tbl(lngVal, cmpVal);
        
        return new ResponseEntity<>(lvOCrnCrnPT, HttpStatus.OK);

    }

}
