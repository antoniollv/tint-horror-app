package com.mapfre.tron.api.dsr.hdc.bl;

import java.util.List;

import com.mapfre.nwt.trn.dsr.hdc.bo.ODsrHdcS;

/**
 * The IBlDsrHdcQry business logic service interface.
 *
 *
 * @author Cristian Saball
 * @version 24/08/2021
 *
 */
public interface IBlDsrHdcQry {

	
    /**
     * Query Third Level Distribution Channel by company, first level and second
     * level.
     *
     * @author Cristian Saball
     * 
     * 
     * 
     * @param cmpVal       -> company code
     * @param frsDstHnlVal -> First Channel Distribution
     * @param scnDstHnlVal -> Second Channel Distribution
     * @param usrVal       -> user value
     * @param lngVal       -> language value
     * 
     * @return List<ODsrHdcS>
     * 
     */
    List<ODsrHdcS> tblByCmpFrsScnLvl(Integer cmpVal, String frsDstHnlVal, String scnDstHnlVal, String usrVal, String lngVal);

}
