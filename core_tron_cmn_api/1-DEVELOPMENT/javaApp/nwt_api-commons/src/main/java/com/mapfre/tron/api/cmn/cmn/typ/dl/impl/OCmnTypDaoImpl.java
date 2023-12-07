package com.mapfre.tron.api.cmn.cmn.typ.dl.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.prt.c.CPrt;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnCltPK;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnTypDao;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnTypPK;

@Repository
public class OCmnTypDaoImpl implements OCmnTypDao {

    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    	
	private static final OCmnTypPRowMapper MAPPER = new OCmnTypPRowMapper();
	private static final OCmnTypPRowMapper2 MAPPER2 = new OCmnTypPRowMapper2();
	private static final String QUERY = "SELECT ROWID, a.* FROM G1010031 a WHERE a.COD_RAMO = " + CPrt.GNC_LOB_VAL;
	private static final String QUERY2 = "SELECT ROWID, a.* FROM G1010031 a WHERE a.COD_RAMO = :lobVal ";
	private static final String PK = " AND a.cod_campo = :fldNam AND a.cod_valor = :itcVal AND a.cod_idioma = :lngVal  AND a.cod_cia = :cmpVal";
	private static final String QUERY_AND_PK_FOR_LIST = "SELECT ROWID, a.* FROM G1010031 a WHERE a.COD_RAMO = :lobVal AND a.cod_campo = :fldNam AND a.cod_idioma = :lngVal AND a.cod_cia = :cmpVal";
	
	private static final String QUERY_TYPES = "SELECT ROWID, a.* FROM A5020200 a ";
	private static final String QUERY_PK_TYPES = " WHERE a.cod_cia = :cmpVal ";
	private static final String QUERY_PK_TYPE = " WHERE a.tip_gestor = :valVal AND a.cod_cia = :cmpVal";

	@Override 
	@Cacheable("PoC-OCmnTypS")
    public OCmnTypS get(OCmnTypPK pk) {
	try {
	    if (pk.asMap().containsKey("lobVal") && pk.asMap().get("lobVal") != null) {
		return jdbc.queryForObject(QUERY2 + PK, pk.asMap(), MAPPER);
	    } else {
		return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
	    }
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }
	
	@Override 
	@Cacheable("PoC-OCmnTypSListAll")
	public List<OCmnTypS> getAllWithPK(OCmnTypPK pk) {
		try {
			return jdbc.query(QUERY_AND_PK_FOR_LIST, pk.asMap(), MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}		
	}
	
	@Override 
	@Cacheable("PoC-OCmnTypSgetAllTypes")
	public List<OCmnTypS> getAllTypes(OCmnTypPK pk) {
		try {
			return jdbc.query(QUERY_TYPES + QUERY_PK_TYPES, pk.asMap(), MAPPER2);
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}		
	}
	
	@Override 
	@Cacheable("PoC-OCmnTypSList")
	public List<OCmnTypS> getAll() {
		return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
	}
	
    @Override
    @Cacheable("PoC-OCmnTypSgetType")
    public OCmnTypS getCollectorType(OCmnCltPK pk) {
	try {
	    return jdbc.queryForObject(QUERY_TYPES + QUERY_PK_TYPE, pk.asMap(), MAPPER2);
	    
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }

	@Override
	public String getDescription(OCmnTypS o) {
		if (o != null) {
			return StringUtils.defaultString(o.getItcNam());
		}
		return "";
	}

	@Override
	public String getAbr(OCmnTypS o) {
	    throw new UnsupportedOperationException();
	}

        @Override
        public OCmnTypS save(OCmnTypS o) {

            return null;
        }
        
        @Override
        public int delete(OCmnTypPK o) {

            return 0;
        }
        
        @Override
        public OCmnTypS update(OCmnTypS o) {

            return null;
        }

}
