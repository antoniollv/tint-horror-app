package com.mapfre.tron.api.dsr.sdc.bl;

import java.util.List;

import com.mapfre.nwt.trn.dsr.sdc.bo.ODsrSdcS;

/**
 * The IBlDsrSdcQry business logic service interface.
 *
 *
 * @author Cristian Saball
 * @version 19/08/2021
 *
 */
public interface IBlDsrSdcQry {

	
    /**
     * Second Level Distribution Channel by company and first level
     *
     * @author Cristian S.
     * 
     * 
     * @param cmpVal       -> company code
     * @param frsDstHnlVal -> first Channel Distribution
     * @param usrVal       -> user value
     * @param lngVal       -> language value
     * 
     * @return List<ODsrSdcS>
     * 
     */
    List<ODsrSdcS> tblByCmpFrsLvl(Integer cmpVal, String frsDstHnlVal, String usrVal, String lngVal);



}
