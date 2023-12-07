package com.mapfre.tron.api.tpd.prf.dl;

import com.mapfre.nwt.trn.tpd.prf.bo.OTpdPrfS;

/**
 * The TpdPrf repository interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 23 Mar 2023 - 10:12:23
 *
 */
public interface IDlTpdPrfDAO {

    /**
     * Query Profession. It will return the information of Profession.
     *
     * @param usrVal -> User code
     * @param lngVal -> Language code
     * @param prfVal -> Profession
     * @param cmpVal -> Company code
     * @return       -> The information of Profession
     */
    OTpdPrfS getProfession(String usrVal, String lngVal, Integer prfVal, Integer cmpVal);

}
