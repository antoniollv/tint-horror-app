package com.mapfre.tron.api.cmn.hnl.dl;

import java.math.BigDecimal;

/**
 * The channel repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 09:33:18
 *
 */
public interface IDlHnlDAO {

    /**
     * Count how many rows exists for a specific third distribution channel code.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param thdDstHnlVal -> Third distribution channel code
     * @return             -> Number of rows for a specific third distribution channel code
     */
    int count(BigDecimal cmpVal, String lngVal, String thdDstHnlVal);

}
