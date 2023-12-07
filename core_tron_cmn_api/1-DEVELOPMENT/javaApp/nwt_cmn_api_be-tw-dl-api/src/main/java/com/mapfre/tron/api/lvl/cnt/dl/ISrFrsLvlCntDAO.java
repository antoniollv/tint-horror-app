package com.mapfre.tron.api.lvl.cnt.dl;

import com.mapfre.nwt.trn.thp.adr.bo.OThpAdrS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;

/**
 * The FrsLvlCnt repository.
 *
 * @author magarafr
 * @since 1.8
 * @version 09 feb. 2021 12:58:31
 *
 */
public interface ISrFrsLvlCntDAO {

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
     * @return -> OThpCntS
     */
    OThpCntS frsLvlCnt(Integer cmpVal, Integer frsLvlVal);

	OThpAdrS get(Integer cmpVal, String lngVal, String usrVal, Integer frsLvlVal);
}
