package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The channel validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 13 Jul 2021 - 09:27:02
 *
 */
public interface IVldHnl {

    /**
     * Validate whether the third distribution channel value exists.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language code
     * @param thdDstHnlVal -> Third distribution channel code
     * @return             -> An error if the third distribution channel value does not exit
     */
    OTrnErrS vldThrDstHnlVal(BigDecimal cmpVal, String lngVal, String thdDstHnlVal);

}
