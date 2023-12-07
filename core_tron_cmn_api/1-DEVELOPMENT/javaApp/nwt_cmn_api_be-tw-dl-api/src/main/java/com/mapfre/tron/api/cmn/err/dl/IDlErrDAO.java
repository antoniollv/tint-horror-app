package com.mapfre.tron.api.cmn.err.dl;

import java.math.BigDecimal;

/**
 * The error repository interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 19 Jul 2021 - 08:50:14
 *
 */
public interface IDlErrDAO {

    /**
     * Count how many rows exists for a specific error value.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param errVal -> Error code
     * @return       -> Number of rows for a specific error value
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal errVal);

}
