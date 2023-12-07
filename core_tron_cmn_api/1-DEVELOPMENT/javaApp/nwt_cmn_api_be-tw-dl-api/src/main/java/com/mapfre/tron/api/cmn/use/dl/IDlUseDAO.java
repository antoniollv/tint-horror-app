package com.mapfre.tron.api.cmn.use.dl;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:38:34
 *
 */
public interface IDlUseDAO {

    /**
     * Count how many rows exists for a specific value.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param vldDat -> Fecha validez
     * @param vhcUseVal -> Codigo tipo vehiculo
     * 
     * @return       -> Number of rows for a specific value
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal vhcUseVal, Date vldDat);

}
