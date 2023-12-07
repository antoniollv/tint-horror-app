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

import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;
import com.mapfre.tron.api.grs.zof.bl.IBlGrsZofQry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@RestController
@Api(tags={ "Common",})
public class ZoneFiveListController implements ZoneFiveListApi {

    @Autowired
    protected IBlGrsZofQry iBLGrsZofQry;

    /**
     * Query zone five list
     * 
     * @param cmpVal -> Company code*
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param sttVal -> State
     * @param pvcVal -> Province Value
     * @param twnVal -> Town Value
     * @param reaPsc -> Real Postal Code
     * @return -> List<OGrsZofS>
     */
    @Override
    public ResponseEntity<List<OGrsZofS>> getZoneFiveList(
            @NotNull @ApiParam(value = "Company code", required = true) @Valid @RequestParam(value = "cmpVal", required = true) Integer cmpVal,
            @NotNull @ApiParam(value = "User code", required = true) @Valid @RequestParam(value = "usrVal", required = true) String usrVal,
            @ApiParam(value = "Language code", required = true) @RequestHeader(value = "lngVal", required = true) String lngVal,
            @NotNull @ApiParam(value = "Country", required = true) @Valid @RequestParam(value = "cnyVal", required = true) String cnyVal,
            @NotNull @ApiParam(value = "State", required = true) @Valid @RequestParam(value = "sttVal", required = true) Integer sttVal,
            @NotNull @ApiParam(value = "Province Value", required = true) @Valid @RequestParam(value = "pvcVal", required = true) Integer pvcVal,
            @NotNull @ApiParam(value = "Town Value", required = true) @Valid @RequestParam(value = "twnVal", required = true) Integer twnVal,
            @NotNull @ApiParam(value = "Real Postal Code", required = true) @Valid @RequestParam(value = "reaPsc", required = true) String reaPsc) {

        log.info("The getZoneFiveList rest operation had been called...");

            List<OGrsZofS> lOGrsZofS = iBLGrsZofQry.tbl(cmpVal, usrVal, lngVal, cnyVal, sttVal, pvcVal, twnVal, reaPsc);

            return new ResponseEntity<>(lOGrsZofS, HttpStatus.OK);

    }

}
