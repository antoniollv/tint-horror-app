package com.mapfre.tron.api.lvl.cnt.bl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmu.snl.bo.OCmuSnlS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;

/**
 * The common cmn business service interface.
 *
 * @author magarafr
 * @since 1.0.0
 * @version 09 feb. 2021 14:54:18
 *
 */
public interface IBlScnLvlCnt {

    /**
     * Query Contact From Second Level
     *
     * @author magarafr
     * @purpose Query the second level contact information. It will return the
     *          contact of the second level of the commercial structure . It will be
     *          mandatory send the parameters defined in the service and the service
     *          will response with the output object defined.
     *
     * @param cmpVal    -> Company code
     * @param scnLvlVal -> Second Level
     * @param lngVal 	-> Language Code
     * @return -> OThpCntS
     */
    OThpCntS scnLvlCnt(Integer cmpVal, Integer scnLvlVal, String lngVal);

    /**
     * Query Second Level List.
     *
     * @param cmpVal    -> Company code
     * @param lngVal    -> Language code
     * @param usrVal    -> User code
     * @param frsLvlVal -> First Level
     * @return          -> The second levels list
     */
    List<OCmuSnlS> scnLvlQry(Integer cmpVal, String lngVal, String usrVal, BigDecimal frsLvlVal);

}
