package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The date validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 20 Jul 2021 - 10:51:54
 *
 */
public interface IVldDat {

    /**
     * Validate whether validation date exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Business line code
     * @param cvrVal -> Coverage code
     * @param mdtVal -> Modality code
     * @param vldDat -> Validation date
     * @return       -> An error if the validation date does not exit
     */
    OTrnErrS vldDat(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, BigDecimal cvrVal, BigDecimal mdtVal,
            Date vldDat);

}
