package com.mapfre.tron.api.cmn.prt.sbs.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.prt.sbs.bo.OPrtSbsS;
import com.mapfre.tron.api.cmn.prt.sbs.dl.OPrtSbsDao;
import com.mapfre.tron.api.cmn.prt.sbs.dl.OPrtSbsPK;
import com.mapfre.tron.api.cmn.prt.sbs.dl.OPrtSbsPK2;

@Repository
public class OPrtSbsDaoImpl implements OPrtSbsDao {
	
	@Qualifier("npJdbcTemplate")
	@Autowired 
	private NamedParameterJdbcTemplate jdbc;
	private static final OPrtSbsSRowMapper MAPPER = new OPrtSbsSRowMapper();
	private static final String QUERY = "SELECT ROWID, a.* FROM a1000250 a";
	private static final String PK = " WHERE a.COD_CIA = :cmpVal AND a.COD_SECTOR = :secVal";
	private static final String PK_SECTOR = " WHERE a.cod_cia= :cmpVal AND a.cod_sector= :secVal AND a.cod_subSector= :sbsVal";
	
	@Override
	@Cacheable("PoC-OPrtSbs")
	public List<OPrtSbsS> getSubSectorList(OPrtSbsPK pk) {
	    return jdbc.query(QUERY + PK , pk.asMap(), MAPPER);
	}
	
	
	@Override
	@Cacheable("PoC-OPrtSbsS")
	public OPrtSbsS getSubSector(OPrtSbsPK2 pk) {
	    return jdbc.queryForObject(QUERY + PK_SECTOR , pk.asMap(), MAPPER);
	}
	
	
	@Override
	public OPrtSbsS get(OPrtSbsPK o) {
	    
	    return null;
	}
	@Override
	public String getDescription(OPrtSbsS o) {
	    
	    return null;
	}
	@Override
	public String getAbr(OPrtSbsS o) {
	    
	    return null;
	}
	@Override
	public List<OPrtSbsS> getAll() {
	    
	    return Collections.emptyList();
	}
	@Override
	public OPrtSbsS save(OPrtSbsS o) {
	    
	    return null;
	}
	@Override
	public int delete(OPrtSbsPK o) {
	    
	    return 0;
	}
	@Override
	public OPrtSbsS update(OPrtSbsS o) {
	    
	    return null;
	}




}
