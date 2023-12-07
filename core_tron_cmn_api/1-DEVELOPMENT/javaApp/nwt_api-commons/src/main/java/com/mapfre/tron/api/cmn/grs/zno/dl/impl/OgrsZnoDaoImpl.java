package com.mapfre.tron.api.cmn.grs.zno.dl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;
import com.mapfre.tron.api.cmn.grs.zno.dl.OgrsZnoDao;
import com.mapfre.tron.api.cmn.grs.zno.dl.OgrsZnoPK;

@Repository
public class OgrsZnoDaoImpl implements OgrsZnoDao {
    
    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    
	private static final OGrsZnoSRowMapper MAPPER = new OGrsZnoSRowMapper();
	private static final String QUERY = "SELECT a.* FROM a1000101 a ";
	private static final String PK = "  WHERE a.cod_pais= :cnyVal ";


    @Override
    @Cacheable("PoC-OGrsZnoS")
    public OGrsZnoS get(OgrsZnoPK pk) {
	try {
	   
	    return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);

	} catch (EmptyResultDataAccessException e) {
		return null;
	}		
    }
    
    @Override
    @Cacheable("PoC-OGrsZnoSList")
    public List<OGrsZnoS> getAll() {
	
	return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
	
    }

    @Override
    public String getDescription(OGrsZnoS o) {

	return null;
    }

    @Override
    public String getAbr(OGrsZnoS o) {

	return null;
    }


    @Override
    public OGrsZnoS save(OGrsZnoS o) {

	return null;
    }

    @Override
    public int delete(OgrsZnoPK o) {

	return 0;
    }

    @Override
    public OGrsZnoS update(OGrsZnoS o) {

	return null;
    }

}
