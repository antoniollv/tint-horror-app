package com.mapfre.tron.api.cmn.brw.dl;

import java.math.BigDecimal;
import java.util.Date;

/**
*
* @author Javier Sangil
* @since 1.8
* @version 15 Jul 2021
*
*/
public interface IDlBrwDAO {

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
    int count(BigDecimal cmpVal, String lngVal, Date vldDat, BigDecimal mdtVal, BigDecimal lobVal, BigDecimal ecmBrwCncVal, BigDecimal cvrVal);

}
