package com.mapfre.tron.api.cmn.abd.vhu.dl;

import java.util.Map;

import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The AbdVhu repository interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 17 May 2022 - 12:34:14
 *
 */
public interface IDlAbdVhuDao extends NewtronDao<String, Map<String, Object>> {

    /**
     * Get the vehicle use description.
     *
     * @param map -> Company code and Vehicle use code
     * @return       -> The vehicle use description
     */
    String getVhuNam(final Map<String, Object> map);

}
