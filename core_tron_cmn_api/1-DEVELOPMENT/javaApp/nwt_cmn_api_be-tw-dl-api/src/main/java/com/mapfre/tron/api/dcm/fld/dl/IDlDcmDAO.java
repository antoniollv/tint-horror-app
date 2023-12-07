package com.mapfre.tron.api.dcm.fld.dl;

import java.math.BigDecimal;

/**
 * The documentum repository interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 16 Jul 2021 - 09:55:29
 *
 */
public interface IDlDcmDAO {

    /**
     * Count how many rows exists for a specific third person document number.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param thpDcmVal    -> Third person documentum number
     * @param thpDcmTypVal -> Third person documentum type
     * @return       -> Number of rows for a specific third person document number
     */
    int count(BigDecimal cmpVal, String lngVal, String thpDcmVal, String thpDcmTypVal);

}
