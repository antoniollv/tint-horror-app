package com.mapfre.tron.api.cmn.lsf.fid.bl;

import java.util.Map;

import com.mapfre.nwt.trn.lsf.fid.bo.OLsfFidS;

/**
 * The IBlOLsfFid business service interface.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 9 feb 2023 - 17:49:28
 *
 */
public interface IBlOLsfFid {

    /**
     * Get the OLsfFidS
     *
     * @param map    -> The map with the query params values
     * @return       -> The OLsfFidS
     */
    OLsfFidS getOLsfFid(Map<String, Object> map);

}
