package com.mapfre.tron.api.cmn.ard.fxa.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.ard.fxa.bo.OArdFxaS;
import com.mapfre.tron.api.cmn.ard.fxa.dl.OArdFxaDao;
import com.mapfre.tron.api.cmn.ard.fxa.dl.OArdFxaPK;

/**
 * The fixed attributes controller.
 *
 * @author arquitectura - izhan del rio
 * @since 1.8
 * @version 12 Nov 2021 - 16:17:02
 *
 */
@Repository
public class OArdFxaDaoImpl implements OArdFxaDao {

    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    	
	private static final OArdFxaPRowMapper MAPPER = new OArdFxaPRowMapper();
	
	private static final String QUERY = "SELECT * FROM g2990015 g";
	
	private static final String PK = " WHERE g.cod_cia = :cmpVal";
	
	
	
	



	@Override 
	@Cacheable("PoC-OArdFxaSListAll")
	public List<OArdFxaS> getAllWithPK(OArdFxaPK pk) {
		try {
			return jdbc.query(QUERY+PK, pk.asMap(), MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}		
	}
	
	
    

	@Override
	public String getDescription(OArdFxaS o) {
	    throw new UnsupportedOperationException();
	}

	@Override
	public String getAbr(OArdFxaS o) {
	    throw new UnsupportedOperationException();
	}

        @Override
        public OArdFxaS save(OArdFxaS o) {

            return null;
        }
        
        @Override
        public int delete(OArdFxaPK o) {

            return 0;
        }
        
        @Override
        public OArdFxaS update(OArdFxaS o) {

            return null;
        }

	
	@Override
	public OArdFxaS get(OArdFxaPK o) {
	    
	    return null;
	}




	@Override
	public List<OArdFxaS> getAll() {
	    
	    return Collections.emptyList();
	}


}
