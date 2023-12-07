package com.mapfre.tron.api.cmn.sr;

import java.math.BigDecimal;
import java.util.Calendar;
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

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.nmr.gpp.bo.ONmrGppS;
import com.mapfre.nwt.trn.prt.del.bo.OPrtDelS;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobP;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.prt.lob.bl.IBlPrtLobQry;
import com.mapfre.tron.api.cmn.prt.lob.dl.OPrtLobQryPK;
import com.mapfre.tron.api.nmr.gpp.bl.IBlNmrGppQry;
import com.mapfre.tron.api.prt.del.bl.IBlPrtDelQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The line of business controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 3 sept. 2020 8:51:13
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class LineOfBusinessController implements LineOfBusinessApi {

    /** The PrtLobQry business interface.*/
    @Autowired
    protected com.mapfre.tron.api.prt.lob.bl.IBlPrtLobQry iBLPrtLobQry;
    /** The PrtLobQry business interface.*/
    @Autowired
    protected IBlPrtLobQry iCmnBLPrtLobQry;
    
    
    
    /** The PrtLobQry business interface.*/
    @Autowired
    protected IBlNmrGppQry iBlNmrGppQry;
    
    
    /** The PrtLobQry business interface.*/
    @Autowired
    protected IBlPrtDelQry iBlPrtDelQry;

    
    /**
     * Query lines of business. It will return the lines of business.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param lobVal -> Line of Business
     * @return -> The lines of business
     */
    @Override
    public ResponseEntity<OPrtLobS> getLineOfBusiness(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Line of Business", required = true) @RequestHeader(value = "lobVal", required = true) final Integer lobVal) {

        log.info("The getLineOfBusiness rest operation had been called...");
       
            BigDecimal lobValBd = (lobVal != null) ? new BigDecimal(lobVal) : null;
            BigDecimal cmpValBd = (cmpVal != null) ? new BigDecimal(cmpVal) : null;
    	
            OPrtLobS lvOPrtLobS = iCmnBLPrtLobQry.lob(OPrtLobQryPK.builder().lobVal(lobValBd).cmpVal(cmpValBd).lngVal(lngVal).build());

            return new ResponseEntity<>(lvOPrtLobS, HttpStatus.OK);

    }

    /**
     * Query line of business validation date.
     * It wil return the validation date of a line of business for a date introduced as input.
     *
     * @author pvraul1
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param lobVal -> Line of Business
     * @param vldDat -> Validation Date
     *
     * @return -> The validation date of a line of business for a date introduced as input
     */
    @Override
    public ResponseEntity<OPrtLobS> getLineOfBusinessValidationDate(
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Line of Business", required = true) @PathVariable("lobVal") final Integer lobVal,
            @NotNull @ApiParam(value = "Validation Date", required = true) @Valid @RequestParam(value = "vldDat", required = true) final Long vldDat) {

        log.info("The getLineOfBusinessValidationDate rest operation had been called...");
        
        OPrtLobP lvOPrtLobP = iBLPrtLobQry.get(lngVal, cmpVal, usrVal, lobVal, vldDat);
        
        return new ResponseEntity<>(lvOPrtLobP.getOPrtLobS(), HttpStatus.OK);

    }
    
    
    
    @Override
    public ResponseEntity<Integer> getActuarialAge(@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,@ApiParam(value = "Language code" ,required=true) @RequestHeader(value="lngVal", required=true) String lngVal,@NotNull @ApiParam(value = "Query Date", required = true) @Valid @RequestParam(value = "qryDat", required = true) Long qryDat,@NotNull @ApiParam(value = "Date of Birth", required = true) @Valid @RequestParam(value = "brdDat", required = true) Long brdDat) {
        log.info("The getActuarialAge rest operation had been called...");

        try {


            
            Calendar nacimiento = Calendar.getInstance();
            nacimiento.setTimeInMillis(brdDat);

            int nacYear = nacimiento.get(Calendar.YEAR);
            int nacMonth = nacimiento.get(Calendar.MONTH);
            int nacDay = nacimiento.get(Calendar.DAY_OF_MONTH);
            
            
       
            
            
            Calendar calculo = Calendar.getInstance();
            calculo.setTimeInMillis(qryDat);

            int calYear = calculo.get(Calendar.YEAR);
            int calMonth = calculo.get(Calendar.MONTH);
            int calDay = calculo.get(Calendar.DAY_OF_MONTH);

            
            
            
            Double edad = (double) (calYear - nacYear);

            edad += (double)(calMonth-nacMonth)/12;                     

            edad += (double)(calDay-nacDay)/365;
            
            Integer edadActual = (int) (edad + 0.5);
            

            return new ResponseEntity<>(edadActual, HttpStatus.OK);

        } catch (NwtException nwte) {
            throw nwte;

        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);
        }

    }
    
    
    
    /**
     * 
     * Query group policies. 
     * It will return the group policies that belong  to a line of business. 
     * It will be mandatory send the parameters defined  in the service and 
     * the service will response with the output object defined.
     * 
     * @param lngVal   -> Language code
     * @param cmpVal   -> Company code
     * @param usrVal   -> User code
     * @param lobVal   -> Line of Business
     * @param vldDat   -> Valid Date
     * 
     * @return List<ONmrGppS>
     * 
     * 
     */
    @Override
	public ResponseEntity<List<ONmrGppS>> getGroupPolicies(
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
			@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
			@ApiParam(value = "Line of Business", required = true) @PathVariable("lobVal") Integer lobVal,
			@ApiParam(value = "Valid Date") @Valid @RequestParam(value = "vldDat", required = false) Long vldDat) {
        
	log.info("The getGroupPolicies rest operation had been called...");

        List<ONmrGppS> oPlyAtrSList = iBlNmrGppQry.get(usrVal, lngVal, cmpVal, vldDat, lobVal);

        return new ResponseEntity<>(oPlyAtrSList, HttpStatus.OK);
			
	}
    
    
    
    
    
    
    /**
     * 
     Query deals. It will return the deals that belong to a line of business. 
     * 
     * @param lngVal   -> Language code
     * @param cmpVal   -> Company code
     * @param usrVal   -> User code
     * @param lobVal   -> Line of Business
     * @param vldDat   -> Valid Date
     * 
     * @return List<OPrtDelS>
     * 
     * 
     */
    @Override
	public ResponseEntity<List<OPrtDelS>> getDeals(
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
			@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
			@ApiParam(value = "Line of Business", required = true) @PathVariable("lobVal") Integer lobVal,
			@ApiParam(value = "Valid Date") @Valid @RequestParam(value = "vldDat", required = false) Long vldDat) {
        log.info("The getDeals rest operation had been called...");

        List<OPrtDelS> oPrtDelSList = iBlPrtDelQry.get(usrVal, lngVal, cmpVal, vldDat, lobVal);

        return new ResponseEntity<>(oPrtDelSList, HttpStatus.OK);
			
	}

    /**
     * Query line of business by deals.
     *
     * @author Cristian Saball
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param delVal -> Deal value
     * @param vldDat -> Validation Date
     *
     * @return -> The list of line of business by deals
     */
    @Override
    public ResponseEntity<List<OPrtLobS>> getLineOfBusinessByDeal(
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @NotNull @ApiParam(value = "Deal value", required = true) @Valid @RequestParam(value = "delVal", required = true) String delVal,
	    @ApiParam(value = "Valid Date") @Valid @RequestParam(value = "vldDat", required = false) Long vldDat) {
        log.info("The getDeals rest operation had been called...");

        List<OPrtLobS> oPrtLobST = iBLPrtLobQry.getLineOfBusinessByDeal(lngVal, cmpVal, usrVal, delVal, vldDat);

        return new ResponseEntity<>(oPrtLobST, HttpStatus.OK);
			
	}

    
}
