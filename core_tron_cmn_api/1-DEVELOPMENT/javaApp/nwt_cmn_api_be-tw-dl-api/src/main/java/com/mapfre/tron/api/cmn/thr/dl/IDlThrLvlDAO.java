package com.mapfre.tron.api.cmn.thr.dl;

import java.math.BigDecimal;

/**
 * The third level respository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 10:46:33
 *
 */
public interface IDlThrLvlDAO {

    /**
     * Count how many rows exists for a specific third level code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thrLvlVal -> Third level code
     * @return          -> Number of rows for a specific third level code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal thrLvlVal);

}
