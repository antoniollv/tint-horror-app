package com.mapfre.tron.api.tcd.tce.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.tcd.tce.bo.OTcdTceS;

/**
 * The TcdTce repository interface.
 *
 * @author Javier Sangil
 * @since 1.8
 *
 */
public interface IDlTcdTceDAO {

    
    /**
     * Query Technical Control Definition
     *
     * @author Javier Sangil
     * 
     * 
     * @param cmpVal -> company code
     * @param usrVal -> user value
     * @param lngVal -> language value
     * @param errValList -> Error List Value
     * 
     * @return List<OTcdTceS>
     * 
     */
    List<OTcdTceS> getTechnicalControlDefinition(BigDecimal pmCmpVal, String usrVal, String lngVal,
	    List<String> errValList);

}
