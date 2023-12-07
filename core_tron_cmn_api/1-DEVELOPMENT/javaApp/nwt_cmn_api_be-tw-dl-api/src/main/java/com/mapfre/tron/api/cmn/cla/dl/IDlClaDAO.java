package com.mapfre.tron.api.cmn.cla.dl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The clause repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 8 Jul 2021 - 08:35:09
 *
 */
public interface IDlClaDAO {

    /**
     * Count how many rows exists for a specific clause code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param claVal -> Clause code
     * @param vldDat -> Validation date
     * @return       -> Number of rows for a specific clause code
     */
    int count(BigDecimal cmpVal, String lngVal, String claVal, Date vldDat);

}
