package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The accounting validation interface.
 *
 * @author  majoguam
 * @since 1.8
 *
 */
public interface IVldDel {

    /**
     * Validate del_val value exists.
     *
     * @param cmpVal    	-> Company code
     * @param lngVal    	-> Language code
     * @param lobVal 		-> Line of Business
     * @param vldDat 		-> Validation Date
     * @param delVal 		-> 
     * @return          	-> An error if the Supplement value does not exit
     */
    OTrnErrS vldDelVal(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, Date vldDat, BigDecimal delVal);

}
