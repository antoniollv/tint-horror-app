package com.mapfre.tron.sfv.bl;

import java.math.BigDecimal;

import com.mapfre.tron.sfv.bo.SfvIn;
import com.mapfre.tron.sfv.bo.SfvOut;

/**
 * The SfvInu business logic interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 8 May 2023 - 10:53:54
 *
 */
public interface IBlSfvInuQry {

    /**
     * Validate information sended from frontal.
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User value
     * @param lngVal -> Language code
     * @param svfIn  -> Input structure data (agent, channel ...)
     * @return       -> Output structure data
     */
    SfvOut postValidateStep(BigDecimal cmpVal, String usrVal, String lngVal, SfvIn sfvIn);

}
