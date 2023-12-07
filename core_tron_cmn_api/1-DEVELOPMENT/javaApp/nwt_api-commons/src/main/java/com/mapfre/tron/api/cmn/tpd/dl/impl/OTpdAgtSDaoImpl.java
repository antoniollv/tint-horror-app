package com.mapfre.tron.api.cmn.tpd.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.tpd.agt.bo.OTpdAgtS;
import com.mapfre.tron.api.cmn.tpd.dl.OTpdAgtSDao;
import com.mapfre.tron.api.cmn.tpd.dl.OTpdAgtSPK;

@Repository
public class OTpdAgtSDaoImpl implements OTpdAgtSDao {

    private static final OTpdAgtMapper MAPPER = new OTpdAgtMapper();
    private static final String QUERY = "SELECT ROWID, a.* FROM a1001343 a ";
    private static final String PK = " where a.cod_cia = :cmpVal";
    private static final String PK2 = " where a.cod_cia = :cmpVal AND a.tip_agt = :agnTypVal";

    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    @Cacheable("PoC-OTpdAgtSList")
    public List<OTpdAgtS> getAgt(OTpdAgtSPK pk) {
	try {
	    return jdbc.query(QUERY + PK, pk.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    return Collections.emptyList();
	}
    }

    @Override
    @Cacheable("PoC-OTpdAgtS")
    public OTpdAgtS getAgentType(OTpdAgtSPK pk) {
	try {
	    return jdbc.queryForObject(QUERY + PK2, pk.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }

}
