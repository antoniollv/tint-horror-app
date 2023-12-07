package com.mapfre.tron.api.lvl.cnt.dl;

import java.math.BigDecimal;
import java.util.List;

import com.mapfre.nwt.trn.cmu.snl.bo.OCmuSnlS;
import com.mapfre.nwt.trn.thp.cnt.bo.OThpCntS;

/**
 * The ScnLvlCnt repository.
 *
 * @author magarafr
 * @since 1.8
 * @version 09 feb. 2021 12:58:31
 *
 */
public interface ISrScnLvlCntDAO {

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
     * @return -> OThpCntS
     */
    OThpCntS scnLvlCnt(Integer cmpVal, Integer scnLvlVal);

}
