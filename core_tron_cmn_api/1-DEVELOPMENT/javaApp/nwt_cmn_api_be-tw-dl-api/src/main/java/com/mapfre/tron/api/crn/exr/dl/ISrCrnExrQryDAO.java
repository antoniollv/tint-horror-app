package com.mapfre.tron.api.crn.exr.dl;

import java.math.BigDecimal;
import java.util.Date;

import com.mapfre.nwt.trn.crn.exr.bo.OCrnExrP;

/**
 * The CrnExrQry repository.
 *
 * @author pvraul1
 * @since 1.8
 * @version 23 abril. 2020 13:08:36
 *
 */
public interface ISrCrnExrQryDAO {

    /**
     * Query currencies. It will return the list of currencies.
     *
     * @author pvraul1
     * @param cmpVal 
     *
     * @return Currency List
     */
    OCrnExrP chnDat(String lngVal, String usrVal, BigDecimal crnVal, Date exrDat, BigDecimal cmpVal);

}
