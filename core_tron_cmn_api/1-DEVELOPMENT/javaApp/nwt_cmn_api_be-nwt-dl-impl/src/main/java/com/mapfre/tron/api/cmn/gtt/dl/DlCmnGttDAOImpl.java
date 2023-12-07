package com.mapfre.tron.api.cmn.gtt.dl;

import java.math.BigDecimal;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.gtt.bo.OCmnGttS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlCmnGttDAOImpl implements IDlCmnGttDao{
    
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
	jdbcTemplate = new JdbcTemplate(dataSource);
	final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
	jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }

    @Override
    public OCmnGttS get(BigDecimal cmpVal, BigDecimal gttVal, String lngVal, Date vldDat, String usrLngVal) {
	final String query = "SELECT a.CMP_VAL, a.GTT_VAL, a.LNG_VAL, a.VLD_DAT FROM DF_CMN_NWT_XX_GTT a WHERE "
		+ "a.CMP_VAL = ? AND a.LNG_VAL = ? AND a.GTT_VAL = ? AND a.VLD_DAT = (SELECT MAX(VLD_DAT) FROM DF_CMN_NWT_XX_GTT b "
		+ "where b.CMP_VAL = a.CMP_VAL "
		+ "AND b.GTT_VAL = a.GTT_VAL " 
		+ "AND b.LNG_VAL = a.LNG_VAL " 
		+ "AND b.DSB_ROW = 'N' " 
		+ "AND (? IS NULL OR " 
		+ "b.VLD_DAT <= ?))";
	
	try {
	    
	    return jdbcTemplate.queryForObject(query,
		    new Object[]{
		    		cmpVal,
		    		lngVal,
		    		gttVal,
		    		vldDat,
		    		vldDat
		    		
	    },
		    new OCmnGttRowMapper());


	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "GTT", "GTT_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

}
