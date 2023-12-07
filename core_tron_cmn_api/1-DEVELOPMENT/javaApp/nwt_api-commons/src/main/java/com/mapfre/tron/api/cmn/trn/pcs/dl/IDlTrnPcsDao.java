package com.mapfre.tron.api.cmn.trn.pcs.dl;

import java.util.Map;

import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The repository interface.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:10:07
 *
 */
public interface IDlTrnPcsDao extends NewtronDao<String, Map<String, Object>> {



    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return          -> La descripción
     */
    String getPcsNam(Map<String, Object> map);

}
