package com.mapfre.tron.api.cmn.lss.fli.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.lss.fli.bo.OLssFliS;
import com.mapfre.tron.api.cmn.lss.fli.dl.OLssFliDao;
import com.mapfre.tron.api.cmn.lss.fli.dl.OLssFliPK;

@Repository
public class OLssFliDaoImpl implements OLssFliDao {

	@Qualifier("npJdbcTemplate")
	@Autowired 
	private NamedParameterJdbcTemplate jdbc;
	private static final OLssFliSRowMapper MAPPER = new OLssFliSRowMapper();
	private static final String QUERY = "SELECT ROWID, a.* FROM g7000090 a";
	private static final String PK = " WHERE a.COD_CIA = :cmpVal AND a.tip_exp = :filTypVal ";

    @Override
    @Cacheable("PoC-OLssFliSDesc")
    public OLssFliS getOLssFliSTypNam(OLssFliPK pk) {
	try {
	    return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }
    @Override
    @Cacheable("PoC-OLssFliS")
    public OLssFliS get(OLssFliPK o) {

	return null;
    }

    @Override
    public String getDescription(OLssFliS o) {

	return null;
    }

    @Override
    public String getAbr(OLssFliS o) {

	return null;
    }

    @Override
    public List<OLssFliS> getAll() {

	return Collections.emptyList();
    }

    @Override
    public OLssFliS save(OLssFliS o) {

	return null;
    }

    @Override
    public int delete(OLssFliPK o) {

	return 0;
    }

    @Override
    public OLssFliS update(OLssFliS o) {

	return null;
    }


}
