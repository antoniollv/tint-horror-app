package com.mapfre.tron.api.prt.sbs.bl;

import java.util.List;

import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;

/**
 * The IBlPrtSbsQry business logic service interface.
 *
 *
 * @author Cristian Saball
 * @version 30/08/2021
 *
 */
public interface IBlPrtSbsQry {

    /**
     * Query subsectors by company and sector value
     *
     * @author Cristian Saball
     * 
     * 
     * @param cmpVal -> company code
     * @param secVal -> Sector value
     * @param usrVal -> user value
     * @param lngVal -> language value
     * 
     * @return List<OPrtSbsS>
     * 
     */
    List<OPrtSbsS> sbsTbl(Integer cmpVal, Integer secVal, String lngVal, String usrVal);

}
