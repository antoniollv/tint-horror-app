package com.mapfre.tron.api.cmn.thp.acv.dl;

import java.util.Map;

import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The thirdpard activity repository interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 5 Dec 2022 - 14:57:15
 *
 */
public interface IDlThpAcv extends NewtronDao<String, Map<String, Object>> {

    /**
     * Get the Thirdpard activity description.
     *
     * @param map -> The map with the query params values
     * @return    -> The thirdpard activity description
     */
    String getThpAcvNam(Map<String, Object> map);

}
