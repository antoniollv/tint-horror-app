package com.mapfre.tron.api.cmn.del.dl;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The account repository.
 *
 * @author  majoguam
 * @since 1.8
 *
 */
public interface IDlDelDAO {

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

    int count(BigDecimal cmpVal, String lngVal, BigDecimal lobVal, Date vldDat, BigDecimal delVal);

}
