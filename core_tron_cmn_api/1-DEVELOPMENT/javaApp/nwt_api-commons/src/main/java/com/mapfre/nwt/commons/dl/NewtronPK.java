package com.mapfre.nwt.commons.dl;

import java.util.Map;

/**
 * Interfaz que representa la clave de un concepto lógico.
 *
 * @author pvraul1 - architecture
 * @since 1.8
 * @version 6 abr. 2021 - 8:10:12
 *
 */
public interface NewtronPK {

    /** Devuelve la clave en forma de mapa de parámetros. */
    Map<String, Object> asMap();

}
