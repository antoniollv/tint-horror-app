package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;
import com.mapfre.tron.api.cmn.model.InConstantInformation;
import com.mapfre.tron.api.cte.def.bl.IBlCteDefQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Query constants definition
 *
 * @author magarafr
 * @since 1.8
 * @version 19 ene. 2021 13:57:46
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Constant Definition",})
public class ConstantsDefinitionController implements ConstantsDefinitionApi {
    
    /** The CteDefQry business interface.*/
    @Autowired
    protected IBlCteDefQry iBlCteDefQry;
    
    @Override
    public ResponseEntity<List<OPlyAtrS>> postConstantsDefinition(
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @ApiParam(value = "Input Data to query constants", required = true) @Valid @RequestBody InConstantInformation inConstantInformation) {
	
	log.info("The postConstantsDefinition rest operation had been called...");

            
            //Todos los fldNam concatenados
            String strFldNam = "";
            
            for(OPlyAtrS lvOPlyAtrS : inConstantInformation.getOPlyAtrPT()) {
        	strFldNam += "'".concat(lvOPlyAtrS.getFldNam()).concat("',");
            }
            
            if(!"".equals(strFldNam)) {
        	strFldNam = strFldNam.substring(0, strFldNam.length()-1);
            }
            
            List<OPlyAtrS> lvOPlyAtrSList = iBlCteDefQry.xxCnn(inConstantInformation, strFldNam, lngVal);
                        
                return new ResponseEntity<>(lvOPlyAtrSList, HttpStatus.OK);
            
    }

}
