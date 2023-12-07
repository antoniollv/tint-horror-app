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

import com.mapfre.nwt.trn.lsf.fid.bo.OLsfFidS;
import com.mapfre.tron.api.lsf.fid.bl.IBlLsfFidQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The FileTypeDefinition controller.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 9 feb 2023 - 17:49:28
 *
 */
@Data
@Slf4j
@Controller
@Api(tags={ "File",})
public class FileTypeDefinitionController implements FileTypeDefinitionApi {

    /** The IBlLsfFidQry service interface. */
    @Autowired
    protected IBlLsfFidQry IBlLsfFidQry;


    /**
     * Query file type definition
     *
     * @author Javier Sangil
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param filTypVal -> File Type Value
     * 
     * @return OLsfFidS -> File type defintion
     */
    @Override
    public ResponseEntity<OLsfFidS> getFileTypeDefinition(
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @ApiParam(value = "File Type Value", required = true) @PathVariable("filTypVal") String filTypVal) {

	log.info("The getLanguages rest operation had been called...");

	OLsfFidS oLsfFidS = IBlLsfFidQry.getFileTypeDefinition(cmpVal, usrVal, lngVal, filTypVal);

	return new ResponseEntity<>(oLsfFidS, HttpStatus.OK);
    }


}
