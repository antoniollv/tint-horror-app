package com.mapfre.tron.api.lvl.cnt.bl;

import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;

/**
 * The common cmn business service interface.
 *
 * @author magarafr
 * @since 1.0.0
 * @version 09 feb. 2021 14:54:18
 *
 */
public interface IBlFrsLvlCnt {

    /**
     * Query Contact From First Level
     *
     * @author magarafr
     * @purpose Query the first level contact information. It will return the
     *          contact of the first level of the commercial structure . It will be
     *          mandatory send the parameters defined in the service and the service
     *          will response with the output object defined.
     *
     * @param cmpVal    -> Company code
     * @param frsLvlVal -> First Level
     * @param lngVal    -> Language
     * @return -> OThpCntS
     */
    OThpCntS frsLvlCnt(Integer cmpVal, Integer frsLvlVal, String lngVal);
}
