package com.mapfre.tron.api.cmn.cnc.dl;

import java.math.BigDecimal;

/**
 * The concept repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 14 Jul 2021 - 11:26:38
 *
 */
public interface IDlCncDAO {

    /**
     * Count how many rows exists for a specific reserve concept code.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param rsvCncVal -> Reserve concept code
     * @return          -> Number of rows for a specific reserve concept code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal rsvCncVal);

}
