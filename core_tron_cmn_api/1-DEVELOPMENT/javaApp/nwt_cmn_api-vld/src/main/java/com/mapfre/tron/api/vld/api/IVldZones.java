package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The zones validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 5 Jul 2021 - 10:43:05
 *
 */
public interface IVldZones {

    /**
     * Validate whether postal code value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country value
     * @param pslCodVal -> Postal code
     * @param twnVal    -> Town Value
     *
     * @return          -> An error if the postal code value does not exit
     */
    OTrnErrS vldZoneFour(BigDecimal cmpVal, String lngVal, String cnyVal, BigDecimal pslCodVal, BigDecimal twnVal);

}
