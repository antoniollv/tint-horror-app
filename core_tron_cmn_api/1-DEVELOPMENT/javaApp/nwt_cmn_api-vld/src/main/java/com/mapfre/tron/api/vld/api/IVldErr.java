package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The error validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 19 Jul 2021 - 08:36:09
 *
 */
public interface IVldErr {

        /**
         * Validate whether the error value exists.
         *
         * @param cmpVal -> Company code
         * @param lngVal -> Language code
         * @param errVal -> Error code
         * @return       -> An error if the error value does not exit
         */
        OTrnErrS vldErrVal(BigDecimal cmpVal, String lngVal, BigDecimal errVal);

}
