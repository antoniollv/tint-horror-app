package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.cmn.mvd.bo.OCmnMvdS;
import com.mapfre.tron.api.cmn.mvd.bl.IBlCmnMvdQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * The MovementDefinition rest controller implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Mar 2023 - 17:20:21
 *
 */
@Slf4j
@Controller
@Api(tags={ "Movement Definition",})
public class MovementDefinitionController implements MovementDefinitionApi {

    /** The CmnMvd query service.*/
    @Autowired
    protected IBlCmnMvdQry iBlCmnMvdQry;

    /**
     * Query movement definition.
     *
     * @param lngVal -> Language code
     * @param usrVal -> User code
     * @param cmpVal -> Company code
     * @param mvmIdn -> Movement Identification
     * @param vldDat -> Valid date
     * @return       -> It will return the defintion of movements.
     */
    @Override
    public ResponseEntity<List<OCmnMvdS>> getMovementDefinition(
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @NotNull @ApiParam(value = "Movement Identification", required = true) @Valid @RequestParam(value = "mvmIdn", required = true) final List<String> mvmIdn,
            @ApiParam(value = "Valid date") @Valid @RequestParam(value = "vldDat", required = false) final Long vldDat) {

        if (log.isInfoEnabled()) {
            log.info("getMovementDefinition rest operation had been called...");
        }

        List<OCmnMvdS> lvOCmnMvdST = iBlCmnMvdQry.getMovementDefinition(lngVal, usrVal, cmpVal, mvmIdn, vldDat);

        return new ResponseEntity<>(lvOCmnMvdST, HttpStatus.OK);
    }

}
