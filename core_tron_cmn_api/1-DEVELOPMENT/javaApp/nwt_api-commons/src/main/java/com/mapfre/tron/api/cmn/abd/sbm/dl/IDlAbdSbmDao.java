package com.mapfre.tron.api.cmn.abd.sbm.dl;

import java.util.Map;

import com.mapfre.tron.api.cmn.abd.sbm.dl.impl.VhtNamPK;
import com.mapfre.tron.api.cmn.dl.NewtronDao;

/**
 * The repository interface.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:10:07
 *
 */
public interface IDlAbdSbmDao extends NewtronDao<String, Map<String, Object>> {

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    String getMakNam(Map<String, Object> map);
    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    String getMdlNam(Map<String, Object> map);
    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    String getVhtNam(VhtNamPK map);
    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    String getTctNam(Map<String, Object> map);
    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    String getVhcCtgNam(Map<String, Object> map);
    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción
     */
    String getVhcCrbNam(Map<String, Object> map);

    /**
     * Obtiene la descripcion SbmNam.
     *
     * @param map -> El mapa de parametros
     * @return    -> La descripcion sbmNam
     */
    String getSbmNam(Map<String, Object> map);
}
