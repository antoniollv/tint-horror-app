package com.mapfre.tron.api.cmn.agn.cpe.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.agn.cpe.dl.OAgnCpeDao;
import com.mapfre.tron.api.cmn.dl.BaseCaheDao;

/**
*
* @author Javier Sangil
* @since 1.8
* @version 17 nov 2021 - 16:57:19
*
*/
@Repository
public class OAgnCpeDaoImpl extends BaseCaheDao implements OAgnCpeDao {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    @Cacheable("PoC-OAgnCpeNam")
    @Override
    public String getAgnCpeNam(Map<String, Object> map) {
        final String query = new StringBuilder()
                .append("SELECT nom_completo ")
                .append(" FROM v1001390 ")
                .append(" WHERE cod_cia       = :cmpVal ")
                .append(" AND cod_tercero     = :agnVal ")
                .append(" AND cod_act_tercero = 2 ")
                .toString();
        try {
            return jdbc.queryForObject(query, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
