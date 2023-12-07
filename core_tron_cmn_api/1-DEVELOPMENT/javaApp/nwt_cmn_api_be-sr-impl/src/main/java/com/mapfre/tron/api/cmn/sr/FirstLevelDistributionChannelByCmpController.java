package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapfre.nwt.trn.dsr.fdc.bo.ODsrFdcS;
import com.mapfre.tron.api.dsr.fdc.bl.IBlDsrFdcQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for FirstLevelDistributionChannel.
 * 
 * @author Javier Sangil
 * @version 12/08/2021
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Common",})
public class FirstLevelDistributionChannelByCmpController implements FirstLevelDistributionChannelByCmpApi {

    @Autowired
    protected IBlDsrFdcQry iBlDsrFdcQry;

    /**
     * Query First Level Distribution Channel by company
     *
     * @author Javier S.
     * 
     * @purpose Query First Level Distribution Channel by company
     * 
     * @param cmpVal -> company code
     * @param usrVal -> user value
     * @param lngVal -> language value
     * 
     * @return List<ODsrFdcS>
     * 
     */
    @Override
    public ResponseEntity<List<ODsrFdcS>> getfirstLevelDistributionChannelByCmp(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal) {

        log.info("The getfirstLevelDistributionChannelByCmp rest operation had been called...");

        List<ODsrFdcS> oDsrFdcSList = iBlDsrFdcQry.tblByCmp(cmpVal, lngVal, usrVal);

        return new ResponseEntity<>(oDsrFdcSList, HttpStatus.OK);
    }

    /**
     * First Level Distribution Channel by Company.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language value
     * @param usrVal       -> User value
     * @param frsDstHnlVal -> First level distribution channel
     * @return             -> The first level distribution channel by company
     */
    @Override
    public ResponseEntity<ODsrFdcS> getfirstLevelDistributionChannelByCmpById(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) final Integer cmpVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) final String lngVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) final String usrVal,
            @ApiParam(value = "First level distribution channel", required = true) @PathVariable("frsDstHnlVal") final String frsDstHnlVal) {

        log.info("The getfirstLevelDistributionChannelByCmpById rest operation had been called...");

        ODsrFdcS lvODsrFdcS = iBlDsrFdcQry.tblByCmpById(cmpVal, lngVal, usrVal, frsDstHnlVal);

        return new ResponseEntity<>(lvODsrFdcS, HttpStatus.OK);
    }

}
