package com.mapfre.tron.api.cmn.acg.dl;

import java.math.BigDecimal;

/**
 * The account repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 12 Jul 2021 - 08:52:30
 *
 */
public interface IDlAcgDAO {

    /**
     * Count how many rows exists for a specific account business line code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param acgLobVal -> Accounting line of business code
     * @return          -> Number of rows for a specific account business line code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal acgLobVal);

}
