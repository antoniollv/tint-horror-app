package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapfre.nwt.trn.cmn.ssg.bo.OCmnSsgS;
import com.mapfre.tron.api.cmn.ssg.bl.IBlCmnSsgQry;
import com.mapfre.tron.api.trn.ssg.bl.IBlCmnSsgCrt;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for StorageFromSelfServiceController.
 * 
 * @author Cristian Saball
 * @version 01/02/2022
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Self-Service",})
public class StorageFromSelfServiceController implements StorageFromSelfServiceApi {
    
    @Autowired
    IBlCmnSsgQry iBlCmnSsgQry;
    
    @Autowired
    protected IBlCmnSsgCrt iBlCmnSsgCrt;
    
    
    /**
     * Query Storage From Self-Service.
     *
     * @author Cristian Saball
     *
     * @param cmpVal 	-> Company code
     * @param usrVal 	-> User code
     * @param lngVal 	-> Language code
     * @param sfrSciVal -> Self-Service Section
     * @param sfrSbsVal -> Self-Service Subsection
     * @param sfrIdnVal -> Authentication Identifier
     * @param vrbDspVal -> Variable Description
     * @return       	-> The variables saved for the self service front end
     */
    @Override
    public ResponseEntity<List<OCmnSsgS>> getStorageFromSelfService(
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @NotNull @ApiParam(value = "Self-Service Section", required = true) @Valid @RequestParam(value = "sfrSciVal", required = true) String sfrSciVal,
	    @NotNull @ApiParam(value = "Self-Service Subsection", required = true) @Valid @RequestParam(value = "sfrSbsVal", required = true) String sfrSbsVal,
	    @ApiParam(value = "Authentication Identifier") @Valid @RequestParam(value = "sfrIdnVal", required = false) List<String> sfrIdnVal,
	    @ApiParam(value = "Variable Description") @Valid @RequestParam(value = "vrbDspVal", required = false) String vrbDspVal) {

        log.info("The getStorageFromSelfService rest operation had been called...");

            List<OCmnSsgS> lOCmnSsgSList = iBlCmnSsgQry.getStorageFromSelfService(cmpVal, usrVal, lngVal, sfrSciVal, sfrSbsVal, sfrIdnVal, vrbDspVal);

            return new ResponseEntity<>(lOCmnSsgSList, HttpStatus.OK);

    }



    /**
     * Create Storage From Self-Service.
     *
     * @param cmpVal               -> Company code
     * @param usrVal               -> User code
     * @param lngVal               -> Language code
     * @param inSelfServiceStorage -> Input data to new self service storage
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public ResponseEntity<Void> postStorageFromSelfService(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @ApiParam(value = "Input data to new self service storage", required = true) @Valid @RequestBody final List<OCmnSsgS> inSelfServiceStorage) {

        log.info("Create Storage From Self-Service operation had been called");

        iBlCmnSsgCrt.postStorageFromSelfService(cmpVal, usrVal, lngVal, inSelfServiceStorage);

        return new ResponseEntity("Operation succesfully done!", HttpStatus.CREATED);
    }

    
}
