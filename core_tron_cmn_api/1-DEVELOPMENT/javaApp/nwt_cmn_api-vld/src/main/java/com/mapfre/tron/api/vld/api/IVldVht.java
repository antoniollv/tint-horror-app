package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The validation interface.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:33:29
 *
 */
public interface IVldVht {

    /**
     * Validate whether the value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param vldDat -> Fecha validez
     * @param vhtVal -> Codigo tipo vehiculo
     * 
     * @return       -> An error if the third level value does not exit
     */
    OTrnErrS vldVht(BigDecimal cmpVal, String lngVal, BigDecimal vhtVal, Date vldDat);

}
