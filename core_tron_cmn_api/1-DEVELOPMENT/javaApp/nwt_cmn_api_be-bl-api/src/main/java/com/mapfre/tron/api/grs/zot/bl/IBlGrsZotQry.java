package com.mapfre.tron.api.grs.zot.bl;

import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotCPT;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;

public interface IBlGrsZotQry {
    
    /**
     * fld : ITG-CMN-29 - OFRECER CONSULTAR zona tres por zona uno, zona dos conjunto
     *
     * @author BRCHARL
     * @purpose Servicio de obtención de provincias
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param sttVal -> State Value
     * @return       -> Type
     */
    OGrsZotCPT zonZntSet(Integer cmpVal, String usrVal, String lngVal, String cnyVal, Integer sttVal);


    
    /**
     * fld : ITG-CMN-40 - OFRECER CONSULTAR zona tres
     *
     * @author BRCHARL
     * @purpose Servicio de obtención de provincia
     *
     * @param cmpVal -> Company code
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param pvcVal -> Province Value        
     * @return       -> Type
     */
    OGrsZotS cnyPvc(Integer cmpVal, String usrVal, String lngVal, String cnyVal, Integer pvcVal);

    
}