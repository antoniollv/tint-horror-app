package com.mapfre.tron.api.cmn.vld.dl;

import java.math.BigDecimal;

/**
 * The Zones interface validation repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 5 Jul 2021 - 11:13:16
 *
 */
public interface IDlZonesDAO {

    /**
     * Count how many rows exists for a specific postal code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country value
     * @param pslCodVal -> Postal code
     * @param twnVal    -> Town Value
     * @return          -> Number of rows for a specific postal code
     */
    int count(BigDecimal cmpVal, String lngVal, String cnyVal, BigDecimal pslCodVal, BigDecimal twnVal);

}
