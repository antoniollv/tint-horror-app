package com.mapfre.tron.api.cmn.sr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.mapfre.nwt.trn.trn.nwo.bo.OTrnNwoS;
import com.mapfre.tron.api.trn.nwo.bl.IBlTrnNwoQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * The rest controller for NewtronOperationList.
 * 
 * @author Cristian Saball
 * @version 07/02/2022
 *
 */
@Data
@Slf4j
@RestController
@Api(tags={ "Newtron Operation",})
public class NewtronOperationListController implements NewtronOperationListApi {
    
    @Autowired
    IBlTrnNwoQry iBlTrnNwoQry;
    
    /**
     * Query Newtron operation.
     *
     * @author Cristian Saball
     *
     * @param lngVal 	-> Language code
     * @return       	-> The newtron operations
     */
    @Override
    public ResponseEntity<List<OTrnNwoS>> getNewtronOperationList(
	    @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal) {

        log.info("The getNewtronOperationList rest operation had been called...");

            List<OTrnNwoS> lOTrnNwoSList = iBlTrnNwoQry.getNewtronOperationList(lngVal);

            return new ResponseEntity<>(lOTrnNwoSList, HttpStatus.OK);

    }

    
}
