package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.mapfre.nwt.trn.ard.fxa.bo.OArdFxaS;
import com.mapfre.tron.api.cmn.fix.bl.IBLCmnFixQry;

import io.swagger.annotations.Api;

@Controller
@Api(tags={ "Common",})
public class FixedAttributesController implements FixedAttributesApi {
    
    @Autowired
    protected IBLCmnFixQry iBLCmnFixQry;

    @Override
    public ResponseEntity<List<OArdFxaS>> getFixedAttributes(Integer cmpVal, String lngVal, String usrVal) {
	log.info("The getFixedAttributes rest operation had been called...");

	List<OArdFxaS> oArdFxaS = iBLCmnFixQry.getFixedAttributes(cmpVal, lngVal, usrVal);

	return new ResponseEntity<>(oArdFxaS, HttpStatus.OK);
    }

    
}
