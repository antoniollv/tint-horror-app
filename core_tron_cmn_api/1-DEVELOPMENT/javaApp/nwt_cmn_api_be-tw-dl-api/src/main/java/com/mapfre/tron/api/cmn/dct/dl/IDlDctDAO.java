package com.mapfre.tron.api.cmn.dct.dl;

import java.math.BigDecimal;

/**
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 27 Jul 2021 - 09:38:34
 *
 */
public interface IDlDctDAO {

    /**
     * Count how many rows exists for a specific value.
     * 
     * @return       -> Number of rows for a specific value
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal dctVal, BigDecimal crnVal, BigDecimal dctTypVal);

}
