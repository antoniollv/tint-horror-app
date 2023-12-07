package com.mapfre.tron.api.cmn.abd.vhu.bl;

import java.util.Map;

/**
 * The AbdVhu business interface.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 17 May 2022 - 12:25:13
 *
 */
public interface IBlAbdVhu {

    /**
     * Get the vehicle use description.
     *
     * @param map -> Company code and Vehicle use code
     * @return       -> The vehicle use description
     */
    String getVhuNam(Map<String, Object> map);

}
