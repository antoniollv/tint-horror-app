package com.mapfre.tron.api.cmn.trn.fil.bl;

import java.util.Map;

/**
 * The service interface.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:47:28
 *
 */
public interface IBlTrnFilTyp {

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return          -> La descripción
     */
    String getFilTypNam(Map<String, Object> map);

}
