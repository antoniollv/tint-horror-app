package com.mapfre.tron.api.cmn.grs.znf.dl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.grs.znf.bo.OGrsZnfS;
import com.mapfre.tron.api.cmn.grs.znf.dl.OgrsZnfDao;
import com.mapfre.tron.api.cmn.grs.znf.dl.OgrsZnfPK;

@Repository
public class OgrsZnfDaoImpl implements OgrsZnfDao {
    
    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    
	private static final OGrsZnfSRowMapper MAPPER = new OGrsZnfSRowMapper();
	private static final OGrsZnfSRowMapper MAPPER_FOR_ALL = new OGrsZnfSRowMapper();
	private static final String QUERY = "SELECT a.* FROM a1000102 a ";
	private static final String PK = "  WHERE a.cod_pais = :cnyVal AND a.cod_prov = :pvcVal AND a.cod_localidad =  :twnVal AND a.mca_localidad_real = 'S' ";
	private static final String PK_FOR_ALL = " WHERE a.cod_pais = :cnyVal AND a.cod_prov = :pvcVal AND a.mca_inh= 'N' AND a.mca_localidad_real = 'S' ORDER BY a.nom_localidad";

    @Override
    @Cacheable("PoC-OGrsZnfS")
    public OGrsZnfS get(OgrsZnfPK pk) {
	try {
	    OGrsZnfS oGrsZnfS = jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
	    if(oGrsZnfS !=null) {
		oGrsZnfS.setPvcVal(pk.getPvcVal());
	    }
	    return oGrsZnfS;
	} catch (EmptyResultDataAccessException e) {
		return null;
	}		
    }
    
    @Override
    @Cacheable("PoC-OGrsZnfSListAll")
    public List<OGrsZnfS> getAll() {
	
	return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
	
    }
    
    @Override
    @Cacheable("PoC-OGrsZnfSList")
    public List<OGrsZnfS> getAllWithPK(OgrsZnfPK pk) {
	
	return jdbc.query(QUERY + PK_FOR_ALL, pk.asMap(), MAPPER_FOR_ALL);		
    }

    @Override
    public String getDescription(OGrsZnfS o) {

	return null;
    }

    @Override
    public String getAbr(OGrsZnfS o) {

	return null;
    }


    @Override
    public OGrsZnfS save(OGrsZnfS o) {

	return null;
    }

    @Override
    public int delete(OgrsZnfPK o) {

	return 0;
    }

    @Override
    public OGrsZnfS update(OGrsZnfS o) {

	return null;
    }

}
