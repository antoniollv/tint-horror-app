package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The company validation.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 13:03:52
 *
 */
public interface IVldCmp {

    /**
     * Validate whether the company value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @return       -> An error if the company value does not exit
     */
    OTrnErrS vldCmpVal(BigDecimal cmpVal, String lngVal);

}
