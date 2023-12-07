package com.mapfre.tron.api.cmn.rte.dl;

import java.math.BigDecimal;

/**
 * The rate repository interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 19 Jul 2021 - 09:50:40
 *
 */
public interface IDlRteDAO {

    /**
     * Count how many rows exists for a specific rate value.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param rteVal -> Rate code
     * @return       -> Number of rows for a specific rate value
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal rteVal);

}
