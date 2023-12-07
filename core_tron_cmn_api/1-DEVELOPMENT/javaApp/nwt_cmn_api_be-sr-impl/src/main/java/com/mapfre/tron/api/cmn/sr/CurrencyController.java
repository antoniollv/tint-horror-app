package com.mapfre.tron.api.cmn.sr;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;
import com.mapfre.tron.api.crn.crn.bl.IBlCrnCrnQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The currency controller.
 *
 * @author Cristian Saball
 * @version 16 sept. 2021 13:57:46
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class CurrencyController implements CurrencyApi {

    @Autowired
    protected IBlCrnCrnQry iBlCrnCrnQry;

    /**
     * Query currency. It will return the currency
     *
     * @author Cristian Saball
     *
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> company
     * @param sdrCrnVal -> standard currency value
     * @return OCrnCrnS -> the currency
     */
    @Override
    public ResponseEntity<OCrnCrnS> getCurrency(
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "company", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "standard currency value", required = true) @PathVariable("sdrCrnVal") final String sdrCrnVal) {

        log.info("The getCurrency rest operation had been called...");

        OCrnCrnS lvOCrnCrnS = iBlCrnCrnQry.getCurrency(usrVal, lngVal, cmpVal, sdrCrnVal);

        return new ResponseEntity<>(lvOCrnCrnS, HttpStatus.OK);
    }

}
