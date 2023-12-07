package com.mapfre.tron.api.cmn.grs.znt.dl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;
import com.mapfre.tron.api.cmn.grs.znt.dl.OgrsZntDao;
import com.mapfre.tron.api.cmn.grs.znt.dl.OgrsZntPK;

@Repository
public class OgrsZntDaoImpl implements OgrsZntDao {
    
    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    
	private static final OGrsZntSRowMapper MAPPER = new OGrsZntSRowMapper();
	private static final OGrsZntSRowMapperAll MAPPER_FOR_ALL = new OGrsZntSRowMapperAll();
	private static final String QUERY = "SELECT a.* FROM a1000104 a";
	private static final String PK = "  WHERE a.cod_pais = :cnyVal AND a.cod_estado = :sttVal";
	private static final String PK_FOR_ALL = "  WHERE a.cod_pais = :cnyVal AND a.mca_inh = 'N' AND a.mca_estado_real = 'S' ORDER BY a.nom_estado";


    @Override
    @Cacheable("PoC-OGrsZntS")
    public OGrsZntS get(OgrsZntPK pk) {
	try {
		return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
		return null;
	}		
    }
    
    @Override
    @Cacheable("PoC-OGrsZntSList")
    public List<OGrsZntS> getAllWithPK(OgrsZntPK pk) {
	
	return jdbc.query(QUERY + PK_FOR_ALL, pk.asMap(), MAPPER_FOR_ALL);
	
    }
    
    @Override
    @Cacheable("PoC-OGrsZntSListAll")
    public List<OGrsZntS> getAll() {
	
	return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
	
    }

    @Override
    public String getDescription(OGrsZntS o) {

	return null;
    }

    @Override
    public String getAbr(OGrsZntS o) {

	return null;
    }


    @Override
    public OGrsZntS save(OGrsZntS o) {

	return null;
    }

    @Override
    public int delete(OgrsZntPK o) {

	return 0;
    }

    @Override
    public OGrsZntS update(OGrsZntS o) {

	return null;
    }

}
