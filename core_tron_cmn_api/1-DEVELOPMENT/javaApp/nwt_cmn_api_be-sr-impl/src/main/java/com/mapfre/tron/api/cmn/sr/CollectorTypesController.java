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

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypCPT;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypP;
import com.mapfre.nwt.trn.rcd.tmd.bo.ORcdTmdS;
import com.mapfre.tron.api.cmn.rcd.bl.IBLCmnRcdQry;
import com.mapfre.tron.api.cmn.typ.bl.IBLCmnTypQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The collector types controller.
 *
 * @author pvraul1
 * @since 1.8
 * @version 3 sept. 2020 14:19:24
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "Common",})
public class CollectorTypesController implements CollectorTypesApi {

    /** The CmnTypQry service interface. */
    @Autowired
    protected IBLCmnTypQry iBLCmnTypQry;
    
    @Autowired
    protected IBLCmnRcdQry iBLCmnRcdQry;

    /**
     * Query collector types. It will return the types of collector.
     *
     * @author pvraul1
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return       -> The types of collector
     */
    @Override
    public ResponseEntity<List<OCmnTypP>> getCollectorTypes(
	    @ApiParam(value = "User code", required = true) @RequestHeader(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @ApiParam(value = "Company code") @Valid @RequestParam(value = "cmpVal", required = false) Integer cmpVal) {

        log.info("The  getCollectorTypes rest operation had been called...");

        try {

            OCmnTypCPT lvOCmnTypCPT = iBLCmnTypQry.Collect(lngVal, cmpVal);

            if (lvOCmnTypCPT != null && lvOCmnTypCPT.getOCmnTypPT() != null && !lvOCmnTypCPT.getOCmnTypPT().isEmpty()) {
                return new ResponseEntity<>(lvOCmnTypCPT.getOCmnTypPT(), HttpStatus.OK);

            } else {
                String message = String.format(
                        "TST-20001: Entity with usrVal: %s and lngVal: %s: not found", usrVal, lngVal);
                throw new NwtException(message);
            }

        } catch (NwtException nwte) {
            throw nwte;

        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);
        }

    }
    
    
    
    
    /**
     * @author AMINJOU
     * 
     * Query collector types v1. 
     * It will return the types of collector. 
     * It will be mandatory send the parameters defined in the service and the service 
     * will response with the output object defined.
     * 
     * @param usrVal
     * @param lngVal
     * @param cmpVal
     * 
     * @return List<ORcdTmdS>
     * 
     */
    @Override
	public ResponseEntity<List<ORcdTmdS>> getCollectorTypesV1(
			@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@ApiParam(value = "Company code") @Valid @RequestParam(value = "cmpVal", required = false) Integer cmpVal) {
        log.info("The getCollectorTypes rest operation had been called...");

        
        List<ORcdTmdS> lvORcdTmdS = iBLCmnRcdQry.getCollectorTypesV1(usrVal, lngVal, cmpVal);
        
        return new ResponseEntity<>(lvORcdTmdS, HttpStatus.OK);
    }

    
    
    
    /**
     * @author AMINJOU
     * 
     * Query collector type v1. 
     * It will return the type of collector. 
     * It will be mandatory send the parameters defined in the service and the 
     * service will response with the output object defined.
     * 
     * @param usrVal
     * @param lngVal
     * @param cmpVal
     * @param mnrTypVal
     * 
     * @return ORcdTmdS
     * 
     */
    @Override
	public ResponseEntity<ORcdTmdS> getCollectorTypeV1(
			@NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
			@ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
			@NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
			@ApiParam(value = "Collector Type", required = true) @PathVariable("mnrTypVal") String mnrTypVal) {
        log.info("The getCollectorTypes rest operation had been called...");

        
        ORcdTmdS lvORcdTmdS = iBLCmnRcdQry.getCollectorTypeV1(usrVal, lngVal, cmpVal, mnrTypVal);
        
        return new ResponseEntity<>(lvORcdTmdS, HttpStatus.OK);    
        }


}
