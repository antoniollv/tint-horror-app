
package com.mapfre.tron.api.cmn.prt.lob.dl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.prt.lob.bo.OPrtLobS;
import com.mapfre.tron.api.cmn.prt.lob.dl.OPrtLobVdaDAO;
import com.mapfre.tron.api.cmn.prt.lob.dl.OPrtLobVdaPK;

import lombok.Data;

/**
 * The ISrPrtLobQryDAO dao repository.
 *
 * @author BRCHARL
 * @since 1.8
 * @version 13 ago. 2019 08:43:37
 *
 */
@Data
@Repository
public class OPrtLobVdaDAOImpl implements OPrtLobVdaDAO {

    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    private static final OPrtLobSRowMapper MAPPER = new OPrtLobSRowMapper();
    private static final String QUERY = "SELECT ROWID, a.* FROM a1002150 a ";
    private static final String PK = " WHERE a.cod_cia  = :cmpVal AND a.cod_ramo = :lobVal AND a.fec_validez <= :vldDat ";

    @Override 
    @Cacheable("PoC-OPrtLobS3")
    public OPrtLobS get(OPrtLobVdaPK pk) {
	
	return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);		
    }
	
    @Override 
    @Cacheable("PoC-OPrtLobSListAll3")
    public List<OPrtLobS> getAll() {
	return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
    }
    
    @Override
    @Cacheable("PoC-OPrtLobSList3")
    public List<OPrtLobS> getAllWithPK(OPrtLobVdaPK pk) {
	
	return jdbc.query(QUERY + PK, pk.asMap(), MAPPER);
	
    }

    @Override
    public String getDescription(OPrtLobS o) {
	throw new UnsupportedOperationException();
    }

    @Override
    public String getAbr(OPrtLobS o) {
	throw new UnsupportedOperationException();
    }

    @Override
    public OPrtLobS save(OPrtLobS o) {
        
        return null;
    }
    
    @Override
    public int delete(OPrtLobVdaPK o) {
        
        return 0;
    }
    
    @Override
    public OPrtLobS update(OPrtLobS o) {
        
        return null;
    }


}
