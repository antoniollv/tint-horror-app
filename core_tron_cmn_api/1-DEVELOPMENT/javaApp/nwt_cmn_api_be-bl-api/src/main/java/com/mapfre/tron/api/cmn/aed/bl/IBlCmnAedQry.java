package com.mapfre.tron.api.cmn.aed.bl;

import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;

/**
 * The CmnAedQry business logic service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 17 Oct 2023 - 09:49:27
 *
 */
public interface IBlCmnAedQry {

    /**
     * TRON-16952 Query data access by user code.
     *
     * @param cmpVal    -> Company Code
     * @param cmpUsrVal -> Company User Value
     * @param lngVal    -> Language Code
     * @param usrVal    -> User Value
     * @return          -> The data access
     */
    OCmnAedS getDataAccessUser(Integer cmpVal, String cmpUsrVal, String lngVal, String usrVal);

}
