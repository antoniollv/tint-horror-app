package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlP;
import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntP;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;
import com.mapfre.tron.api.cmu.thl.bl.IBlCmuThlCntQry;
import com.mapfre.tron.api.cmu.thl.bl.IBlCmuThlQry;
import com.mapfre.tron.api.thp.adr.bl.IBlThpAdrQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The third level controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 4 sept. 2020 10:22:51
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ThirdLevelController implements ThirdLevelApi {

    /** The CmuThlQry business interface. */
    @Autowired
    protected IBlCmuThlQry iBlCmuThlQry;

    /** The CmuThlCntQry business interface. */
    @Autowired
    protected IBlCmuThlCntQry iBlCmuThlCntQry;

    @Autowired
    protected IBlThpAdrQry iBlThpCntQry;

    /**
     * Query the third level. It will return the third level of the commercial
     * structure.
     *
     * @author pvraul1
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param thrLvlVal -> Third Level
     * @return -> The third level of the commercial structure
     */
    @Override
    public ResponseEntity<OCmuThlS> getThirdLevel(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Third Level", required = true) @PathVariable("thrLvlVal") final Integer thrLvlVal) {

        log.info("The getThirdLevel rest operation had been called...");

        OCmuThlP lvOCmuThlP = iBlCmuThlQry.get(cmpVal, lngVal, usrVal, thrLvlVal);

        return new ResponseEntity<>(lvOCmuThlP.getOCmuThlS(), HttpStatus.OK);

    }

    /**
     * Query Contact From Third Level. It will return the contact of the third level
     * of the commercial structure
     *
     * @author Cristian Saball
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param thrLvlVal -> Third Level
     * @return oThpCntS -> The contact of the third level of the commercial
     *         structure
     */
    @Override
    public ResponseEntity<OThpCntS> getContactFromThirdLevel(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
            @ApiParam(value = "Third Level", required = true) @PathVariable("thrLvlVal") Integer thrLvlVal) {

        log.info("The getContactFromThirdLevel rest operation had been called...");

        OThpCntP lvOThpCntP = iBlCmuThlCntQry.get(cmpVal, lngVal, usrVal, thrLvlVal);

        return new ResponseEntity<>(lvOThpCntP.getOThpCntS(), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<OThpAdrS> getAddresstFromThirdLevel(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
            @ApiParam(value = "Third Level", required = true) @PathVariable("thrLvlVal") Integer thrLvlVal) {
        log.info("The getAddresstFromThirdLevel rest operation had been called...");

        OThpAdrS lvOThpCntP = iBlThpCntQry.get(cmpVal, lngVal, usrVal, thrLvlVal);

        return new ResponseEntity<>(lvOThpCntP, HttpStatus.OK);

    }

    /**
     * Query the third level contact information.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param thrLvlVal -> Third Level
     * @return          -> It will return the contact of the third level of the commercial structure.
     */
    @Override
    public ResponseEntity<List<OThpCntS>> getContactFromThirdLevelv1(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Third Level", required = true) @PathVariable("thrLvlVal") final Integer thrLvlVal) {

        if (log.isInfoEnabled()) {
            log.info("The getContactFromThirdLevelv1 rest operation had been called...");
        }

        List<OThpCntS> lvOThpCntST = iBlCmuThlCntQry.getContactFromThirdLevelv1(cmpVal, lngVal, usrVal, thrLvlVal);

        return new ResponseEntity<>(lvOThpCntST, HttpStatus.OK);
    }

    /**
     * Query Address From Third Level 1.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param thrLvlVal -> Third Level
     * @return          -> It will return the address of the third level of the commercial structure.
     */
    @Override
    public ResponseEntity<List<OThpAdrS>> getAddresstFromThirdLevelv1(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final  String usrVal,
            @ApiParam(value = "Third Level", required = true) @PathVariable("thrLvlVal") final Integer thrLvlVal) {

        if (log.isInfoEnabled()) {
            log.info("The getAddresstFromThirdLevelv1 rest operation had been called...");
        }

        List<OThpAdrS> lvOThpAdrST = iBlCmuThlCntQry.getAddresstFromThirdLevelv1(cmpVal, lngVal, usrVal, thrLvlVal);

        return new ResponseEntity<>(lvOThpAdrST, HttpStatus.OK);
    }

}
