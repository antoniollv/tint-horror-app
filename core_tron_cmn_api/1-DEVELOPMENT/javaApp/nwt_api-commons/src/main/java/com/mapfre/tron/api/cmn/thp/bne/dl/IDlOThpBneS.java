package com.mapfre.tron.api.cmn.thp.bne.dl;

import java.util.Map;

import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The repository interface.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 23 dec 2022 - 14:57:15
 *
 */
public interface IDlOThpBneS extends NewtronDao<String, Map<String, Object>> {

    /**
     * Get the description.
     *
     * @param map -> The map with the query params values
     * @return    -> The description
     */
    String getOThpBneS(Map<String, Object> map);

}
