
package com.mapfre.tron.api.grs.zno.bl;

import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoCPT;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;

/**
 * The common cmn business service interface.
 *
 * @author BRCHARL
 * @since 1.0.0
 * @version 21 oct. 2019 11:07:41
 * 
 */
public interface IBlGrsZnoQry {

    /**
     * fld : ITG-CMN-46 - OFRECER CONSULTAR zona uno
     *
     * @author BRCHARL
     * @purpose Servicio de obtención de pais
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @return       -> Type
     */
    public OGrsZnoS cny(Integer cmpVal, String usrVal, String lngVal, String cnyVal);

    /**
     * fld : ITG-CMN-48 - OFRECER CONSULTAR zona uno conjunto
     *
     * @author BRCHARL
     * @purpose Servicio de obtención de países
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param sttVal -> State  
     * @return       -> Type
     */
    public OGrsZnoCPT set(Integer cmpVal, String usrVal, String lngVal);

}