package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.trn.vrb.bo.OTrnVrbS;
import com.mapfre.tron.api.trn.vrb.bl.IBlTrnVrbQry;
import com.mapfre.tron.api.trv.vrb.bl.IBlTrvVrbQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for types services.
 * 
 * @author MAJOGUAM
 * @since 1.8
 * @version 24/02/2021
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Variable Definition",})
public class VariableDefinitionController implements VariableDefinitionApi {

    /** The TrvVrbQry business interface.*/
    @Autowired
    protected IBlTrvVrbQry iBLTrvVrbQry;

    /** The TrnVrbQry business service.*/
    @Autowired
    protected IBlTrnVrbQry iBlTrnVrbQry;

    /**
     * Query variable definition by company. It will return the variable definition
     * filtering by company
     * 
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param vrbNamVal -> Variable Name
     * @param vldDat    -> Validation Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
    @Override
    public ResponseEntity<List<OTrnVrbS>> getVariableDefinitionByCompany(
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
            @NotNull @ApiParam(value = "Variable Name", required = true) @Valid @RequestParam(value = "vrbNamVal", required = true) String vrbNamVal,
            @ApiParam(value = "Validation Date") @Valid @RequestParam(value = "vldDat", required = false) Long vldDat) {
        log.info("The postVariableConceptsDefinition rest operation had been called...");

        List<OTrnVrbS> oPlyAtrSList = iBLTrvVrbQry.get(usrVal, lngVal, cmpVal, vrbNamVal, vldDat);

        return new ResponseEntity<>(oPlyAtrSList, HttpStatus.OK);

    }

    /**
     * Query variable definition by line of business. It will return the variable
     * definition filtering by company and line of business
     * 
     * 
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param vrbNamVal -> Variable Name
     * @param vldDat    -> Validation Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
    @Override
    public ResponseEntity<List<OTrnVrbS>> getVariableDefinitionByLineOfBusiness(
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
            @NotNull @ApiParam(value = "Line of Business", required = true) @Valid @RequestParam(value = "lobVal", required = true) Integer lobVal,
            @NotNull @ApiParam(value = "Variable Name", required = true) @Valid @RequestParam(value = "vrbNamVal", required = true) String vrbNamVal,
            @ApiParam(value = "Validation Date") @Valid @RequestParam(value = "vldDat", required = false) Long vldDat) {

        log.info("The getVariableDefinitionByLineOfBusiness rest operation had been called...");

        List<OTrnVrbS> oPlyAtrSList = iBLTrvVrbQry.getVrb(usrVal, lngVal, cmpVal, vrbNamVal, vldDat, lobVal);

        return new ResponseEntity<>(oPlyAtrSList, HttpStatus.OK);

    }

    /**
     * Update variable definition by thrid party. It will update the variable
     * definition by third party
     * 
     * @param usrVal               -> User code
     * @param lngVal               -> Language code
     * @param cmpVal               -> Company code
     * @param thpDcmTypVal         -> Document type
     * @param thpDcmVal            -> document
     * @param thpAcvVal            -> Activity
     * @param vrbNamVal            -> Variable Name
     * @param inVariableDefinition -> Input data to update the variable definition
     * 
     * @return void
     * 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public ResponseEntity<Void> putVariableDefinitionByThirdParty(
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
            @NotNull @ApiParam(value = "Document type", required = true) @Valid @RequestParam(value = "thpDcmTypVal", required = true) String thpDcmTypVal,
            @NotNull @ApiParam(value = "document", required = true) @Valid @RequestParam(value = "thpDcmVal", required = true) String thpDcmVal,
            @NotNull @ApiParam(value = "Activity", required = true) @Valid @RequestParam(value = "thpAcvVal", required = true) Integer thpAcvVal,
            @NotNull @ApiParam(value = "Variable Name", required = true) @Valid @RequestParam(value = "vrbNamVal", required = true) String vrbNamVal,
            @ApiParam(value = "Input data to update the variable definition", required = true) @Valid @RequestBody OTrnVrbS inVariableDefinition,
            @ApiParam(value = "Language code") @Valid @RequestParam(value = "lngVal", required = false) String lngVal) {

        log.info("The putVariableDefinitionByThirdParty rest operation had been called...");

        try {

            iBLTrvVrbQry.upd(usrVal, lngVal, cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal, vrbNamVal,
                    inVariableDefinition);

            return new ResponseEntity("Operation successfully done!", HttpStatus.OK);
        } catch (NwtException e) {
            throw e;
        } catch (Exception e) {
            throw new NwtException(e.getMessage(), e);
        }

    }

    /**
     * Variable Definition
     * 
     * 
     * @param usrVal       -> User code
     * @param lngVal       -> Language code
     * @param cmpVal       -> Company code
     * @param thpDcmTypVal -> Document type
     * @param thpDcmVal    -> Document
     * @param thpAcvVal    -> Activity
     * @param vrbNamVal    -> Variable Name
     * @param vldDat       -> Valid Date
     * 
     * @return List<OTrnVrbS>
     * 
     */
    @Override
    public ResponseEntity<List<OTrnVrbS>> getVariableDefinitionByThirdParty(
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
            @NotNull @ApiParam(value = "Document type", required = true) @Valid @RequestParam(value = "thpDcmTypVal", required = true) String thpDcmTypVal,
            @NotNull @ApiParam(value = "Document", required = true) @Valid @RequestParam(value = "thpDcmVal", required = true) String thpDcmVal,
            @NotNull @ApiParam(value = "Activity", required = true) @Valid @RequestParam(value = "thpAcvVal", required = true) Integer thpAcvVal,
            @NotNull @ApiParam(value = "Variable Name", required = true) @Valid @RequestParam(value = "vrbNamVal", required = true) String vrbNamVal,
            @ApiParam(value = "Validation Date") @Valid @RequestParam(value = "vldDat", required = false) Long vldDat) {

        log.info("The getVariableDefinitionByThirdParty rest operation had been called...");

        List<OTrnVrbS> oPlyAtrSList = iBLTrvVrbQry.getThp(usrVal, lngVal, cmpVal, thpDcmTypVal, thpDcmVal, thpAcvVal,
                vrbNamVal, vldDat);

        return new ResponseEntity<>(oPlyAtrSList, HttpStatus.OK);

    }

