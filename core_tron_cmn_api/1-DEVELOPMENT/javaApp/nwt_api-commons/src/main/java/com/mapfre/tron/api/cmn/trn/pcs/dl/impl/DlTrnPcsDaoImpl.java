package com.mapfre.tron.api.cmn.trn.pcs.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.dl.BaseCaheDao;
import com.mapfre.tron.api.cmn.trn.pcs.dl.IDlTrnPcsDao;

/**
 * The repository implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 13 Dec 2021 - 12:29:03
 *
 */
@Repository
public class DlTrnPcsDaoImpl extends BaseCaheDao implements IDlTrnPcsDao {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Obtiene la descripción.
     *
     * @param map -> La clave primaria
     * @return          -> La descripción PcsNam
     */
    @Cacheable("PoC-PcsNam")
    @Override
    public String getPcsNam(Map<String, Object> map) {
        final String query = new StringBuilder()
       .append(" SELECT nom_tramite ")
       .append(" FROM G7500020 t  ")
       .append(" WHERE t.cod_cia = :cmpVal  ")/*1*/
       .append(" AND t.cod_tramite = :pcsVal  ")/*'9999'*/
       .toString();
        try {
            return jdbc.queryForObject(query, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
