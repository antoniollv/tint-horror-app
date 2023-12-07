package com.mapfre.tron.api.cmn.thp.ent.bl;

import java.util.Map;

/**
 * The business interface.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 23 dec 2022 - 14:49:20
 *
 */
public interface IBlOThpEntS {

    /**
     * Get the description.
     *
     * @param map -> The map with the query params values
     * @return    -> The description
     */
    String getOThpEntS(Map<String, Object> map);

}
