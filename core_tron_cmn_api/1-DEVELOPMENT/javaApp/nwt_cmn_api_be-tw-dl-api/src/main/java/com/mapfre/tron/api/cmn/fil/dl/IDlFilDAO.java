package com.mapfre.tron.api.cmn.fil.dl;

import java.math.BigDecimal;

/**
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 26 Jul 2021 - 09:38:34
 *
 */
public interface IDlFilDAO {

    /**
     * Count how many rows exists for a specific value.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Ramo
     * @param valVal -> Valor
     * 
     * @return       -> Number of rows for a specific value
     */
    int count(BigDecimal cmpVal, String lngVal, String filTypVal);

}
