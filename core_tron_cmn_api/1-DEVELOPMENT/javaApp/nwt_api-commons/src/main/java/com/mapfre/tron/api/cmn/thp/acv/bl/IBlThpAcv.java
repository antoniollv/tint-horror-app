package com.mapfre.tron.api.cmn.thp.acv.bl;

import java.util.Map;

/**
 * The thirdparty activity business interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 5 Dec 2022 - 14:49:20
 *
 */
public interface IBlThpAcv {

    /**
     * Get the Thirdpard activity description.
     *
     * @param map -> The map with the query params values
     * @return    -> The Thirdpard activity description
     */
    String getThpAcvNam(Map<String, Object> map);

}
