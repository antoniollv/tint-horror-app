package com.mapfre.tron.api.cte.def.bl;

import java.util.List;

import com.mapfre.nwt.trn.ply.atr.bo.OPlyAtrS;
import com.mapfre.tron.api.cmn.model.InConstantInformation;

/**
 * Query constants definition
 *
 * @author magarafr
 * @since 1.8
 * @version 20 ene. 2021 12:41:47
 *
 */
public interface IBlCteDefQry {

    /**
     * Query constants definition
     *
     * @author magarafr
     * @param lngVal 
     *
     * @return List<OPlyAtrS>
     */
    List<OPlyAtrS> xxCnn(InConstantInformation inConstantInformation, String strFldNam, String lngVal);
}
