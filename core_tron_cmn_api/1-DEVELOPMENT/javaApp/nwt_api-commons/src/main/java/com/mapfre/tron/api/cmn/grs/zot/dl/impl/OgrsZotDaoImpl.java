package com.mapfre.tron.api.cmn.grs.zot.dl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;
import com.mapfre.tron.api.cmn.grs.zot.dl.OgrsZotDao;
import com.mapfre.tron.api.cmn.grs.zot.dl.OgrsZotPK;

@Repository
public class OgrsZotDaoImpl implements OgrsZotDao {
    
    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    
	private static final OGrsZotSRowMapper MAPPER = new OGrsZotSRowMapper();
	private static final String QUERY = "SELECT a.* FROM a1000100 a";
	private static final String PK = "  WHERE a.cod_pais = :cnyVal AND a.cod_prov = :pvcVal";
	private static final String PK_FOR_ALL = "  WHERE a.cod_pais = :cnyVal AND a.cod_estado = :sttVal ORDER BY a.nom_prov";


    @Override
    @Cacheable("PoC-OGrsZotS")
    public OGrsZotS get(OgrsZotPK pk) {
	try {
		return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
		return null;
	}		
    }
    
    @Override
    @Cacheable("PoC-OGrsZotSList")
    public List<OGrsZotS> getAllWithPK(OgrsZotPK pk) {
	
	return jdbc.query(QUERY + PK_FOR_ALL, pk.asMap(), MAPPER);		
    }
    
    @Override
    @Cacheable("PoC-OGrsZotSListAll")
    public List<OGrsZotS> getAll() {
	
	return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
	
    }

    @Override
    public String getDescription(OGrsZotS o) {
	
	return null;
    }

    @Override
    public String getAbr(OGrsZotS o) {
	
	return null;
    }


    @Override
    public OGrsZotS save(OGrsZotS o) {
	
	return null;
    }

    @Override
    public int delete(OgrsZotPK o) {
	
	return 0;
    }

    @Override
    public OGrsZotS update(OGrsZotS o) {
	
	return null;
    }

}
