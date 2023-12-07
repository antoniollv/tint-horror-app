	package com.mapfre.tron.api.cmn.psf.psf.dl.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.psf.psf.bo.OPsfPsfS;
import com.mapfre.tron.api.cmn.psf.psf.dl.OPsfPsfDao;
import com.mapfre.tron.api.cmn.psf.psf.dl.OPsfPsfPK;

@Repository
public class OPsfPsfDaoImpl implements OPsfPsfDao {
	
	@Qualifier("npJdbcTemplate")
	@Autowired 
	private NamedParameterJdbcTemplate jdbc;
	private static final OPsfPsfPRowMapper MAPPER = new OPsfPsfPRowMapper();
	private static final String QUERY = "SELECT ROWID, a.* FROM a1001402 a";
	private static final String PK = " WHERE a.COD_CIA = :cmpVal AND a.COD_FRACC_PAGO = :pmsVal";
	private static final String QUERY2 = "SELECT ROWID, a.* FROM a1001410 a";
	private static final String PK2 = " WHERE a.COD_CIA = :cmpVal AND a.COD_PLAN_PAGO = :pmsVal";

	@Override @Cacheable("PoC-OPsfPsfS")
	public OPsfPsfS get(OPsfPsfPK pk) {
			OPsfPsfS s = jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
			jdbc.queryForObject(QUERY2 + PK2, pk.asMap(), new OPsfPsfPRowMapperA1001410(s));
			return s;
	}
	
	@Override @Cacheable("PoC-OPsfPsfSListAll")
	public List<OPsfPsfS> getAll() {
		List<OPsfPsfS> ls = jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
		Map<OPsfPsfPK, OPsfPsfS> map = new HashMap<>();
		for(OPsfPsfS s : ls) {
			map.put(OPsfPsfPK.get(s), s);
		}
		jdbc.query(QUERY2, NewtronEmptySqlParameterSource.I, new OPsfPsfPRowMapperA1001410(map));
		return ls;
	}

	@Override
	public String getDescription(OPsfPsfS o) {
		if (o != null) {
			return StringUtils.defaultString(o.getPmsNam());
		}
		return "";
	}

	@Override
	public String getAbr(OPsfPsfS o) {
		if (o != null) {
			return StringUtils.defaultString(o.getPmsAbr());
		}
		return "";
	}

    @Override
    public OPsfPsfS save(OPsfPsfS o) {
        
        return null;
    }

    @Override
    public int delete(OPsfPsfPK o) {
        
        return 0;
    }

    @Override
    public OPsfPsfS update(OPsfPsfS o) {
        
        return null;
    }
}

