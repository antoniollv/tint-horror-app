package com.mapfre.tron.api.cmn.pid.mdd.bl;

import java.util.Map;

/**
 * The PidMdd business interface.
 *
 * @author Javier Sangil 
 * @since 1.8
 * @version 19 May 2022 - 12:25:13
 *
 */
public interface IBlPidMdd {

    /**
     * Get the description.
     *
     * @param map 
     * @return       -> The description
     */
    String getMdtNam(Map<String, Object> map);

}
