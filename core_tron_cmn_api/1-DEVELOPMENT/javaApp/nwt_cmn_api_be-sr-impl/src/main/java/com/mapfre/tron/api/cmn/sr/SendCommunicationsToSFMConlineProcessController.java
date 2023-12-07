package com.mapfre.tron.api.cmn.sr;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.tron.api.cmn.sfmc.bl.IBlSFMCProcessService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * The send comunications to SFCMC online process controller implementation.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 4 jun. 2021 - 12:53:04
 *
 */
@Slf4j
@Controller
@Api(tags={ "Common",})
public class SendCommunicationsToSFMConlineProcessController implements SendCommunicationsToSFMConlineProcessApi {

    /** The SFMC Process service. */
    @Autowired
    protected IBlSFMCProcessService iBlSFMCProcessService;

    /**
     * Send communications to SFMC online process.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResponseEntity<Void> postSendToSFMConlineProcess(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @NotNull @ApiParam(value = "Language code", required = true) @Valid @RequestParam(value = "lngVal", required = true) final String lngVal) {

        log.info("The PostSendToSFMConlineProcess rest operation had been called");

        iBlSFMCProcessService.sendToSFMConlineProcess(cmpVal, lngVal);

        return new ResponseEntity("Operation succesfully done!", HttpStatus.CREATED);
    }

}
