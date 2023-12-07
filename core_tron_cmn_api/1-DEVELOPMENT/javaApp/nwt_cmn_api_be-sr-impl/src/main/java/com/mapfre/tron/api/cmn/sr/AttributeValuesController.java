package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvS;
import com.mapfre.tron.api.ard.aby.bl.IBlSrArdAbvQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@RestController
@Api(tags={ "Common",})
public class AttributeValuesController implements AttributeValuesApi{

    @Autowired
    protected IBlSrArdAbvQry iBlSrArdAbvQry;

    /**
     * Query Attribute Values
     * 
     * @param lngVal -> Language code
     * @param usrVal -> User
     * @param cmpVal -> Company
     * @param fldNam -> Field code
     * @param lobVal -> Line of Business
     * @param mdtVal -> Modality
     * @return -> List<OArdAbvS>
     */
    @Override
    public ResponseEntity<List<OArdAbvS>> getAttributeValues(
            @ApiParam(value = "Language code" ,required=true) @RequestHeader(value="lngVal", required=true) String lngVal,
            @NotNull @ApiParam(value = "User", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
            @NotNull @ApiParam(value = "Company", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
            @NotNull @ApiParam(value = "Field code", required = true) @Valid @RequestParam(value = "fldNam", required = true) String fldNam,
            @ApiParam(value = "Line of Business") @Valid @RequestParam(value = "lobVal", required = false) Integer lobVal,
            @ApiParam(value = "Modality") @Valid @RequestParam(value = "mdtVal", required = false) Integer mdtVal) {

        log.info("The getAttributeValues rest operation had been called...");
        
        List<OArdAbvS> oArdAbvP = iBlSrArdAbvQry.tbl(lngVal, usrVal, cmpVal, fldNam, lobVal, mdtVal);
        
        return new ResponseEntity<>(oArdAbvP, HttpStatus.OK);


    }

}
