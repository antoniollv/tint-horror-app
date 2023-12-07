package com.mapfre.tron.api.trn.ssg.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.ssg.bo.OCmnSsgS;

/**
 * The StorageFromSelfService interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 1 feb. 2022 - 16:41:54
 *
 */
public interface IBlCmnSsgCrt {

    /**
     * Create Storage From Self-Service.
     *
     * @param cmpVal               -> Company code
     * @param usrVal               -> User code
     * @param lngVal               -> Language code
     * @param inSelfServiceStorage -> Input data to new self service storage
     */
    void postStorageFromSelfService(Integer cmpVal, String usrVal, String lngVal, List<OCmnSsgS> inSelfServiceStorage);

}
