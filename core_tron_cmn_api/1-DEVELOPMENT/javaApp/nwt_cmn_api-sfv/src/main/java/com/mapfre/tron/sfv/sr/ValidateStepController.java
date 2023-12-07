package com.mapfre.tron.sfv.sr;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.tron.sfv.bl.IBlSfvInuQry;
import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * The Validate Step API controller implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 5 May 2023 - 09:12:21
 *
 */
@Slf4j
@Controller
@Api(tags={ "Validate Step",})
public class ValidateStepController implements ValidateStepApi {

    @Autowired
    protected IBlSfvInuQry iBlSfvInu;

    /**
     * Validate information sended from frontal.
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User value
     * @param lngVal -> Language code
     * @param svfIn  -> Input structure data (agent, channel ...)
     * @return       -> Output structure data
     */
    @Override
    public ResponseEntity<SfvOut> postValidateStep(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final BigDecimal cmpVal,
            @NotNull @ApiParam(value = "User value", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Input structure data (agent, channel ...)", required = true) @Valid @RequestBody final SfvIn sfvIn) {

        if (log.isInfoEnabled()) {
            log.info("postValidateStep REST operation had been called...");
        }

        SfvOut lvSfvOut = iBlSfvInu.postValidateStep(cmpVal, usrVal, lngVal, sfvIn);
        if (lvSfvOut.getMessages() == null || lvSfvOut.getMessages().isEmpty()
                || (lvSfvOut.getMessages().get(0).getMsgTypVal() != null
                        && lvSfvOut.getMessages().get(0).getMsgTypVal() != 3)) {
            return new ResponseEntity<>(lvSfvOut, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(lvSfvOut, HttpStatus.BAD_REQUEST);
        }

    }

}
