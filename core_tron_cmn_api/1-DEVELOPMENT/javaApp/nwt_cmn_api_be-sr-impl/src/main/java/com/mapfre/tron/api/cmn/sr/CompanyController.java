package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.tron.api.cmn.cmp.bl.IBlCmnCmpQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The Companies controller.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 sept. 2021
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class CompanyController implements CompanyApi {

    /** The CmnCmpQry business interface.*/
    @Autowired
    protected IBlCmnCmpQry iBLCmnCmpQry;

    /**
     * Query companies. It will return the companies.
     *
     * @author Javier Sangil
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cmpVal -> Company code
     * @return       -> The companies
     */
    @Override
    public ResponseEntity<OCmuCmpS> getCompany(
	    @ApiParam(value = "Company code", required = true) @PathVariable("cmpVal") Integer cmpVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal) {

        log.info("The getCompany rest operation had been called...");
        
        OCmuCmpS oCmuCmpS = iBLCmnCmpQry.getCompany(usrVal, lngVal, new BigDecimal(cmpVal));
        
        return new ResponseEntity<>(oCmuCmpS, HttpStatus.OK);
        
    }

}
