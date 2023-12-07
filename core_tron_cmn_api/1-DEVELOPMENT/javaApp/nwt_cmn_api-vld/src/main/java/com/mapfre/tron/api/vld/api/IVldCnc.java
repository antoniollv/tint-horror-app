package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The concept validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 14 Jul 2021 - 09:52:01
 *
 */
public interface IVldCnc {

    /**
     * Validate whether the reserve concept value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param rsvCncVal -> Reserve concept code
     * @return          -> An error if the reserve concept value does not exit
     */
    OTrnErrS vldRsvCncVal(BigDecimal cmpVal, String lngVal, BigDecimal rsvCncVal);

}
