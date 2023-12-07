package com.mapfre.tron.api.cmn.cny.dl;

import java.math.BigDecimal;

/**
 * The country repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 13:22:25
 *
 */
public interface IDlCnyDAO {

    /**
     * Count how many rows exists for a specific country code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param cnyVal -> Country code
     * @return       -> Number of rows for a specific country code
     */
    int count(BigDecimal cmpVal, String lngVal, String cnyVal);

}
