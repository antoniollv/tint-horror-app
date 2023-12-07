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

import com.mapfre.nwt.trn.cmu.snl.bo.OCmuSnlS;
import com.mapfre.tron.api.lvl.cnt.bl.IBlScnLvlCnt;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * The second leves controller implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 20 Oct 2021 - 14:11:29
 *
 */
@Slf4j
@Controller
@Api(tags={ "Common",})
public class SecondLevelsController implements SecondLevelsApi {

    @Autowired
    protected IBlScnLvlCnt iBlScnLvlCnt;

    /**
     * Query Second Level List.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param frsLvlVal -> First Level
     * @return          -> The second levels list
     */
    @Override
    public ResponseEntity<List<OCmuSnlS>> getSecondLevels(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @NotNull @ApiParam(value = "First Level", required = true) @Valid @RequestParam(value = "frsLvlVal", required = true) final BigDecimal frsLvlVal) {

        log.info("The getSecondLevels rest operation had been called...");

        List<OCmuSnlS> lvOCmuSnlST = iBlScnLvlCnt.scnLvlQry(cmpVal, lngVal, usrVal, frsLvlVal);

        return new ResponseEntity<>(lvOCmuSnlST, HttpStatus.OK);
    }

}
