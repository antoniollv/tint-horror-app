package com.mapfre.tron.api.trn.err.dl.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.NewtronEmptySqlParameterSource;
import com.mapfre.tron.api.trn.err.dl.OTrnErrDao;
import com.mapfre.tron.api.trn.err.dl.OTrnErrPK;

@Repository
public class OTrnErrDaoImpl implements OTrnErrDao {

    private static final String QUERY = "select * from g1010020 ";
    private static final String PK = "where cod_mensaje = :errVal and cod_idioma = :lngVal and cod_cia = :cmpVal";
    private static final OTrnErrRowMapper MAPPER = new OTrnErrRowMapper();
    
    @Autowired @Qualifier("jdbcFetch1000") protected NamedParameterJdbcTemplate jdbcTemplateAll;
    @Autowired @Qualifier("jdbcFetch1") protected NamedParameterJdbcTemplate jdbcTemplateOne;

    @Override
    //@Cacheable("DescripcionError")
    public OTrnErrS get(OTrnErrPK o) {
	try {
	    return jdbcTemplateOne.queryForObject(QUERY + PK, o.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    return new OTrnErrS(null,null,o.getErrVal(),"-- nd --");
	}
    }


    @Override
    public String getDescription(OTrnErrS o) {
	if (o != null)
	    return StringUtils.defaultString(o.getErrNam());
	return "";
    }


    @Override
    public String getAbr(OTrnErrS o) {
	throw new UnsupportedOperationException();
    }


    @Override
    //@Cacheable("DescripcionesError")
    public List<OTrnErrS> getAll() {
	try {
	    return jdbcTemplateAll.query(QUERY,NewtronEmptySqlParameterSource.I, MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    return Arrays.asList(new OTrnErrS(null,null,null,"-- nd --"));
	}
    }


    @Override
    public OTrnErrS save(OTrnErrS o) {
	throw new UnsupportedOperationException();
    }


    @Override
    public int delete(OTrnErrPK o) {
	throw new UnsupportedOperationException();
    }


    @Override
    public OTrnErrS update(OTrnErrS o) {
	throw new UnsupportedOperationException();
    }

}
