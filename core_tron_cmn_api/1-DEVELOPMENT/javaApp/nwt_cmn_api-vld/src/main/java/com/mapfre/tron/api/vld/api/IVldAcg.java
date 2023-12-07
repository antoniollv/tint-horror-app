package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The accounting validation interface.
 *
 * @author architecture - pvraul1
 * @since 1.8
 * @version 12 Jul 2021 - 08:36:51
 *
 */
public interface IVldAcg {

    /**
     * Validate whether the accounting line of business value exists.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param acgLobVal -> Accounting line of business code
     * @return          -> An error if the accounting line of business value does not exit
     */
    OTrnErrS vldAcgLobVal(BigDecimal cmpVal, String lngVal, BigDecimal acgLobVal);

}
