package com.mapfre.tron.api.cmn.stt.dl;

import java.math.BigDecimal;

/**
 * The state repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 9 Jul 2021 - 09:38:34
 *
 */
public interface IDlSttDAO {

    /**
     * Count how many rows exists for a specific state code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param cnyVal -> Country code
     * @param sttVal -> State code
     * @return       -> Number of rows for a specific state code
     */
    int count(BigDecimal cmpVal, String lngVal, String cnyVal, BigDecimal sttVal);

}
