package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The accounting validation interface.
 *
 * @author  majoguam
 * @since 1.8
 *
 */
public interface IVldGpp {

    /**
     * Validate Supplement value exists.
     *
     * @param cmpVal    	-> Company code
     * @param lngVal    	-> Language code
     * @param enrVal 		-> Endorsement code
     * @param enrSbdVal 	-> Endorsement subcode
     * @param enrScoTypVal 	-> Endorsement scope type value
     * @return          	-> An error if the Supplement value does not exit
     */
    OTrnErrS vldGppVal(BigDecimal cmpVal, String lngVal, String gppVal);

}
