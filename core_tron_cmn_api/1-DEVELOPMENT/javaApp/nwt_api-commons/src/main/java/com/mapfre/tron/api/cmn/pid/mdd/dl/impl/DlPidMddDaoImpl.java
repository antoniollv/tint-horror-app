package com.mapfre.tron.api.cmn.pid.mdd.dl.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.tron.api.cmn.dl.BaseCaheDao;
import com.mapfre.tron.api.cmn.pid.mdd.dl.IDlPidMddDao;

/**
 * The repository implementation.
 *
 * @author Javier Sangil
 * @since 1.8
 * @version 19 May 2022 - 12:37:24
 *
 */
@Repository
public class DlPidMddDaoImpl extends BaseCaheDao implements IDlPidMddDao {

    @Qualifier("npJdbcTemplate")
    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Get the description.
     *
     * @param map 
     * @return       -> The description
     */
    @Cacheable("PoC-MdtNam")
    @Override
    public String getMdtNam(final Map<String, Object> map) {
        final String sql = new StringBuilder()
        	.append(" SELECT nom_modalidad ")
        	.append(" FROM g2990004 ")
        	.append(" WHERE cod_cia = :cmpVal AND ")
        	.append("       cod_modalidad = :mdtVal ")
                .toString();

        try {
            return jdbc.queryForObject(sql, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}
