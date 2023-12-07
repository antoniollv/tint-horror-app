package com.mapfre.tron.api.trn.prp.dl.impl;

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
import com.mapfre.tron.api.trn.prp.dl.OTrnPrpDao;
import com.mapfre.tron.api.trn.prp.dl.OTrnPrpPK;

@Repository
public class OTrnPrpDaoImpl implements OTrnPrpDao {

    private final static String QUERY = "select * from df_trn_nwt_xx_prp a ";
    private final static String PK = "where prp_idn = :prpIdn and a.lng_val = :lngVal and ins_val = 'TRN'";
    private final static OTrnPrpRowMapper MAPPER = new OTrnPrpRowMapper();
    
    @Autowired @Qualifier("jdbcFetch1000") protected NamedParameterJdbcTemplate jdbcTemplateAll;
    @Autowired @Qualifier("jdbcFetch1") protected NamedParameterJdbcTemplate jdbcTemplateOne;
   
    @Override
    //@Cacheable("DescripcionFld")
    public OTrnGlsS get(OTrnPrpPK o) {
	try {
	    return jdbcTemplateOne.queryForObject(QUERY + PK, o.asMap(), MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    OTrnGlsS oTrnGlsS = new OTrnGlsS();
	    oTrnGlsS.setTemTxtT(new ArrayList<>(Arrays.asList(new OTrnGlsTxtS(null,null,"--nd--"))));
	    oTrnGlsS.setPrpIdn(o.getPrpIdn());
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
	throw new UnsupportedOperationException();
    }

    @Override
    //@Cacheable("DescripcionesFld")
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
    public int delete(OTrnPrpPK o) {
	throw new UnsupportedOperationException();
    }

    @Override
    public OTrnGlsS update(OTrnGlsS o) {
	throw new UnsupportedOperationException();
    }
    
}
