package com.mapfre.tron.api.vld.api;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;

/**
 * The validation interface.
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 19 Jul 2021 - 09:33:29
 *
 */
public interface IVldTyp {

    /**
     * Validate whether the value exists.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Ramo
     * @param valVal -> Valor
     * 
     * @return       -> An error if the third level value does not exit
     */
    OTrnErrS vldTyp(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, String valVal);
    
    
    
    /**
     * Validate type document  exists.
     *
     * @param cmpVal 		-> Company code
     * @param lngVal 		-> Language code
     * @param thpDcmTypVal 	-> third party type document value
     * 
     * @return       		-> An error if the third level value does not exit
     */
    OTrnErrS vldTypDoc(BigDecimal cmpVal, String lngVal, String thpDcmTypVal);
    
    
    
    /**
     * Validate Endorsement exists.
     *
     * @param cmpVal 		-> Company code
     * @param lngVal 		-> Language code
     * @param lobVal 		-> cod ramo
     * @param valVal 		-> value
     * 
     * @return       		-> An error if the third level value does not exit
     */
    OTrnErrS vldEnrTypVal(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, String valVal);


}
