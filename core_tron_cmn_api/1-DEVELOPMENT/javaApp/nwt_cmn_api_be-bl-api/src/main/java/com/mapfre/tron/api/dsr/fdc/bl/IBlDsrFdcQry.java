/**
 * 
 */
package com.mapfre.tron.api.dsr.fdc.bl;

import java.util.List;

import com.mapfre.nwt.trn.dsr.fdc.bo.ODsrFdcS;

/**
 * The IBlCmnFdcQry business logic service interface.
 *
 *
 * @author Javier Sangil
 * @version 12/08/2021
 *
 */
public interface IBlDsrFdcQry {

    /**
     * Query First Level Distribution Channel by company
     *
     * @author Javier S.
     * 
     * @purpose Query First Level Distribution Channel by company
     *          
     * @param cmpVal -> company code
     * @param usrVal -> user value
     * @param lngVal -> language value
     * 
     * @return List<ODsrFdcS>
     * 
     */
    List<ODsrFdcS> tblByCmp(Integer cmpVal, String usrVal, String lngVal);

    /**
     * First Level Distribution Channel by Company.
     *
     * @param cmpVal       -> Company code
     * @param lngVal       -> Language value
     * @param usrVal       -> User value
     * @param frsDstHnlVal -> First level distribution channel
     * @return             -> The first level distribution channel by company
     */
    ODsrFdcS tblByCmpById(Integer cmpVal, String lngVal, String usrVal, String frsDstHnlVal);

}
