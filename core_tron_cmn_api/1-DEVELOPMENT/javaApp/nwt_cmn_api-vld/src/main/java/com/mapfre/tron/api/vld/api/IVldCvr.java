package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The coverage validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 8 Jul 2021 - 13:27:48
 *
 */
public interface IVldCvr {

    /**
     * Validate whether coverage value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param cvrVal -> Coverage code
     * @param vldDat -> Validation date
     * @param lobVal -> Line of Business
     * @param mdtVal -> Modality code
     * @return -> An error if the coverage value does not exit
     */
    OTrnErrS vldCvrVal(BigDecimal cmpVal, String lngVal, BigDecimal cvrVal, Date vldDat, BigDecimal lobVal,
            String mdtVal);

}
