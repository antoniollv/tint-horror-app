package com.mapfre.tron.api.cmn.mvd.bl;

import java.util.List;

import com.mapfre.nwt.trn.cmn.mvd.bo.OCmnMvdS;

/**
 * The CmnMvd query business service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 27 Mar 2023 - 17:26:31
 *
 */
public interface IBlCmnMvdQry {

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
