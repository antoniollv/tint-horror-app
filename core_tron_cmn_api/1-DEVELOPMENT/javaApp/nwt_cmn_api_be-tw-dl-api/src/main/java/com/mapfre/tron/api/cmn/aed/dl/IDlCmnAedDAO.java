package com.mapfre.tron.api.cmn.aed.dl;

import com.mapfre.nwt.trn.cmn.aed.bo.OCmnAedS;

/**
 * The CmnAed interface repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 Oct 2023 - 11:24:10
 *
 */
public interface IDlCmnAedDAO {

    /**
     * TRON-16952 Query data access by user code.
     *
     * @param cmpVal    -> Company Code
     * @param cmpUsrVal -> Company User Value
     * @param lngVal    -> Language Code
     * @param usrVal    -> User Value
     * @return          -> The data access
     */
    OCmnAedS get000(Integer cmpVal, String cmpUsrVal, String lngVal, String usrVal);

}
