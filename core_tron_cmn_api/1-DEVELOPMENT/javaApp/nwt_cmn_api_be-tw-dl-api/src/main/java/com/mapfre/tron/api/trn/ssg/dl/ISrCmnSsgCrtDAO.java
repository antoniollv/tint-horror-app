package com.mapfre.tron.api.trn.ssg.dl;

import com.mapfre.nwt.trn.cmn.ssg.bo.OCmnSsgS;

/**
 * The CmnSsgCrt repository.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 1 feb. 2022 - 17:46:05
 *
 */
public interface ISrCmnSsgCrtDAO {

    /**
     * Create Storage From Self-Service.
     *
     * @param cmpVal   -> Company code
     * @param usrVal   -> User code
     * @param lngVal   -> Language code
     * @param oCmnSsgS -> Input data to new self service storage
     * @return         -> number or rows inserted
     */
    int postStorageFromSelfService(Integer cmpVal, String usrVal, String lngVal, OCmnSsgS oCmnSsgS);

}
