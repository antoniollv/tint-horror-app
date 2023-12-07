package com.mapfre.tron.api.cmu.thl.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;

/**
 * The Cmu third level service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Oct 2021 - 09:45:48
 *
 */
public interface IBlCmuThl {

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
    List<OCmuThlS> getThirdLevels(Integer cmpVal, String lngVal, String usrVal, BigDecimal frsLvlVal,
            BigDecimal scnLvlVal);

}
