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

import com.mapfre.nwt.trn.cmu.fsl.bo.OCmuFslS;
import com.mapfre.tron.api.cmu.fsl.bl.IBlCmuFsl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * The first levels controller.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 Oct 2021 - 08:35:02
 *
 */
@Slf4j
@Controller
@Api(tags={ "Common",})
public class FirstLevelsController implements FirstLevelsApi {

    /** The Cmu first level service.*/
    @Autowired
    protected IBlCmuFsl iBlCmuFsl;

    /**
     * Query First Level List.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @return          -> The first level list
     */
    @Override
    public ResponseEntity<List<OCmuFslS>> getFirstLevels(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal) {

        log.info("The getFirstLevels rest operation had been called...");

        List<OCmuFslS> lvOCmuFslST = iBlCmuFsl.fstLvlQry(cmpVal, lngVal, usrVal);

        return new ResponseEntity<>(lvOCmuFslST, HttpStatus.OK);
    }

}
