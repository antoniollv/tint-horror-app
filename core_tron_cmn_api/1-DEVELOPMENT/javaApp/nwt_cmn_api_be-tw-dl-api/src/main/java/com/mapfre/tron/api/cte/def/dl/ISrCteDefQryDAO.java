package com.mapfre.tron.api.cte.def.dl;

import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrCPT;
import com.mapfre.tron.api.cmn.model.InConstantInformation;

/**
 * Query constants definition
 *
 * @author magarafr
 * @since 1.8
 * @version 20 ene. 2021 13:08:36
 *
 */
public interface ISrCteDefQryDAO {

    /**
     * Query constants definition. It will return the list.
     *
     * @author magarafr
     *
     * @return OPlyAtrCPT
     */
    OPlyAtrCPT xxCnn(InConstantInformation inConstantInformation, String strFldNam);
}
