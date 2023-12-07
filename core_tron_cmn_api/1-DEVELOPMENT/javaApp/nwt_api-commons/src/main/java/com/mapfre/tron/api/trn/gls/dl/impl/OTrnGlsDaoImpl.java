package com.mapfre.tron.api.trn.gls.dl.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.trn.gls.bo.OTrnGlsTxtS;
import com.mapfre.nwt.trn.trn.gls.bo.OTrnGlsS;
import com.mapfre.tron.api.cmn.dl.NewtronEmptySqlParameterSource;
import com.mapfre.tron.api.trn.gls.dl.OTrnGlsDao;
import com.mapfre.tron.api.trn.gls.dl.OTrnGlsPK;

@Repository
public class OTrnGlsDaoImpl implements OTrnGlsDao {

    private static final String QUERY = "select * from df_trn_nwt_xx_gls a ";
    private static final String PK = "where a.tem_VAL = :temVal and a.lng_val = :lngVal and a.tem_typ = 'TXT' and a.ins_val = 'TRN'";
    private static final OTrnGlsRowMapper MAPPER = new OTrnGlsRowMapper();
    
    @Autowired @Qualifier("jdbcFetch1000") protected NamedParameterJdbcTemplate jdbcTemplateAll;
    @Autowired @Qualifier("jdbcFetch1") protected NamedParameterJdbcTemplate jdbcTemplateOne;
    
    @Override
    //@Cacheable("Descripcion")
    public OTrnGlsS get(OTrnGlsPK o) {
	try {
	    return jdbcTemplateOne.queryForObject(QUERY + PK, o.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    OTrnGlsS oTrnGlsS = new OTrnGlsS();
	    oTrnGlsS.setTemTxtT(new ArrayList<>(Arrays.asList(new OTrnGlsTxtS(null,null,"--nd--"))));
	    oTrnGlsS.setTemVal(o.getTemVal());
	    return oTrnGlsS;
	}
    }

    @Override
    public String getDescription(OTrnGlsS o) {
	if (o != null)
	    return StringUtils.defaultString(o.getTemTxtT().get(0).getTxtVal());
	return "";
    }

    @Override
    public String getAbr(OTrnGlsS o) {
	
	 
	return null;
    }

    @Override
    //@Cacheable("Descripciones")
    public List<OTrnGlsS> getAll() {
	try {
	    return jdbcTemplateAll.query(QUERY,NewtronEmptySqlParameterSource.I, MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    return new ArrayList<>();
	}
    }

    @Override
    public OTrnGlsS save(OTrnGlsS o) {
	throw new UnsupportedOperationException();
    }

    @Override
    public int delete(OTrnGlsPK o) {
	throw new UnsupportedOperationException();
    }

    @Override
    public OTrnGlsS update(OTrnGlsS o) {
	throw new UnsupportedOperationException();
    }

    
}
