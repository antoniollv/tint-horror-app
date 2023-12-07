package com.mapfre.tron.api.cmn.tgf.rcd.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.rcd.tmd.bo.ORcdTmdS;
import com.mapfre.tron.api.cmn.rcd.ucd.dl.ORcdTmdSDao;
import com.mapfre.tron.api.cmn.rcd.ucd.dl.ORcdTmdSPK;

@Repository
public class ORcdTmdSDaoImpl implements ORcdTmdSDao {

    private static final ORcdTmdSMapper MAPPER = new ORcdTmdSMapper();
    private static final String QUERY = "SELECT ROWID, a.* FROM a5020200 a ";
    private static final String PK = " where a.cod_cia = :cmpVal";
    private static final String PK2 = " where a.cod_cia = :cmpVal AND a.tip_gestor= :mnrTypVal";

    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    
	 /**
     * @author AMINJOU
     * 
     * Query collector type v1. 
     * It will return the type of collector. 
     * It will be mandatory send the parameters defined in the service and the 
     * service will response with the output object defined.
     * 
     * @param usrVal
     * @param lngVal
     * @param cmpVal
     * @param mnrTypVal
     * 
     * @return ORcdTmdS
     * 
     */
    @Cacheable("PoC-ORcdTmdS")
	@Override
	public ORcdTmdS getCollectorTypeV1(ORcdTmdSPK pk) {
    	try {
    	    return jdbc.queryForObject(QUERY + PK2, pk.asMap(), MAPPER);
    	} catch (EmptyResultDataAccessException e) {
    	    return null;
    	}
	}

    
    /**
     * @author AMINJOU
     * 
     * Query collector types v1. 
     * It will return the types of collector. 
     * It will be mandatory send the parameters defined in the service and the service 
     * will response with the output object defined.
     * 
     * @param usrVal
     * @param lngVal
     * @param cmpVal
     * 
     * @return List<ORcdTmdS>
     * 
     */
    @Cacheable("PoC-ORcdTmdSList")
	@Override
	public List<ORcdTmdS> getCollectorTypesV1(ORcdTmdSPK pk) {
    	try {
    	    return jdbc.query(QUERY + PK, pk.asMap(), MAPPER);
    	} catch (EmptyResultDataAccessException e) {
    	    return Collections.emptyList();
    	}
	}

	@Override
	public ORcdTmdS get(ORcdTmdSPK o) {
		
		return null;
	}

	@Override
	public String getDescription(ORcdTmdS o) {
		
		return null;
	}

	@Override
	public String getAbr(ORcdTmdS o) {
		
		return null;
	}

	@Override
	public List<ORcdTmdS> getAll() {
		
		return Collections.emptyList();
	}

	@Override
	public ORcdTmdS save(ORcdTmdS o) {
		
		return null;
	}

	@Override
	public int delete(ORcdTmdSPK o) {
		
		return 0;
	}

	@Override
	public ORcdTmdS update(ORcdTmdS o) {
		
		return null;
	}


}
