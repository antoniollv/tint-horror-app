package com.mapfre.tron.api.cmn.lob.dl;

import java.math.BigDecimal;

/**
 * The ramo repository.
 *
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 16 Jul 2021 - 09:33:29
 *
 */
public interface IDlLobDAO {

    /**
     * Count how many rows exists
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Ramo 
     * 
     * @return       -> Number of rows for a specific state code
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal lobVal);

}
