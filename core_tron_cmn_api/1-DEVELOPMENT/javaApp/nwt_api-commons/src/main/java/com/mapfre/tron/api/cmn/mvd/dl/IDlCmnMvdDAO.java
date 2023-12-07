package com.mapfre.tron.api.cmn.mvd.dl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.mvd.bo.OCmnMvdS;

/**
 * The CmnMvd respository interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 28 Mar 2023 - 09:05:29
 *
 */
public interface IDlCmnMvdDAO {

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
    List<OCmnMvdS> getMovementDefinition(String lngVal, String usrVal, Integer cmpVal, List<String> mvmIdn,
            Long vldDat);

}
