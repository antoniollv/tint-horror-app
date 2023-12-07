package com.mapfre.tron.api.cmn.cmu.thl.dl.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.tron.api.cmn.cmu.thl.dl.OCmuThlDao;
import com.mapfre.tron.api.cmn.cmu.thl.dl.OCmuThlPK;

@Repository
public class OCmuThlDaoImpl implements OCmuThlDao {
	
	@Qualifier("npJdbcTemplate")
	@Autowired 
	private NamedParameterJdbcTemplate jdbc;
	private static final OCmuThlPRowMapper MAPPER = new OCmuThlPRowMapper();
	private static final String QUERY = "SELECT ROWID, a.* FROM a1000702 a";
	private static final String PK = " WHERE a.COD_CIA = :cmpVal AND a.cod_nivel3 = :thrLvlVal";


	@Override 
	@Cacheable("PoC-OCmuThlS")
	public OCmuThlS get(OCmuThlPK pk) {
		try {
			return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override 
	@Cacheable("PoC-OCmuThlSList")
	public List<OCmuThlS> getAll() {
		return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
	}

	@Override
	public String getDescription(OCmuThlS o) {
		if (o != null ) {
			return StringUtils.defaultString(o.getThrLvlNam());
		}
		return "";
	}

	@Override
	public String getAbr(OCmuThlS o) {
	    throw new UnsupportedOperationException();
	}

    @Override
    public OCmuThlS save(OCmuThlS o) {

        return null;
    }

    @Override
    public int delete(OCmuThlPK o) {

        return 0;
    }

    @Override
    public OCmuThlS update(OCmuThlS o) {

        return null;
    }
}

