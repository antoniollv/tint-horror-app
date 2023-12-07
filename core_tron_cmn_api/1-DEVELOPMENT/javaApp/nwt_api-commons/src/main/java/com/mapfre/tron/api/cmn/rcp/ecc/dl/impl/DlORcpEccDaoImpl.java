package com.mapfre.tron.api.cmn.rcp.ecc.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.dl.BaseCaheDao;
import com.mapfre.tron.api.cmn.rcp.ecc.dl.IDlORcpEcc;

/**
 * The RcpEcc repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 18 Jan 2023 - 17:49:28
 *
 */
@Repository
public class DlORcpEccDaoImpl extends BaseCaheDao implements IDlORcpEcc {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Get the economic concept description.
     *
     * @param map    -> The map with the query params values
     * @return       -> The economic concept description
     */
    @Cacheable("PoC-EccNam")
    @Override
    public String getEccNam(final Map<String, Object> map) {
        final String sql = new StringBuilder()
                .append("SELECT ")
                .append("    t.nom_eco ")
                .append("FROM ")
                .append("    g2000161 t ")
                .append("WHERE ")
                .append("    t.cod_cia = :cmpVal ")
                .append("AND t.cod_eco = :eccVal ")
                .toString();

        try {
            return jdbc.queryForObject(sql, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}
