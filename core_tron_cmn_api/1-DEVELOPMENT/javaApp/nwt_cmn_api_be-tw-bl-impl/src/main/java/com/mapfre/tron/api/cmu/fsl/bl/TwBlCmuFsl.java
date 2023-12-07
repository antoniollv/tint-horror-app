package com.mapfre.tron.api.cmu.fsl.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmu.fsl.bo.OCmuFslS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The Cmu first level implementation service.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 25 Oct 2021 - 10:05:24
 *
 */
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmuFsl extends TwBlCmnBase implements IBlCmuFsl {

    /**
     * Query First Level List.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param usrVal -> User code
     * @return -> The first level list
     */
    @Override
    public List<OCmuFslS> fstLvlQry(final Integer cmpVal, final String lngVal, final String usrVal) {
        log.info("Tronweb business logic implementation fstLvlQry have been called...");

        // reseting session
        resetSession();

        try {
            List<OCmuFslS> lvOCmuFslST = cacheableAttribute.getFirstLevels(cmpVal, lngVal, usrVal);

            if (lvOCmuFslST == null || lvOCmuFslST.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCmuFslST;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "FSL", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

}
