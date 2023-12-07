package com.mapfre.tron.api.cmn.lng.bl;

import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngCPT;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;

/**
 * The CmnLngQry business logic service interface.
 *
 * @author SSROBE1
 * @since 1.0
 * @version 24 mar. 2020 16:53:08
 *
 */
public interface IBlCmnLngQry {

    /**
     * 
     * @prc CMN-2664 - OFRECER CONSULTAR idioma conjunto
     * @purpose Servicio que obtiene los idiomas definidos.
     * 
     * @author SSROBE1
     * 
     * @param lngVal
     * @param usrVal
     * @return -> Tipo Conjunto
     */
    OCmnLngCPT getTbl(String lngVal, String usrVal);

    /**
     * Query language. It will return the language
     *
     * @author Cristian Saball
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @return oCmnLngS -> The type of language
     */
    OCmnLngS getLng(String lngVal, String usrVal);

    
}
