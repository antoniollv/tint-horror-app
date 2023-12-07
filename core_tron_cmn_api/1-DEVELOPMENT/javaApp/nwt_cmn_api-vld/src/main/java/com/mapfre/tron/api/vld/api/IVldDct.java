package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The validation interface.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 27 Jul 2021 - 09:33:29
 *
 */
public interface IVldDct {

    /**
     * Validate whether the value exists.
     * 
     * @return       -> An error if the value does not exit
     */
    OTrnErrS vldDct(BigDecimal cmpVal, String lngVal, BigDecimal dctVal, BigDecimal crnVal, BigDecimal dctTypVal);

}
