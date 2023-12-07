
package com.mapfre.tron.api.cmn.col.bl;

import com.mapfre.nwt.trn.cmn.col.bo.OCmnColCPT;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColP;

/**
 * The cmn business service interface.
 *
 * @author BRCHARL
 * @since 1.0.0
 * @version 21 oct. 2019 11:07:41
 *
 */
public interface IBlCmnColQry {

    /**
     * fld : ITG-CMN-42 - OFRECER CONSULTAR color
     *
     * @author BRCHARL
     * @purpose Servicio de obtención de color
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param colVal -> colVal
     * @return       -> Type
     */
    OCmnColP col(Integer cmpVal, String usrVal, String lngVal, Integer cnyVal);

    /**
     * fld : ITG-CMN-52 - OFRECER CONSULTAR color conjunto
     *
     * @author BRCHARL
     * @purpose Servicio de obtención de colores
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param colVal -> colVal
     * @return       -> Type
     */
    OCmnColCPT set(Integer cmpVal, String usrVal, String lngVal);

}
