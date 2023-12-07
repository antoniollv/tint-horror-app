package com.mapfre.tron.api.cmn.atz.dl;

import java.math.BigDecimal;

/**
 * The currency repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 08:27:13
 *
 */
public interface IDlAtzDAO {

    /**
     * Count how many rows exists for a specific authorization level code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param atzLvlVal -> Authorization level code
     * @return          -> Number of rows for a specific authorization level code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal atzLvlVal);

}
