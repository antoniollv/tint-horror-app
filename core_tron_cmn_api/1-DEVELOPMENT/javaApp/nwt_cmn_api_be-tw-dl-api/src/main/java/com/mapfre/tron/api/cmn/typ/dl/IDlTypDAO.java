package com.mapfre.tron.api.cmn.typ.dl;

import java.math.BigDecimal;

/**
 *
 * @author architecture - Javier Sangil
 * @since 1.8
 * @version 26 Jul 2021 - 09:38:34
 *
 */
public interface IDlTypDAO {

    /**
     * Count how many rows exists for a specific value.
     *
     * @param cmpVal -> Company code
     * @param lngVal -> Language code
     * @param lobVal -> Ramo
     * @param valVal -> Valor
     * 
     * @return       -> Number of rows for a specific value
     */
    int count(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, String valVal);

    
    
    /**
     * Validate type document  exists.
     *
     * @param cmpVal 		-> Company code
     * @param lngVal 		-> Language code
     * @param thpDcmTypVal 	-> third party type document value
     * 
     * @return       		-> An error if the third level value does not exit
     */
	int countDoc(BigDecimal cmpVal, String lngVal, String thpDcmTypVal);


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
	int countEnr(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, String valVal);

}
