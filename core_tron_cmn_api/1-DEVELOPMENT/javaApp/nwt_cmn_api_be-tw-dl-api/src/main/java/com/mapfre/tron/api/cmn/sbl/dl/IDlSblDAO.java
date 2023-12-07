package com.mapfre.tron.api.cmn.sbl.dl;

import java.math.BigDecimal;

/**
 * The validation interface.
 *
 * @author architecture - AMINJOU
 * @since 1.8
 *
 */
public interface IDlSblDAO {

    /**
     * Validate whether the value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param sblVal -> 
     * 
     * @return       -> An error if the third level value does not exit
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal sblVal);

}
