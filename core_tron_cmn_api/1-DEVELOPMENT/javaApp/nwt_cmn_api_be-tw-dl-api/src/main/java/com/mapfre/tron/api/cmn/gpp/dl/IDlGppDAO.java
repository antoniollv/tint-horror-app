package com.mapfre.tron.api.cmn.gpp.dl;

import java.math.BigDecimal;

/**
 * The account repository.
 *
 * @author  majoguam
 * @since 1.8
 *
 */
public interface IDlGppDAO {
    
	/**
     * Validate del_val value exists.
     *
     * @param cmpVal    	-> Company code
     * @param lngVal    	-> Language code
     * @param gppVal 		-> 
     * @return          	-> An error if the Supplement value does not exit
     */
    int count(BigDecimal cmpVal, String lngVal, String gppVal);

}
