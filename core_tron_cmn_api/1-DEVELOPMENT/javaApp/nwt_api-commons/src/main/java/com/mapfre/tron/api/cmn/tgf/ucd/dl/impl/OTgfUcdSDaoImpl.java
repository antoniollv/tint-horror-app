package com.mapfre.tron.api.cmn.tgf.ucd.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.tgf.ucd.bo.OTgfUcdS;
import com.mapfre.tron.api.cmn.tgf.ucd.dl.OTgfUcdSDao;
import com.mapfre.tron.api.cmn.tgf.ucd.dl.OTgfUcdSPK;
import com.mapfre.tron.api.cmn.tgf.ucd.dl.OTgfUcdSPK2;

@Repository
public class OTgfUcdSDaoImpl implements OTgfUcdSDao {

    private static final OTgfUcdSMapper MAPPER = new OTgfUcdSMapper();
    private static final String QUERY = "SELECT ROWID, a.* FROM a5020028 a ";
    private static final String PK = " where a.cod_cia = :cmpVal AND a.tip_causa = :tscTypVal";
    private static final String PK2 = " where a.cod_cia = :cmpVal AND a.tip_causa = :tscTypVal AND a.cod_causa = :tsyCasVal";

    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    
    @Cacheable("PoC-OTgfUcdSList")
	@Override
	public List<OTgfUcdS> getCauses(OTgfUcdSPK pk) {
    	try {
    	    return jdbc.query(QUERY + PK, pk.asMap(), MAPPER);
    	} catch (EmptyResultDataAccessException e) {
    	    return Collections.emptyList();
    	}
	}

    @Cacheable("PoC-OTgfUcdS")
	@Override
	public OTgfUcdS getCause(OTgfUcdSPK2 pk) {
    	try {
    	    return jdbc.queryForObject(QUERY + PK2, pk.asMap(), MAPPER);
    	} catch (EmptyResultDataAccessException e) {
    	    return null;
    	}
	}

	@Override
	public OTgfUcdS get(OTgfUcdSPK o) {
		
		return null;
	}

	@Override
	public String getDescription(OTgfUcdS o) {
		
		return null;
	}

	@Override
	public String getAbr(OTgfUcdS o) {
		
		return null;
	}

	@Override
	public List<OTgfUcdS> getAll() {
		
		return Collections.emptyList();
	}

	@Override
	public OTgfUcdS save(OTgfUcdS o) {
		
		return null;
	}

	@Override
	public int delete(OTgfUcdSPK o) {
		
		return 0;
	}

	@Override
	public OTgfUcdS update(OTgfUcdS o) {
		
		return null;
	}


}