    /**
     * Query variable definition by coverage.
     *
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> Company code
     * @param lobVal    -> Line of Business
     * @param cvrVal    -> Coverage
     * @param vrbNamVal -> Variable name
     * @param mdtVal    -> Modality
     * @param vldDat    -> Validation Date
     * @param prnVrbVal -> Parent Variable Value
     * @return          -> It will return the variable definition filtering by coverage
     */
    @Override
    public ResponseEntity<List<OTrnVrbS>> getVariableDefinitionByCoverage(
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @NotNull @ApiParam(value = "Line of Business", required = true) @Valid @RequestParam(value = "lobVal", required = true) final Integer lobVal,
            @NotNull @ApiParam(value = "Coverage", required = true) @Valid @RequestParam(value = "cvrVal", required = true) final Integer cvrVal,
            @NotNull @ApiParam(value = "Variable Name", required = true) @Valid @RequestParam(value = "vrbNamVal", required = true) final String vrbNamVal,
            @ApiParam(value = "Modality") @Valid @RequestParam(value = "mdtVal", required = false) final Integer mdtVal,
            @ApiParam(value = "Validation Date") @Valid @RequestParam(value = "vldDat", required = false) final Long vldDat,
            @ApiParam(value = "Parent Variable Value") @Valid @RequestParam(value = "prnVrbVal", required = false) final String prnVrbVal) {

        log.info("The getVariableDefinitionByCoverage rest operation had been called...");

        List<OTrnVrbS> lvOTrnVrbST = iBlTrnVrbQry.getVariableDefinitionByCoverage(usrVal, lngVal, cmpVal, lobVal, cvrVal, vrbNamVal, mdtVal, vldDat, prnVrbVal);

        return new ResponseEntity<>(lvOTrnVrbST, HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<OTrnVrbS> getVariableDefinitionByCompanyById(
	    @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
	    @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
	    @NotNull @ApiParam(value = "Variable Name", required = true) @Valid @RequestParam(value = "vrbNamVal", required = true) String vrbNamVal,
	    @ApiParam(value = "Variable Value", required = true) @PathVariable("vrbVal") String vrbVal,
	    @ApiParam(value = "Validation Date") @Valid @RequestParam(value = "vldDat", required = false) Long vldDat) {
        
	log.info("The getVariableDefinitionByCompanyById rest operation had been called...");

	OTrnVrbS oTrnVrbS = iBLTrvVrbQry.getById(usrVal, lngVal, cmpVal, vrbNamVal, vldDat, vrbVal);

        return new ResponseEntity<>(oTrnVrbS, HttpStatus.OK);	
    }

}
