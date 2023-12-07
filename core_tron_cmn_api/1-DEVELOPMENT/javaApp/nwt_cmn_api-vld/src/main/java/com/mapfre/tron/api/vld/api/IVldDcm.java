package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The documentum validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 16 Jul 2021 - 09:34:32
 *
 */
public interface IVldDcm {

    /**
     * Validate whether the third person documentum value exists.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param thpDcmVal    -> Third person documentum number
     * @param thpDcmTypVal -> Third person documentum type
     * @return             -> An error if the third person documentum value does not exit
     */
    OTrnErrS vldThpDcmVal(BigDecimal cmpVal, String lngVal, String thpDcmVal, String thpDcmTypVal);

}
