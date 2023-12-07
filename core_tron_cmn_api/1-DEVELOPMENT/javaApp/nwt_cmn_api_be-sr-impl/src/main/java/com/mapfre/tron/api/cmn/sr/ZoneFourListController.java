package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfCPT;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfP;
import com.mapfre.tron.api.grs.znf.bl.IBlGrsZnfQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The zone four list controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 2 sept. 2020 13:26:40
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ZoneFourListController implements ZoneFourListApi {

    /** The GrsZnfQry business interface. */
    @Autowired
    protected IBlGrsZnfQry iBLGrsZnfQry;

    /**
     * Query zone four list. It will return the list of zone four.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param pvcVal -> Province Value
     * @return -> The list of zone four
     */
    @Override
    public ResponseEntity<List<OGrsZnfP>> getZoneFourList(
	    @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
	    @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
	    @ApiParam(value = "Country", required = true) @RequestHeader(value = "cnyVal", required = true) final String cnyVal,
	    @ApiParam(value = "Province Value", required = true) @RequestHeader(value = "pvcVal", required = true) final Integer pvcVal) {

	log.info("The getZoneFourList rest operation had been called...");

	OGrsZnfCPT lvOGrsZnfCPT = iBLGrsZnfQry.tbl(cmpVal, usrVal, lngVal, cnyVal, pvcVal);

	return new ResponseEntity<>(lvOGrsZnfCPT.getOGrsZnfPT(), HttpStatus.OK);

    }

}
