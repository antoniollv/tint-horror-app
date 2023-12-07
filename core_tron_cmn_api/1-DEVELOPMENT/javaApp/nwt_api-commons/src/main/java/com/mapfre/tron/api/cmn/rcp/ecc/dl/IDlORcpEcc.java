package com.mapfre.tron.api.cmn.rcp.ecc.dl;

import java.util.Map;

import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The RcpEcc repository interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 18 Jan 2023 - 17:37:59
 *
 */
public interface IDlORcpEcc extends NewtronDao<String, Map<String, Object>> {

    /**
     * Get the economic concept description.
     *
     * @param map    -> The map with the query params values
     * @return       -> The economic concept description
     */
    String getEccNam(Map<String, Object> map);

}
