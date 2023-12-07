package com.mapfre.tron.api.cmu.thl.bl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * The CmuThl service implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Oct 2021 - 14:05:05
 *
 */
@Slf4j
@Service
@EqualsAndHashCode(callSuper = false)
public class TwBlCmuThlImpl extends TwBlCmnBase implements IBlCmuThl {

    /**
     * Query Third Level List.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param frsLvlVal -> First Level
     * @param scnLvlVal -> Second Level
     * @return          -> The third levels list
     */
    @Override
    public List<OCmuThlS> getThirdLevels(final Integer cmpVal, final String lngVal, final String usrVal,
            final BigDecimal frsLvlVal, final BigDecimal scnLvlVal) {

        log.info("Tronweb business logic implementation getThirdLevels have been called...");

        // reseting session
        resetSession();

        try {
            List<OCmuThlS> lvOCmuThlST = cacheableAttribute.getThirdLevels(cmpVal, lngVal, usrVal, frsLvlVal, scnLvlVal);

            if (lvOCmuThlST == null || lvOCmuThlST.isEmpty()) {
                throw new EmptyResultDataAccessException(ERROR_CODE);
            }

            return lvOCmuThlST;

        } catch (IncorrectResultSizeDataAccessException e) {
            getErrorWithoutPrpIdn(lngVal, "THL", BigDecimal.valueOf(cmpVal));
        }

        return Collections.emptyList();
    }

}
