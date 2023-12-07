package com.mapfre.tron.api.cmn.grs.zof.dl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;
import com.mapfre.tron.api.cmn.grs.zof.dl.OgrsZofDao;
import com.mapfre.tron.api.cmn.grs.zof.dl.OgrsZofPK;

@Repository
public class OgrsZofDaoImpl implements OgrsZofDao {
    
    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    
	private static final OGrsZofSRowMapper MAPPER = new OGrsZofSRowMapper();
	private static final OGrsZofSRowMapper2 MAPPER_FOR_ALL = new OGrsZofSRowMapper2();
	private static final String QUERY = "SELECT a.* FROM a1000103 a ";
	private static final String PK = "  WHERE a.cod_postal= :pslCodVal AND a.mca_codpostal_real = UPPER(NVL(:reaPsc ,'S')) ";
	private static final String PK_FOR_ALL = "  WHERE a.cod_pais= :cnyVal AND a.cod_estado = :sttVal AND a.cod_prov = :pvcVal AND a.cod_localidad = :twnVal AND a.mca_codpostal_real = :reaPsc ";


    @Override
    @Cacheable("PoC-OGrsZofS")
    public OGrsZofS get(OgrsZofPK pk) {
	   
	    return null;	
    }
    
    @Override
    @Cacheable("PoC-OGrsZofSList")
    public List<OGrsZofS> getZoneFiveByPslCodVal(OgrsZofPK pk) {
	
	return jdbc.query(QUERY + PK, pk.asMap(), MAPPER);
	
    }
    
    @Override
    @Cacheable("PoC-OGrsZofSFiveList")
    public List<OGrsZofS> getZoneFiveList(OgrsZofPK pk) {
	
	return jdbc.query(QUERY + PK_FOR_ALL, pk.asMap(), MAPPER_FOR_ALL);
	
    }
    
    @Override
    @Cacheable("PoC-OGrsZofSListAll")
    public List<OGrsZofS> getAll() {
	
	return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
	
    }

    @Override
    public String getDescription(OGrsZofS o) {

	return null;
    }

    @Override
    public String getAbr(OGrsZofS o) {

	return null;
    }


    @Override
    public OGrsZofS save(OGrsZofS o) {

	return null;
    }

    @Override
    public int delete(OgrsZofPK o) {

	return 0;
    }

    @Override
    public OGrsZofS update(OGrsZofS o) {

	return null;
    }

}
