package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
*
* @author Javier Sangil
* @since 1.8
* @version 15 Jul 2021
*
*/
public interface IVldBrw {

    /**
     * Validate whether state value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param vldDat -> Fecha validez
     * @param makVal -> Codigo marca
     * @param mdlVal -> Codigo modelo
     * @return       -> An error if the third level value does not exit
     */
    OTrnErrS vldBrw(BigDecimal cmpVal, String lngVal, Date vldDat, BigDecimal mdtVal, BigDecimal lobVal, BigDecimal ecmBrwCncVal, BigDecimal cvrVal);

}
