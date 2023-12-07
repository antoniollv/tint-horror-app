package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The clause validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 8 Jul 2021 - 08:27:51
 *
 */
public interface IVldCla {

    /**
     * Validate whether clause value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param claVal -> Clause code
     * @param vldDat -> Validation date
     * @return       -> An error if the clause value does not exit
     */
    OTrnErrS vldClaVal(BigDecimal cmpVal, String lngVal, String claVal, Date vldDat);

}
