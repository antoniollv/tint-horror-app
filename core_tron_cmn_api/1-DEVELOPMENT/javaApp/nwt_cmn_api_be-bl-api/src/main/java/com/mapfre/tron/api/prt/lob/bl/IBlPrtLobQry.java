
package com.mapfre.tron.api.prt.lob.bl;

import java.util.List;

import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobCPT;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobP;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;

/**
 * The thirdparty cmn business service interface.
 *
 * @author BRCHARL
 * @since 1.0.0
 * @version 21 oct. 2019 16:07:41
 *
 */
public interface IBlPrtLobQry {

    /**
     * fld : ITG-CMN-44 - OFRECER CONSULTAR ramo emision conjunto
     *
     * @author BRCHARL
     * @purpose Servicio de obtención de tipos de póliza
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return       -> Type
     */
    OPrtLobCPT isuTbl(Integer cmpVal, String usrVal, String lngVal);

    /**
     * fld : CMN-2667 - OFRECER CONSULTAR ramo fecha validez ramo a fecha (<=)
     * 
     * @propose Query line of business validation date
     * @author BRCHARL
     * 
     * @param  lngVal   -> Language code
     * @param  cmpVal   -> Company code
     * @param  usrVal   -> User code
     * @param  lobVal   -> Line of Business
     * @param  vldDat   -> Validation Date
     * @return OPrtLobP -> Line of Business
     */
    OPrtLobP get(String lngVal, Integer cmpVal, String usrVal, Integer lobVal, Long vldDat);

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
