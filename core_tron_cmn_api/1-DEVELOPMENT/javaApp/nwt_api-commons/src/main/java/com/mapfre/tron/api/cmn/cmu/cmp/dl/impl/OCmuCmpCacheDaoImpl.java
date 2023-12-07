package com.mapfre.tron.api.cmn.cmu.cmp.dl.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.commons.dl.NewtronEmptySqlParameterSource;
import com.mapfre.nwt.trn.cmu.cmp.bo.OCmuCmpS;
import com.mapfre.tron.api.cmn.cmu.cmp.dl.OCmuCmpDao;
import com.mapfre.tron.api.cmn.cmu.cmp.dl.OCmuCmpPK;

@Repository
public class OCmuCmpCacheDaoImpl implements OCmuCmpDao {

    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;
    private static final OCmuCmpPRowMapper MAPPER = new OCmuCmpPRowMapper();
    private static final String QUERY = "SELECT ROWID, a.* FROM a1000900 a";
    private static final String PK = " WHERE a.COD_CIA = :cmpVal";
    private static final String QUERY2 = "SELECT ROWID, a.* FROM g1000900 a";

    @Override
    @Cacheable("PoC-OCmuCmpS")
    public OCmuCmpS get(OCmuCmpPK pk) {
	try {
	    OCmuCmpS s = jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
	    jdbc.queryForObject(QUERY2 + PK, pk.asMap(), new OCmuCmpPRowMapperG1000900(s));
	    return s;
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }

    @Override
    @Cacheable("PoC-OCmuCmpSList")
    public List<OCmuCmpS> getAll() {
	List<OCmuCmpS> ls = jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
	Map<OCmuCmpPK, OCmuCmpS> map = new HashMap<>();
	for (OCmuCmpS s : ls) {
	    map.put(OCmuCmpPK.get(s), s);
	}
	jdbc.query(QUERY2, NewtronEmptySqlParameterSource.I, new OCmuCmpPRowMapperG1000900(map));
	return ls;
    }

    @Override
    @Cacheable("PoC-OCmuCmpS2")
    public OCmuCmpS get2(OCmuCmpPK pk) {
	try {
	    return jdbc.queryForObject(QUERY + PK, pk.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }

    @Override
    @Cacheable("PoC-OCmuCmpSList2")
    public List<OCmuCmpS> getAll2() {
	try {
	    return jdbc.query(QUERY, NewtronEmptySqlParameterSource.I, MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    return Collections.emptyList();
	}
    }

    @Override
    public String getDescription(OCmuCmpS o) {
	if (o != null) {
	    return StringUtils.defaultString(o.getCmpNam());
	}
	return "";
    }

    @Override
    public String getAbr(OCmuCmpS o) {
	if (o != null) {
	    return StringUtils.defaultString(o.getCmpAbr());
	}
	return "";
    }

    @Override
    public OCmuCmpS save(OCmuCmpS o) {

	return null;
    }

    @Override
    public int delete(OCmuCmpPK o) {

	return 0;
    }

    @Override
    public OCmuCmpS update(OCmuCmpS o) {

	return null;
    }
}
