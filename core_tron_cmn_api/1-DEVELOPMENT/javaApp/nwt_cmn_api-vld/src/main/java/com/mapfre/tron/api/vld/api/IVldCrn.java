package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The currency validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 6 Jul 2021 - 13:02:20
 *
 */
public interface IVldCrn {

    /**
     * Validate whether currency value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param crnVal -> Currency code
     * @param vldDat -> Validation date
     * @param lobVal -> Line of Business
     * @return       -> An error if the currency value does not exit
     */
    OTrnErrS vldCrnVal(BigDecimal cmpVal, String lngVal, BigDecimal crnVal, Date vldDat, BigDecimal lobVal);

}
