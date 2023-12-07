package com.mapfre.tron.api.ard.aby.dl.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.ply.c.CPly;
import com.mapfre.nwt.prt.c.CPrt;
import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvCPT;
import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvP;
import com.mapfre.nwt.trn.ard.abv.bo.OArdAbvS;
import com.mapfre.tron.api.ard.aby.dl.OArdAbvQryDAO;
import com.mapfre.tron.api.ard.aby.dl.OArdAbvQryPK;

import lombok.Data;

@Repository
@Data
public class OArdAbvQryDAOImpl implements OArdAbvQryDAO {

    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    private static final OArdAbvSRowMapper MAPPER = new OArdAbvSRowMapper();
    private static final String QUERY = "SELECT ROWID, a.* FROM g2990006 a "
    					+ "WHERE a.fec_validez IN (SELECT MAX(b.fec_validez) FROM g2990006 b "
    					+ "WHERE b.cod_cia = a.cod_cia AND a.cod_ramo = b.cod_ramo AND a.cod_modalidad = b.cod_modalidad "
    					+ "AND b.cod_campo = a.cod_campo) ";
    private static final String PK = " AND a.cod_cia = :cmpVal AND a.cod_ramo = NVL(:lobVal, "+CPrt.GNC_LOB_VAL+") "
    				+ "AND a.cod_modalidad = NVL(:mdtVal, "+CPly.GNC_MDT+") AND a.cod_campo = :fldNam ";

    @Override 
    @Cacheable("PoC-OArdAbvCPT")
    public OArdAbvCPT get(OArdAbvQryPK pk) {
	try {
	    List<OArdAbvS> lvOArdAbvS = jdbc.query(QUERY + PK, pk.asMap(), MAPPER);
	    
	    OArdAbvCPT oArdAbvCPT = new OArdAbvCPT();
	    oArdAbvCPT.setOArdAbvPT(new ArrayList<>());

	    if (lvOArdAbvS!=null && !lvOArdAbvS.isEmpty()) {
		for (OArdAbvS oArdAbvS: lvOArdAbvS) {
		    OArdAbvP oArdAbvP = new OArdAbvP();
		    oArdAbvP.setOArdAbvS(oArdAbvS);
		    oArdAbvCPT.getOArdAbvPT().add(oArdAbvP);
	        }
	    }
	    
	    return oArdAbvCPT;
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}		
    }
	
    @Override 
    public List<OArdAbvCPT> getAll() {
	throw new UnsupportedOperationException();
    }

    @Override
    public String getDescription(OArdAbvCPT o) {
	throw new UnsupportedOperationException();
    }

    @Override
    public String getAbr(OArdAbvCPT o) {
	throw new UnsupportedOperationException();
    }

    @Override
    public OArdAbvCPT save(OArdAbvCPT o) {
        
        return null;
    }
    
    @Override
    public int delete(OArdAbvQryPK o) {
        
        return 0;
    }
    
    @Override
    public OArdAbvCPT update(OArdAbvCPT o) {
        
        return null;
    }


}
