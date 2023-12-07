package com.mapfre.tron.api.grs.zon.dl;

import java.util.List;

import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;

/**
 * The GrsZonSbdQry repository interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 31 Mar 2022 - 12:47:29
 *
 */
public interface ISrGrsZonSbdQryDAO {

    /**
     * Query Zone Four Subcode.
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country
     * @param sttVal    -> State Value
     * @param pvcVal    -> Province Value
     * @param twnVal    -> Town Value
     * @param ditVal    -> District Value
     * @param reaGrsDit -> Real District
     * @return          -> It will return the zone four subcode (District)
     */
    OGrsZnfS getZoneFourSubcode(Integer cmpVal, String usrVal, String lngVal, String cnyVal, Integer sttVal,
            Integer pvcVal, Integer twnVal, String ditVal, String reaGrsDit);

    /**
     * Query Zone Four Subcode List.
     *
     * @param cmpVal    -> Company code
     * @param usrVal    -> User code
     * @param lngVal    -> Language code
     * @param cnyVal    -> Country
     * @param sttVal    -> State Value
     * @param pvcVal    -> Province Value
     * @param twnVal    -> Town Value
     * @param reaGrsDit -> Real District
     * @return          -> It will return the list of zone four subcode list (Districts)
     */
    List<OGrsZnfS> getZoneFourSubcodeList(Integer cmpVal, String usrVal, String lngVal, String cnyVal, String sttVal,
            Integer pvcVal, Integer twnVal, String reaGrsDit);

}
