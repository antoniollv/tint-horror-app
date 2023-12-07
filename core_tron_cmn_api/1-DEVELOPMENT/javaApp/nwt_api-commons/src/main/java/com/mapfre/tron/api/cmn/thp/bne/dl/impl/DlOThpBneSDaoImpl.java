package com.mapfre.tron.api.cmn.thp.bne.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.dl.BaseCaheDao;
import com.mapfre.tron.api.cmn.thp.bne.dl.IDlOThpBneS;

/**
 * The repository implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 23 dec 2022 - 15:02:48
 *
 */
@Repository
public class DlOThpBneSDaoImpl extends BaseCaheDao implements IDlOThpBneS {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Get the description.
     *
     * @param map -> The map with the query params values
     * @return    -> The description
     */
    @Cacheable("PoC-ThpBneNam")
    @Override
    public String getOThpBneS(final Map<String, Object> map) {

        final String sql = new StringBuilder()
                .append("select a.nom_entidad FROM a5020900 a ")
                .append("WHERE a.cod_entidad = :bneVal ")
                .append("AND a.cod_cia     = :cmpVal  ")
                .toString();

        try {
            return jdbc.queryForObject(sql, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}
