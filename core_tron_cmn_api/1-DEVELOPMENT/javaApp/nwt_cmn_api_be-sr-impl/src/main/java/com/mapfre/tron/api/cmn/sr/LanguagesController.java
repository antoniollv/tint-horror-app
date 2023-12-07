package com.mapfre.tron.api.cmn.sr;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngCPT;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngP;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.tron.api.cmn.lng.bl.IBlCmnLngQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The languages controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 4 sept. 2020 8:52:16
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class LanguagesController implements LanguagesApi {

    /** The CmnLngQry service interface. */
    @Autowired
    protected IBlCmnLngQry iBlCmnLngQry;

    /**
     * Query languages. It will return the types of languages.
     *
     * @author pvraul1
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return -> The types of languages
     */
    @Override
    public ResponseEntity<List<OCmnLngS>> getLanguages(
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal) {

	log.info("The getLanguages rest operation had been called...");

	OCmnLngCPT lvOCmnLngCPT = iBlCmnLngQry.getTbl(lngVal, usrVal);

	List<OCmnLngS> lvOCmnLngST = new ArrayList<>();

	for (OCmnLngP oCmnLngP : lvOCmnLngCPT.getOCmnLngPT()) {
	    lvOCmnLngST.add(oCmnLngP.getOCmnLngS());
	}

	return new ResponseEntity<>(lvOCmnLngST, HttpStatus.OK);
    }

}
