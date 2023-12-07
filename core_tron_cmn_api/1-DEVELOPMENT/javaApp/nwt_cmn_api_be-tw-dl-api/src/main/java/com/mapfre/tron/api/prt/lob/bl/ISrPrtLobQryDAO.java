package com.mapfre.tron.api.prt.lob.bl;

import java.util.List;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;

public interface ISrPrtLobQryDAO {

    /**
     * Query line of business by deals.
     *
     * @author Cristian Saball
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param delVal -> Deal value
     * @param vldDat -> Validation Date
     *
     * @return -> The list of line of business by deals
     */
    List<OPrtLobS> getLineOfBusinessByDeal(String lngVal, Integer cmpVal, String usrVal, String delVal, Long vldDat);

}
