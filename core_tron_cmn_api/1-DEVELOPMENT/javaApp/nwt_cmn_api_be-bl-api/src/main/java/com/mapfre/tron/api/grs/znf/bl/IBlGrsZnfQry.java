

package com.mapfre.tron.api.grs.znf.bl;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfCPT;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;


/**
 * The thirdparty cmn business service interface.
 *
 * @author BRCHARL
 * @since 1.0.0
 * @version 21 oct. 2019 14:54:18
 *
 */
public interface IBlGrsZnfQry {

    /**
     * fld : ITG-CMN-13 - OFRECER CONSULTAR zona cuatro conjunto
     *
     * @author BRCHARL
     * @purpose Servicio de obtención de ciudades
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param pvcVal -> Province Value
     * @return       -> Type
     */
    OGrsZnfCPT tbl(Integer cmpVal, String usrVal, String lngVal, String cnyVal, Integer pvcVal);

    
    /**
     * fld : ITG-CMN-38 - OFRECER CONSULTAR zona cuatro
     *
     * @author BRCHARL
     * @purpose Servicio de obtención de ciudad
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country code
     * @param pvcVal -> Province code          
     * @param twnVal -> Town code            
     * @return       -> Type
     */
    OGrsZnfS cnyPvcTwn(Integer cmpVal, String usrVal, String lngVal, String cnyVal, Integer pvcVal, Integer twnVal);

}

