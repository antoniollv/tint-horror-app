package com.mapfre.tron.api.cmn.dl;

import java.io.Serializable;
import java.util.Map;

/**
 * Interfaz que representa la clave de un concepto lógico.
 */
public interface NewtronPK extends Serializable {
    /** Devuelve la clave en forma de mapa de parámetros. */
	Map<String, Object> asMap();
}
