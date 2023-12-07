package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The validation interface.
 *
 * @author architecture - AMINJOU
 * @since 1.8
 *
 */
public interface IVldSbl {

    /**
     * Validate whether the value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param sblVal -> 
     * 
     * @return       -> An error if the third level value does not exit
     */
    OTrnErrS vldSblVal(BigDecimal cmpVal, String lngVal, BigDecimal sblVal);

}
