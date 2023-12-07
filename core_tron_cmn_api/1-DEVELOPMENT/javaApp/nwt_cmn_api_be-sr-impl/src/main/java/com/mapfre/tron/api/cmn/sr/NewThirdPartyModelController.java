package com.mapfre.tron.api.cmn.sr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mapfre.tron.api.cmn.model.McaMdtThpNewTrueReponsenDefinition;
import com.mapfre.tron.api.thp.mdl.bl.IBlNewThirdPartymodel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * The new third party model controller implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 5 Dec 2022 - 11:53:28
 *
 */
@Slf4j
@Controller
@Api(tags={ "Common",})
public class NewThirdPartyModelController implements NewThirdPartyModelApi {

    @Autowired
    protected IBlNewThirdPartymodel iBlNewThirdPartymodel;

    /**
     * Query Company New Third Party Model.
     *
     * @param cmpVal -> Company code
     * @return       -> It will return S if the company is new third party model, otherwise N
     */
    @Override
    public ResponseEntity<McaMdtThpNewTrueReponsenDefinition> getNewThirdPartyModel(
            @ApiParam(value = "Company code", required = true) @RequestHeader(value = "cmpVal", required = true) final Integer cmpVal) {

        if (log.isInfoEnabled()) {
            log.info("GetNewThirdPartyModel operation had been called...");
        }

        McaMdtThpNewTrueReponsenDefinition mcaMdtThpNew = iBlNewThirdPartymodel.getNewThirdPartyModel(cmpVal);

        return new ResponseEntity<>(mcaMdtThpNew, HttpStatus.OK);
    }

}
