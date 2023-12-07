package com.mapfre.tron.api.cmn.mak.dl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The state repository.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 9 Jul 2021 - 09:38:34
 *
 */
public interface IDlMakDAO {

    /**
     * Count how many rows exists for a specific state code.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param vldDat -> Fecha validez
     * @param makVal -> Codigo marca
     * @param mdlVal -> Codigo modelo
     * 
     * @return       -> Number of rows for a specific state code
     */
    int count(BigDecimal cmpVal, String lngVal, Date vldDat, BigDecimal makVal);

}
