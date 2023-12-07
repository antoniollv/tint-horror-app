package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The third level validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 10:23:15
 *
 */
public interface IVldThr {

    /**
     * Validate whether third level value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param thrLvlVal -> Third level code
     * @return          -> An error if the third level value does not exit
     */
    OTrnErrS vldThrLvl(BigDecimal cmpVal, String lngVal, BigDecimal thrLvlVal);

}
