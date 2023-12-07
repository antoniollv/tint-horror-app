/**
 * 
 */
package com.mapfre.tron.api.crn.crn.bl;


import java.util.List;

import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnP;
import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;

/**
 * The CrnCrnQry business logic service interface.
 *
 * @author pvraul1
 * @since 1.8
 * @version 17 oct. 2019 12:41:47
 *
 */
public interface IBlCrnCrnQry {

    /**
     * Query currencies. It will return the list of currencies.
     *
     * @author pvraul1
     * @param lngVal
     * @param cmpVal 
     *
     * @return Currency List
     */
    List<OCrnCrnP> tbl(String lngVal, Integer cmpVal);

    /**
     * Query currency. It will return the currency
     *
     * @author Cristian Saball
     *
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cmpVal    -> company
     * @param sdrCrnVal -> standard currency value
     * @return OCrnCrnS -> the currency
     */
    OCrnCrnS getCurrency(String usrVal, String lngVal, Integer cmpVal, String sdrCrnVal);

}
