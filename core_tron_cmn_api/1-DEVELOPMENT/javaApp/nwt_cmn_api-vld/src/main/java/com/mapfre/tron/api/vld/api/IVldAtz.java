package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The authorization validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 08:18:43
 *
 */
public interface IVldAtz {

    /**
     * Validate whether authorization level value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param vldAtzLvl -> Authorization level code
     * @return          -> An error if the authorization level value does not exit
     */
    OTrnErrS vldAtzLvl(BigDecimal cmpVal, String lngVal, BigDecimal atzLvlVal);

}
