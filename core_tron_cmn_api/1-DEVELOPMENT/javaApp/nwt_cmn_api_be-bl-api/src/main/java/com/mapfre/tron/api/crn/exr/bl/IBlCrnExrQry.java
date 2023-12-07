package com.mapfre.tron.api.crn.exr.bl;

import java.math.BigDecimal;

import com.mapfre.nwt.trn.crn.exr.bo.OCrnExrP;


/**
 * The CrnExrQry business logic service interface.
 *
 * @author SSROBE1
 * @since 1.0
 * @version 22 abr. 2020 12:24:08
 *
 */
public interface IBlCrnExrQry {

    
    /**
     * fld : CMN-1673 - OFRECER CONSULTAR tipo cambio por fecha cambio (<=)
     * 
     * @author pvraul1
     * 
     * @param  lngVal   -> Language code
     * @param  usrVal   -> User code
     * @param  crnVal   -> Currency
     * @param  exrDat   -> Exchange rate date
     * @return          -> Type list
     */
    OCrnExrP chnDat(String lngVal, String usrVal, Integer crnVal, Long exrDat, BigDecimal cmpVal);

}
