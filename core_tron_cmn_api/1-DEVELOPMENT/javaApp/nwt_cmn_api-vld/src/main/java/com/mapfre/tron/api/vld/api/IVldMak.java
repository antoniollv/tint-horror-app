package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The state validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 9 Jul 2021 - 09:33:29
 *
 */
public interface IVldMak {

    /**
     * Validate whether state value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param vldDat -> Fecha validez
     * @param makVal -> Codigo marca
     * 
     * @return       -> An error if the third level value does not exit
     */
    OTrnErrS vldMak(BigDecimal cmpVal, String lngVal, Date vldDat, BigDecimal makVal);

}
