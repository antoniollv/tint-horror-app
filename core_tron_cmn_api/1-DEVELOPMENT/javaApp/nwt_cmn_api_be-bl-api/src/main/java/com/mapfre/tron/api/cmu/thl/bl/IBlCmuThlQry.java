package com.mapfre.tron.api.cmu.thl.bl;


import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlP;

/**
 * The CmuThlQry business logic service interface.
 *
 * @author USOZALO
 * @since 1.0
 * @version 04 jun. 2020 16:53:08
 *
 */
public interface IBlCmuThlQry {

    /**
     * 
     * @prc CMN-1334 - OFRECER CONSULTAR tercer nivel

     * @author USOZALO
     * 
     * @param cmpVal
     * @param lngVal
     * @param usrVal
     * @param thrLvlVal
     * @return -> OCmuThlP
     */
    
    OCmuThlP get(Integer cmpVal,
                 String lngVal,
                 String usrVal,
                 Integer thrLvlVal);

    
    
    
}
