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

import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;
import com.mapfre.tron.api.lvl.cnt.bl.IBlScnLvlCnt;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Second level controller.
 *
 * @author magarafr
 * @since 1.0.0
 * @version 09 feb. 2021 14:54:18
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class SecondLevelController implements SecondLevelApi {

    @Autowired
    protected IBlScnLvlCnt iBlScnLvlCnt;
    
    /**
     * Query Contact From Second Level
     *
     * @author magarafr
     * @purpose Query the second level contact information. It will return the
     *          contact of the second level of the commercial structure . It will be
     *          mandatory send the parameters defined in the service and the service
     *          will response with the output object defined.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param scnLvlVal -> Second Level
     * @return -> OThpCntS
     */
    @Override
    public ResponseEntity<OThpCntS> getContactFromSecondLevel(
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Second Level", required = true) @PathVariable("scnLvlVal") Integer scnLvlVal) {

	log.info("The getContactFromSecondLevel rest operation had been called...");


	    OThpCntS lvOThpCntS = iBlScnLvlCnt.scnLvlCnt(cmpVal, scnLvlVal, lngVal);
	    
                return new ResponseEntity<>(lvOThpCntS, HttpStatus.OK);

     }

}
