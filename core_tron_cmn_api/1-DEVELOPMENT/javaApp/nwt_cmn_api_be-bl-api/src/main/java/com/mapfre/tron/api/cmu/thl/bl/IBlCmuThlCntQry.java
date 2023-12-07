package com.mapfre.tron.api.cmu.thl.bl;

import java.util.List;

import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntP;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;

public interface IBlCmuThlCntQry {
    
    /**
     * Query Contact From Third Level. It will return the contact of the third level of the commercial structure
     *
     * @author Cristian Saball
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param thrLvlVal -> Third Level
     * @return oThpCntS -> The contact of the third level of the commercial structure
     */
    
    OThpCntP get(Integer cmpVal,
                 String lngVal,
                 String usrVal,
                 Integer thrLvlVal);

    /**
     * Query the third level contact information.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param thrLvlVal -> Third Level
     * @return          -> It will return the contact of the third level of the commercial structure
     */
    List<OThpCntS> getContactFromThirdLevelv1(Integer cmpVal, String lngVal, String usrVal, Integer thrLvlVal);

    /**
     * Query Address From Third Level 1.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param thrLvlVal -> Third Level
     * @return          -> It will return the address of the third level of the commercial structure.
     */
    List<OThpAdrS> getAddresstFromThirdLevelv1(Integer cmpVal, String lngVal, String usrVal, Integer thrLvlVal);

}
