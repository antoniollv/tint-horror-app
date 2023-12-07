
package com.mapfre.tron.api.grs.znt.bl;

import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntCPT;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntP;

/**
 * The common cmn business service interface.
 *
 * @author BRCHARL
 * @since 1.0.0
 * @version 21 oct. 2019 14:54:18
 *
 */
public interface IBlGrsZntQry {

    /**
     * fld : ITG-CMN-2 - OFRECER CONSULTAR zona dos conjunto
     *
     * @author BRCHARL
     * @purpose Servicio de obtención de estados / comunidades autónomas
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @return       -> Type
     */
    OGrsZntCPT set(Integer cmpVal, String usrVal, String lngVal, String cnyVal);

    /**
     * fld : ITG-CMN-36 - OFRECER CONSULTAR zona 2
     *
     * @author BRCHARL
     * @purpose Servicio de obtención de estado / comunidad autónoma
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param sttVal -> State  
     * @return       -> Type
     */
    OGrsZntP cnyStt(Integer cmpVal, String usrVal, String lngVal, String cnyVal, Integer sttVal);

}
