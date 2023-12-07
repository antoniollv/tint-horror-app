package com.mapfre.tron.api.cmn.prt.sec.dl.impl;

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
import com.mapfre.nwt.trn.prt.sec.bo.OPrtSecS;
import com.mapfre.tron.api.cmn.prt.sec.dl.OPrtSecDao;
import com.mapfre.tron.api.cmn.prt.sec.dl.OPrtSecPK;

@Repository
public class OPrtSecDaoImpl implements OPrtSecDao {
	
	@Qualifier("npJdbcTemplate")
	@Autowired 
	private NamedParameterJdbcTemplate jdbc;
	private static final OPrtSecPRowMapper MAPPER = new OPrtSecPRowMapper();
	private static final String QUERY = "SELECT ROWID, a.* FROM A1000200 a";
	private static final String PK = " WHERE a.COD_CIA = :cmpVal AND a.COD_SECTOR = :secVal";
	private static final String QUERY_SECLIST = "SELECT a.cod_cia, a.cod_sector, a.nom_sector, a.abr_sector, a.num_pol_reserva, a.num_sol_reserva ,a.mca_subsector_estadistico, a.mca_emision FROM A1000200 a";
	private static final String PK_SECLIST = " WHERE a.COD_CIA = :cmpVal order by a.cod_sector";


	@Override @Cacheable("PoC-OPrtSecS")
	public OPrtSecS get(OPrtSecPK pk) {
		try {
			return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override @Cacheable("PoC-OPrtSecSListAll")
	public List<OPrtSecS> getAll() {
		return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
	}

	@Override
	public String getDescription(OPrtSecS o) {
		if (o != null) {
			return StringUtils.defaultString(o.getSecNam());
		}
		return "";
	}

	@Override
	public String getAbr(OPrtSecS o) {
		if (o != null) {
			return StringUtils.defaultString(o.getSecAbr());
		}
		return "";
	}

    @Override
    public OPrtSecS save(OPrtSecS o) {
        
        return null;
    }

    @Override
    public int delete(OPrtSecPK o) {
        
        return 0;
    }

    @Override
    public OPrtSecS update(OPrtSecS o) {
        
        return null;
    }

	@Override 
	@Cacheable("PoC-SecList-OPrtSecS")
	public List<OPrtSecS> getSecList(OPrtSecPK pk) {
		try {
			return jdbc.query(QUERY_SECLIST + PK_SECLIST, pk.asMap(), MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
		}
	}
}
