package com.mapfre.tron.api.cmn.cache.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.mvd.bo.OCmnMvdS;

/**
 * The own CMN cacheable methods.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 28 Mar 2023 - 11:38:28
 *
 */
public interface ICmnCacheableMethods {

    /**
     * Query movement definition.
     *
     * @param lngVal -> Language code
     * @param usrVal -> User code
     * @param cmpVal -> Company code
     * @param mvmIdn -> Movement Identification
     * @param vldDat -> Valid date
     * @return       -> It will return the defintion of movements.
     */
    List<OCmnMvdS> getMovementDefinitionCache(String lngVal, String usrVal, Integer cmpVal, List<String> mvmIdn,
            Long vldDat);

}
