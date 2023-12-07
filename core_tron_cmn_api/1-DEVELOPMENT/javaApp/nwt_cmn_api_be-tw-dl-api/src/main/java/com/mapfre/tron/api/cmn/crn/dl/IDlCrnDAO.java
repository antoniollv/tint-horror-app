package com.mapfre.tron.api.cmn.crn.dl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The currency repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 6 Jul 2021 - 13:35:24
 *
 */
public interface IDlCrnDAO {

    /**
     * Count how many rows exists for a specific currency code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param crnVal -> Currency code
     * @param vldDat -> Validation date
     * @param lobVal -> Line of Business
     * @return       -> Number of rows for a specific currency code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal crnVal, Date vldDat, BigDecimal lobVal);

}
