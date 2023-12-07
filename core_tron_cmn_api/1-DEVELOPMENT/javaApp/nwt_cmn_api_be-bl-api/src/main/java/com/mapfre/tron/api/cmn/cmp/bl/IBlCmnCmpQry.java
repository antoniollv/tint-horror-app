
package com.mapfre.tron.api.cmn.cmp.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;

/**
 * The cmn business service interface.
 *
 * @author 
 * 
 * @version 
 *
 */
public interface IBlCmnCmpQry {

    /**
     *
     * @author 
     * @purpose 
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * 
     * @return       -> List<OCmuCmpS>
     */
    List<OCmuCmpS> getCompanies(String usrVal, String lngVal);


    OCmuCmpS getCompany(String usrVal, String lngVal, BigDecimal cmpVal);
}