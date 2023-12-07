package com.mapfre.tron.api.cmn.dl;

import java.util.List;

/**
 * Interfaz generica de los DAO sobre conceptos lógicos.
 *
 * @param <P> clase que representa el objeto P de datos.
 * @param <PK> clase que representa la clave primaria.
 */
public interface NewtronDao<P, PK> {
    /** Devuelve una instancia del concepto lógico dada su clave. */
    P get(PK o);
    /** Devuelve la descripcion asociada al concepto lógico. */
    String getDescription(P o);
    /** Devuelve la abreviatura asociada al concepto lógico. */
    String getAbr(P o);
    /** Devuelve todas las instancias de un concepto lógico. */
    List<P> getAll();

    /**
     * Save the entity.
     *
     * @param o -> the entity to save
     * return   -> the entity saved
     */
    P save(P o);

    /**
     * Delete the entity.
     *
     * @param pmOCrnCrnPK -> the PK
     * @return            -> deleted items counter
     */
    int delete(PK o);

    /**
     * Update the entity.
     *
     * @param o -> the entity to update
     * return   -> the entity updated
     */
    P update(P o);
   
}
