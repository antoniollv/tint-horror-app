package com.mapfre.tron.api.cmn.cmp.dl;

import java.math.BigDecimal;

/**
 * The company repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 13:09:50
 *
 */
public interface IDlCmpDAO {

    /**
     * Count how many rows exists for a specific company code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @return       -> Number of rows for a specific company code
     */
    int count(BigDecimal cmpVal, String lngVal);

}
