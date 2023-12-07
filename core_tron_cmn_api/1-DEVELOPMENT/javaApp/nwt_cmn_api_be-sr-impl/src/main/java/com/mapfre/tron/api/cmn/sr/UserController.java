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

import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;
import com.mapfre.tron.api.bo.UserConnected;
import com.mapfre.tron.api.cmn.aed.bl.IBlCmnAedQry;
import com.mapfre.tron.api.thp.usr.bl.IBlThpUsrQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest user controller implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 17 Oct 2023 - 09:41:04
 *
 */
@Data
@Slf4j
@Controller
@Api(tags = { "User info", })
public class UserController implements UserApi {

    @Autowired
    protected IBlThpUsrQry iBlThpUsrQry;

    /** The CmnAedQry service.*/
    @Autowired
    protected IBlCmnAedQry iBlCmnAedQry;

    @Override
    public ResponseEntity<UserConnected> getUserInfo(
            @ApiParam(value = "User", required = true) @PathVariable("usrVal") final String usrVal) {
        if (log.isInfoEnabled()) {
            log.info("The getAttributeValues rest operation called!");
        }

        return ResponseEntity.ok(iBlThpUsrQry.getUserConnected(usrVal));
    }

    /**
     * TRON-16952 Query data access by user code.
     *
     * @param cmpVal    -> Company Code
     * @param cmpUsrVal -> Company User Value
     * @param lngVal    -> Language Code
     * @param usrVal    -> User Value
     * @return          -> The data access
     */
    @Override
    public ResponseEntity<OCmnAedS> getDataAccessUser(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "Company User Value", required = true) @PathVariable("cmpUsrVal") final String cmpUsrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "User Value", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal) {

        if (log.isInfoEnabled()) {
            log.info("The getDataAccessUser rest operation called!");
        }

        OCmnAedS lvOCmnAedS = iBlCmnAedQry.getDataAccessUser(cmpVal, cmpUsrVal, lngVal, usrVal);

        return new ResponseEntity<>(lvOCmnAedS, HttpStatus.OK);
    }

}
