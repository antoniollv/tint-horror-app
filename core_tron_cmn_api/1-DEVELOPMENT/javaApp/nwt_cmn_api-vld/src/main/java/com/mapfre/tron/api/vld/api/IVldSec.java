package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The Sector validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 12 Jul 2021 - 11:53:45
 *
 */
public interface IVldSec {

    /**
     * Validate whether the sector value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param secVal -> Sector code
     * @return       -> An error if the sector value does not exit
     */
    OTrnErrS vldSecVal(BigDecimal cmpVal, String lngVal, BigDecimal secVal);

}
