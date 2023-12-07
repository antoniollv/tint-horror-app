package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The validation interface.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 16 Jul 2021 - 09:33:29
 *
 */
public interface IVldPvc {

    /**
     * Validate whether state value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param PvcVal -> Ramo 
     * 
     * @return       -> An error if the third level value does not exit
     */
    OTrnErrS vldPvc(BigDecimal cmpVal, String lngVal, BigDecimal pvcVal, String cnyVal);

}
