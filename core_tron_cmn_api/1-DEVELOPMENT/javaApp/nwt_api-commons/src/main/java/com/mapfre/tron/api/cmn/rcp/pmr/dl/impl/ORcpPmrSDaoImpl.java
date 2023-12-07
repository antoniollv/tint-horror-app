package com.mapfre.tron.api.cmn.rcp.pmr.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.rcd.tsd.bo.ORcdTsdS;
import com.mapfre.nwt.trn.rcp.pmr.bo.ORcpPmrS;
import com.mapfre.tron.api.cmn.rcp.pmr.dl.ORcdTsdSPK;
import com.mapfre.tron.api.cmn.rcp.pmr.dl.ORcpPmrPK;
import com.mapfre.tron.api.cmn.rcp.pmr.dl.ORcpPmrSDao;

@Repository
public class ORcpPmrSDaoImpl implements ORcpPmrSDao {

	@Qualifier("npJdbcTemplate")
	@Autowired 
	private NamedParameterJdbcTemplate jdbc;
	//Table A5020500
	private static final ORcpPmrSRowMapper MAPPER = new ORcpPmrSRowMapper();
	private static final ORcdTsdSRowMapper MAPPER2 = new ORcdTsdSRowMapper();
	private static final String QUERY = "SELECT ROWID, a.* FROM A5020500 a";
	private static final String QUERY2 = "SELECT a.tip_situacion,a.nom_situacion,a.cod_usr,a.fec_actu FROM a5020500 a ";
	
	private static final String PK = " WHERE a.TIP_SITUACION =:typVal ";
	private static final String PK2 = " WHERE a.tip_situacion = :rcpStsTypVal ";
	
	
	//Table A1000400
	private static final ORcpPmrSRowMapper2 MAPPER_A1000400 = new ORcpPmrSRowMapper2();
	private static final String QUERY_A1000400 = "SELECT ROWID, a.* FROM A1000400 a";
	private static final String PK_A1000400 = " WHERE a.cod_mon =:crnVal AND a.cod_cia =:cmpVal ";

    @Override
    @Cacheable("PoC-ORcpPmrS-A5020500")
    public ORcpPmrS getORcpPmrSTypNam(ORcpPmrPK pk) {
	try {
	    return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }
    
    @Override
    @Cacheable("PoC-ORcpPmrS-A1000400")
    public ORcpPmrS getORcpPmrSCrnNam(ORcpPmrPK pk) {
	try {
	    return jdbc.queryForObject(QUERY_A1000400 + PK_A1000400, pk.asMap(), MAPPER_A1000400);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }    
    
    @Override
    @Cacheable("PoC-ORcdTsdS")
    public List<ORcdTsdS> getORcdTsdSList() {
	
	try {
	    return jdbc.query(QUERY, MAPPER2);

	} catch (EmptyResultDataAccessException e) {
	    return Collections.emptyList();
	}
	
    }
    
    @Override
    @Cacheable("PoC-ORcdTsdS2")
    public ORcdTsdS getORcdTsdS(ORcdTsdSPK pk) {
	
	try {
	    return jdbc.queryForObject(QUERY2 + PK2, pk.asMap(), MAPPER2);

	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }
    
    @Override
    @Cacheable("PoC-ORcpPmrS")
    public ORcpPmrS get(ORcpPmrPK pk) {
	
	return null;
    }

    @Override
    public String getDescription(ORcpPmrS o) {

	return null;
    }

    @Override
    public String getAbr(ORcpPmrS o) {
	
	return null;
    }

    @Override
    public List<ORcpPmrS> getAll() {
	
	return Collections.emptyList();
    }

    @Override
    public ORcpPmrS save(ORcpPmrS o) {
	
	return null;
    }

    @Override
    public int delete(ORcpPmrPK o) {
	
	return 0;
    }

    @Override
    public ORcpPmrS update(ORcpPmrS o) {
	
	return null;
    }





}
