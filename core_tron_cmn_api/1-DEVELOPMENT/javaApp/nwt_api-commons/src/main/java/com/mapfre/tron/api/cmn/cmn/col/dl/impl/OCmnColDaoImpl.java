package com.mapfre.tron.api.cmn.cmn.col.dl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.cmn.col.bo.OCmnColS;
import com.mapfre.tron.api.cmn.cmn.col.dl.OCmnColDao;
import com.mapfre.tron.api.cmn.cmn.col.dl.OCmnColPK;

@Repository
public class OCmnColDaoImpl implements OCmnColDao {
    
    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    
    private static final OCmnColPRowMapper MAPPER = new OCmnColPRowMapper();
    private static final String QUERY = "SELECT a.* FROM a2100800 a";
    /** The primary key clause query property.*/
    private static final String PK = " WHERE a.cod_color = :colVal";

    /**
     * Get the entity.
     *
     * @param pmOCmnColQryPK -> The primary key
     * @return            -> The data entity
     */
    @Override 
    @Cacheable("PoC-OCmnColS")
    public OCmnColS get(OCmnColPK pmOCmnColQryPK) {
        try {
            return jdbc.queryForObject(QUERY + PK, pmOCmnColQryPK.asMap(), MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getDescription(OCmnColS o) {

	return null;
    }

    @Override
    public String getAbr(OCmnColS o) {

	return null;
    }

    @Override
    @Cacheable("PoC-OCmnColSList")
    public List<OCmnColS> getAll() {
	return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
    }

    @Override
    public OCmnColS save(OCmnColS o) {

	return null;
    }

    @Override
    public int delete(OCmnColPK o) {

	return 0;
    }

    @Override
    public OCmnColS update(OCmnColS o) {

	return null;
    }

}
