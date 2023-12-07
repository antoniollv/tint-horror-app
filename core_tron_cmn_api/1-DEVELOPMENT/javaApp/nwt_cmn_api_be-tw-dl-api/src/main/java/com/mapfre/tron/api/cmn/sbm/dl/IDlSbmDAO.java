package com.mapfre.tron.api.cmn.sbm.dl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The state repository.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:38:34
 *
 */
public interface IDlSbmDAO {

    /**
     * Count how many rows exists for a specific value.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param vldDat -> Fecha validez
     * @param makVal -> Codigo marca
     * @param mdlVal -> Codigo modelo
     * @param sbmVal -> Codigo submodelo
     * 
     * @return       -> Number of rows for a specific value
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal makVal, BigDecimal mdlVal, BigDecimal sbmVal, Date vldDat);

}
