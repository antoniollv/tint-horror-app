package com.mapfre.tron.api.cmn.adr.adr.bl;

import java.util.Map;

/**
 * The service interface.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:47:28
 *
 */
public interface IBlAdrAdr {

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    String getFldNamVal(Map<String, Object> map);
    
}
