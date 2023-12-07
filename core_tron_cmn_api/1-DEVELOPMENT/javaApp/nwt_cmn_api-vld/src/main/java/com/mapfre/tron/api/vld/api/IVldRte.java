package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The rate validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 19 Jul 2021 - 09:43:34
 *
 */
public interface IVldRte {

    /**
     * Validate whether the rate value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param rteVal -> Rate code
     * @return       -> An error if the rate value does not exit
     */
    OTrnErrS vldRteVal(BigDecimal cmpVal, String lngVal, BigDecimal rteVal);

}
