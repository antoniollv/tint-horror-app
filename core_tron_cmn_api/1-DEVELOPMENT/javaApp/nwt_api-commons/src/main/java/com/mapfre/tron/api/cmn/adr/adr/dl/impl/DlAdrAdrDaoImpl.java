package com.mapfre.tron.api.cmn.adr.adr.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.adr.adr.dl.IDlAdrAdrDao;
import com.mapfre.tron.api.cmn.dl.BaseCaheDao;

/**
 * The repository implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:29:03
 *
 */
@Repository
public class DlAdrAdrDaoImpl extends BaseCaheDao implements IDlAdrAdrDao {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return -> La descripción 
     */
    @Cacheable("PoC-MakNam")
    @Override
    public String getFldNamVal(Map<String, Object> map) {
        final String query = new StringBuilder()
       .append("SELECT a.nom_campo   ")//SELECT a.* FROM G2000010 a WHERE a.cod_cia=cmpVal AND a.cod_Campo=fldNam
       .append("  FROM G2000010 a  ")
       .append(" WHERE a.cod_cia = :cmpVal   ")
       .append("   AND a.cod_Campo = :fldNam   ")
       .toString();
        try {
            return jdbc.queryForObject(query, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
