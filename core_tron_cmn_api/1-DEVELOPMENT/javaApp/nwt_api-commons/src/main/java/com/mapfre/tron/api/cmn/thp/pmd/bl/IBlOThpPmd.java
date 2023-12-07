package com.mapfre.tron.api.cmn.thp.pmd.bl;

import java.util.Map;

/**
 * The business interface.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 23 dec 2022 - 14:49:20
 *
 */
public interface IBlOThpPmd {

    /**
     * Get the description.
     *
     * @param map -> The map with the query params values
     * @return    -> The description
     */
    String getOThpPmd(Map<String, Object> map);

}
