package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The state validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 9 Jul 2021 - 09:33:29
 *
 */
public interface IVldStt {

    /**
     * Validate whether state value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param cnyVal -> Country code
     * @param sttVal -> State code
     * @return       -> An error if the third level value does not exit
     */
    OTrnErrS vldStt(BigDecimal cmpVal, String lngVal, String cnyVal, BigDecimal sttVal);

}
