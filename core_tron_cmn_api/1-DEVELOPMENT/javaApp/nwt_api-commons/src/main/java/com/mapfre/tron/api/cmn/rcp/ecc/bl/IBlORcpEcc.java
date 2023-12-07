package com.mapfre.tron.api.cmn.rcp.ecc.bl;

import java.util.Map;

/**
 * The RcpEcc business service interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 18 Jan 2023 - 17:06:09
 *
 */
public interface IBlORcpEcc {

    /**
     * Get the economic concept description.
     *
     * @param map    -> The map with the query params values
     * @return       -> The economic concept description
     */
    String getEccNam(Map<String, Object> map);

}
