package com.mapfre.tron.api.cmn.thp.acv.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.dl.BaseCaheDao;
import com.mapfre.tron.api.cmn.thp.acv.dl.IDlThpAcv;

/**
 * The thirdpard activity repository implementation.
 *
 * @author arquitectura - pvraul1
 * @since 1.8
 * @version 5 Dec 2022 - 15:02:48
 *
 */
@Repository
public class DlThpAcvDaoImpl extends BaseCaheDao implements IDlThpAcv {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Get the Thirdpard activity description.
     *
     * @param map -> The map with the query params values
     * @return    -> The thirdpard activity description
     */
    @Cacheable("PoC-ThpAcvNam")
    @Override
    public String getThpAcvNam(final Map<String, Object> map) {

        final String sql = new StringBuilder()
                .append("SELECT ")
                .append("    a.nom_act_tercero ")
                .append("FROM ")
                .append("    a1002200 a ")
                .append("WHERE ")
                .append("    a.cod_cia = :cmpVal ")
                .append("AND a.cod_act_tercero = :thpAcvVal ")
                .toString();

        try {
            return jdbc.queryForObject(sql, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}
