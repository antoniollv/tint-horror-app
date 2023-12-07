package com.mapfre.tron.api.cmn.sr;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.tron.api.cmn.lng.bl.IBlCmnLngQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The language controller.
 *
 * @author Cristian Saball
 * @since 1.8
 * @version 14 sept. 2021 8:52:16
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class LanguageController implements LanguageApi {

    /** The CmnLngQry service interface. */
    @Autowired
    protected IBlCmnLngQry iBlCmnLngQry;


    /**
     * Query language. It will return the language
     *
     * @author Cristian Saball
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return oCmnLngS -> The type of language
     */
    @Override
    public ResponseEntity<OCmnLngS> getLanguage(
	    @ApiParam(value = "Language code", required = true) @PathVariable("lngVal") String lngVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal) {

	log.info("The getLanguages rest operation had been called...");

	OCmnLngS oCmnLngS = iBlCmnLngQry.getLng(lngVal, usrVal);

	return new ResponseEntity<>(oCmnLngS, HttpStatus.OK);
    }


}
