package com.mapfre.tron.api.ard.aby.bl;

import java.util.List;

import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvS;

/**
 * The SrArdAbvQry business service interface.
 *
 * @since 1.8
 * @version 26 jun 2020
 *
 */
public interface IBlSrArdAbvQry {

    /**
     * Query Attribute Values
     * 
     * @param lngVal -> Language code
     * @param usrVal -> User
     * @param cmpVal -> Company
     * @param fldNam -> Field code
     * @param lobVal -> Line of Business
     * @param mdtVal -> Modality
     * @return -> List<OArdAbvS>
     */
    List<OArdAbvS> tbl(String lngVal, String usrVal, Integer cmpVal, String fldNam, Integer lobVal, Integer mdtVal);

}
