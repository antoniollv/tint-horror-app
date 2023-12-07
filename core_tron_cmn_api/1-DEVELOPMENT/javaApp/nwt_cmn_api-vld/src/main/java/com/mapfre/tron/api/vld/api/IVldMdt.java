package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The modality validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 9 Jul 2021 - 11:49:01
 *
 */
public interface IVldMdt {

    /**
     * Validate whether modality value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param mdtVal -> Modality code
     * @return -> An error if the modality value does not exit
     */
    OTrnErrS vldMdtVal(BigDecimal cmpVal, String lngVal, String mdtVal);

}
