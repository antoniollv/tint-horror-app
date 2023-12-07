package com.mapfre.tron.api.cmn.sec.dl;

import java.math.BigDecimal;

/**
 * The Sector respository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 12 Jul 2021 - 12:17:39
 *
 */
public interface IDlSecDAO {

    /**
     * Count how many rows exists for a specific sector code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param secVal -> Accounting line of business code
     * @return       -> Number of rows for a specific sector code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal secVal);

}
