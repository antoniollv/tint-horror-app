package com.mapfre.tron.api.grs.zof.bl;

import java.util.List;

import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;


/**
 * The SrGrsZofQry business service interface.
 *
 * @since 1.8
 * @version 30 jun 2020
 *
 */
public interface IBlGrsZofQry {

    
    /**
     * Query zone five list
     * 
     * @param cmpVal -> Company code* 
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param cnyVal -> Country
     * @param sttVal -> State 
     * @param pvcVal -> Province Value
     * @param twnVal -> Town Value
     * @param reaPsc -> Real Postal Code
     * @return -> List<OGrsZofS>
     */
    List<OGrsZofS> tbl(Integer cmpVal,
                       String usrVal,
                       String lngVal,
                       String cnyVal,
                       Integer sttVal,
                       Integer pvcVal,
                       Integer twnVal,
                       String reaPsc);

    
    /**
     * Query zone five list. It will return the list of zone five (postalCode).  
     * It will be mandatory send the parameters defined in the service and the 
     * service will response with the output object defined.
     * 
     * @param usrVal
     * @param lngVal
     * @param pslCodVal
     * @param reaPsc
     * 
     * @return List<OGrsZofS>
     * 
     */
	List<OGrsZofS> get(String pslCodVal, String usrVal, String lngVal, String reaPsc);

}
