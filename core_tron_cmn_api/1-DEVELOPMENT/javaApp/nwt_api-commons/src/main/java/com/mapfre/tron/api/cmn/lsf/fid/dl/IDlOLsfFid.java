package com.mapfre.tron.api.cmn.lsf.fid.dl;

import java.util.Map;

import com.mapfre.nwt.trn.lsf.fid.bo.OLsfFidS;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The LsfFid repository interface.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 9 feb 2023 - 17:49:28
 *
 */
public interface IDlOLsfFid extends NewtronDao<String, Map<String, Object>> {

    /**
     * Get the economic OLsfFidS
     *
     * @param map    -> The map with the query params values
     * @return       -> The economic concept description
     */
    OLsfFidS getOLsfFid(Map<String, Object> map);

}
