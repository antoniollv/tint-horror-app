package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The country validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 7 Jul 2021 - 13:17:45
 *
 */
public interface IVldCny {

    /**
     * Validate whether country value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param cnyVal -> Country code
     * @return       -> An error if the country value does not exit
     */
    OTrnErrS vldThrLvl(BigDecimal cmpVal, String lngVal, String cnyVal);

}
