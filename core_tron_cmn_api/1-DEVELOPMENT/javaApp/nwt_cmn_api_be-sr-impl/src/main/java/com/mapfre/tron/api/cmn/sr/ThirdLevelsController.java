package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.tron.api.cmu.thl.bl.IBlCmuThl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * The third levels controller.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Oct 2021 - 09:24:10
 *
 */
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ThirdLevelsController implements ThirdLevelsApi {

    @Autowired
    protected IBlCmuThl iBlCmuThl;

    /**
     * Query Third Level List.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param frsLvlVal -> First Level
     * @param scnLvlVal -> Second Level
     * @return          -> The third levels list
     */
    @Override
    public ResponseEntity<List<OCmuThlS>> getThirdLevels(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @NotNull @ApiParam(value = "First Level", required = true) @Valid @RequestParam(value = "frsLvlVal", required = true) final BigDecimal frsLvlVal,
            @NotNull @ApiParam(value = "Second Level", required = true) @Valid @RequestParam(value = "scnLvlVal", required = true) final BigDecimal scnLvlVal) {

        log.info("The getThirdLevels rest operation had been called...");

        List<OCmuThlS> lvOCmuThlST = iBlCmuThl.getThirdLevels(cmpVal, lngVal, usrVal, frsLvlVal, scnLvlVal);

        return new ResponseEntity<>(lvOCmuThlST, HttpStatus.OK);
    }

}
