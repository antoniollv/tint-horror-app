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

import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;
import com.mapfre.tron.api.grs.zof.bl.IBlGrsZofQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The zone two list controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 2 sept. 2020 14:11:54
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class ZoneFiveController implements ZoneFiveApi {
	
    /** The GrsZntQry business interface.*/
    @Autowired
    protected IBlGrsZofQry iBlGrsZofQry;
	
    
    
    /**
     * Query zone five list. It will return the list of zone five (postalCode).  
     * It will be mandatory send the parameters defined in the service and the 
     * service will response with the output object defined.
     * 
     * @param usrVal
     * @param lngVal
     * @param pslCodVal
     * @param reaPsc
     * 
     * @return List<OGrsZofS>
     * 
     */
    @Override
    public ResponseEntity<List<OGrsZofS>> getZoneFiveByPslCodVal(
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @ApiParam(value = "Postal Code", required = true) @PathVariable("pslCodVal") String pslCodVal,
	    @ApiParam(value = "Real Postal Code") @Valid @RequestParam(value = "reaPsc", required = false) String reaPsc) {

	log.info(" THE ZONE FIVE rest operation had been called...");

	List<OGrsZofS> oGrsZofSList = iBlGrsZofQry.get(pslCodVal, usrVal, lngVal, reaPsc);

	return new ResponseEntity<>(oGrsZofSList, HttpStatus.OK);

    }




}
