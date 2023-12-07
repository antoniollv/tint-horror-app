package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.crn.exr.bo.OCrnExrP;
import com.mapfre.nwt.trn.crn.exr.bo.OCrnExrS;
import com.mapfre.tron.api.crn.exr.bl.IBlCrnExrQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The exchange rate controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 4 sept. 2020 9:30:02
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ExchangeRateController implements ExchangeRateApi {

    /** The CrnExrQry business interface.*/
    @Autowired
    protected IBlCrnExrQry iBlCrnExrQry;


        // Query about application
        @Value("${default.app.cmpVal}")
        public BigDecimal cmpValDefault;

    /**
     * Query exchange rate. It will return the exchange rate in a currency for a date.
     *
     * @author pvraul1
     *
     * @param lngVal -> Language code
     * @param usrVal -> User code
     * @param crnVal -> Currency
     * @param exrDat -> Exchange rate date
     * @return       -> The exchange rate in a currency for a date
     */
    @Override
    public ResponseEntity<OCrnExrS> getExchangeRate(
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @NotNull @ApiParam(value = "Currency", required = true) @Valid @RequestParam(value = "crnVal", required = true) final Integer crnVal,
            @NotNull @ApiParam(value = "Exchange rate date", required = true) @Valid @RequestParam(value = "exrDat", required = true) final Long exrDat,
            @ApiParam(value = "Company", required = false) @Valid @RequestParam(value = "cmpVal", required = false) final Integer cmpVal) {

        log.info("The getExchangeRate rest operation had been called...");

        BigDecimal bdCmpVal = null; 

            if(cmpVal == null){
                bdCmpVal = cmpValDefault;
            }else{
                bdCmpVal = new BigDecimal(cmpVal);
            }
        
            OCrnExrP oCrnExrP  = iBlCrnExrQry.chnDat(lngVal, usrVal, crnVal, exrDat, bdCmpVal);

            return new ResponseEntity<>(oCrnExrP.getOCrnExrS(), HttpStatus.OK);


    }

}
