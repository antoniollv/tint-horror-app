package com.mapfre.tron.api.cmn.fix.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.ard.fxa.bo.OArdFxaS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class IBLCmnFixQryImpl extends TwBlCmnBase implements IBLCmnFixQry {

    @Override
    public List<OArdFxaS> getFixedAttributes(Integer cmpVal, String lngVal, String usrVal) {
        log.debug("Tronweb business logic implementation getFixedAttributes have been called...");

        // reseting session
        resetSession();

        try {
            List<OArdFxaS> listOArdFxaS = cacheableAttribute.getOArdFxaS(lngVal, new BigDecimal(cmpVal), usrVal);

            if (listOArdFxaS == null || listOArdFxaS.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return listOArdFxaS;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "FXA", new BigDecimal(cmpVal));
        }

        return Collections.emptyList();
    }

}
