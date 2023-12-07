package com.mapfre.tron.api.cmn.thp.cst.dl.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.tpd.csg.bo.OTpdCsgS;
import com.mapfre.tron.api.cmn.thp.cst.dl.OTpdCsgSDao;
import com.mapfre.tron.api.cmn.thp.cst.dl.OTpdCsgSPK;

@Repository
public class OTpdCsgSDaoImpl implements OTpdCsgSDao {

    private static final OTpdCsgSMapper MAPPER = new OTpdCsgSMapper();
    private static final String QUERY = "select ROWID, a.* FROM df_thp_nwt_xx_cgt a ";
    private static final String PK = " WHERE a.cgt_typ_val = '5' AND a.cmp_val = :cmpVal AND a.dsb_row = 'N' AND a.lng_val = :lngVal ";
    private static final String PK2 = " WHERE a.cgt_typ_val = '5' AND a.cmp_val = :cmpVal AND a.fld_val = :csgTypVal AND a.dsb_row = 'N' AND a.lng_val = :lngVal ";

    @Qualifier("npJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Cacheable("PoC-OTpdCsgS")
    @Override
    public OTpdCsgS getOTpdCsgS(OTpdCsgSPK pk) {
	try {
	    return jdbc.queryForObject(QUERY + PK2, pk.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    return null;
	}
    }

    @Cacheable("PoC-OTpdCsgSList")
    @Override
    public List<OTpdCsgS> getOTpdCsgSList(OTpdCsgSPK pk) {
	try {
	    return jdbc.query(QUERY + PK, pk.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    return Collections.emptyList();
	}
    }

    @Override
    public OTpdCsgS get(OTpdCsgSPK o) {
	 
	return null;
    }

    @Override
    public String getDescription(OTpdCsgS o) {
	 
	return null;
    }

    @Override
    public String getAbr(OTpdCsgS o) {
	 
	return null;
    }

    @Override
    public List<OTpdCsgS> getAll() {
	 
	return Collections.emptyList();
    }

    @Override
    public OTpdCsgS save(OTpdCsgS o) {
	 
	return null;
    }

    @Override
    public int delete(OTpdCsgSPK o) {
	 
	return 0;
    }

    @Override
    public OTpdCsgS update(OTpdCsgS o) {
	 
	return null;
    }

}
