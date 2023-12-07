package com.mapfre.tron.api.lsf.ppd.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.prt.c.CPrt;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.lsf.ppd.bo.OLsfPpdS;
import com.mapfre.tron.api.cmn.bl.TwBlCmnBase;
import com.mapfre.tron.api.cmn.cmn.typ.dl.OCmnTypPK;
import com.mapfre.tron.api.cmn.cmn.typ.dl.impl.OCmnTypPRowMapper;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;

@Repository
public class DlLsfPpdDAOImpl extends TwBlCmnBase implements IDlLsfPpdDAO  {

	protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }
	
	@Override
	public OLsfPpdS getFilTypVal(BigDecimal cmpVal, BigDecimal lobVal, String filTypVal, String lngVal) {
		final String query = "SELECT a.COD_CIA, a.TIP_EXP FROM g7000100 a WHERE a.COD_RAMO=? AND a.TIP_EXP=? AND a.cod_cia=?";
		
		OLsfPpdS oLsfPpdS = null;
		
		try {
			oLsfPpdS = jdbcTemplate.queryForObject(query, new Object[] { lobVal, filTypVal, cmpVal },
					new OLsfPpdFilTypValRowMapper());
		} catch (EmptyResultDataAccessException e) {
			getErrorWithPrpIdn(lngVal, "LSF", "FIL_TYP_VAL", cmpVal);
		}
		
		return oLsfPpdS;
	}

	@Override
	public OCmnTypS getCmnTypS(String fldNam, String itcVal, String lngVal, BigDecimal cmpVal, String prpIdn) {
		final String query = "SELECT a.* FROM G1010031 a WHERE a.COD_RAMO = " + CPrt.GNC_LOB_VAL
				+ "AND a.cod_campo =? AND a.cod_valor =? AND a.cod_idioma =?  AND a.cod_cia =?";
		
		OCmnTypS oCmnTypS = null;
		
		try {
			
			oCmnTypS = jdbcTemplate.queryForObject(query, new Object[] {fldNam, itcVal, lngVal, cmpVal}, new OCmnTypPRowMapper());
			
		} catch (EmptyResultDataAccessException e) {
			getErrorWithPrpIdn(lngVal, "PPD", prpIdn, cmpVal);
		}
		
		return oCmnTypS;
	}
	
	
	
}
