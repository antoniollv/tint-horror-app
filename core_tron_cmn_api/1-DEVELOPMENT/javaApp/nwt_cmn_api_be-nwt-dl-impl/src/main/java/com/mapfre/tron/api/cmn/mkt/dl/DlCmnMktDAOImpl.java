package com.mapfre.tron.api.cmn.mkt.dl;

import java.math.BigDecimal;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.mkt.bo.OCmnMktS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlCmnMktDAOImpl implements IDlCmnMktDao{
    
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
    public OCmnMktS get(BigDecimal cmpVal, BigDecimal mktVal, String lngVal, Date vldDat, String usrLngVal) {
	
	final String query = "SELECT a.CMP_VAL, a.MKT_VAL, a.LNG_VAL, a.VLD_DAT FROM DF_CMN_NWT_XX_MKT a WHERE "
		+ "a.CMP_VAL = ? AND a.LNG_VAL = ? AND a.MKT_VAL = ? AND a.VLD_DAT = (SELECT MAX(VLD_DAT)  FROM DF_CMN_NWT_XX_MKT b "
		+ "where b.CMP_VAL = a.CMP_VAL "
		+ "AND b.MKT_VAL = a.MKT_VAL "  
		+ "AND b.LNG_VAL = a.LNG_VAL " 
		+ "AND b.DSB_ROW = 'N' " 
		+ "AND (? IS NULL OR "
		+ "b.VLD_DAT <= ?))";
	
	try {
	    return jdbcTemplate.queryForObject(query,
		    new Object[]{
		    		cmpVal,
		    		lngVal,
		    		mktVal,
		    		vldDat,
		    		vldDat
		    		
	    },
		    new OCmnMktRowMapper());

	     
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MKT", "MKT_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
	
    }

    @Override
    public OCmnMktS get_002(BigDecimal cmpVal, BigDecimal mktVal, String lngVal, Date vldDat, String usrLngVal) {
	
	final String query = "SELECT a.CMP_VAL, a.MKT_VAL, a.LNG_VAL, a.VLD_DAT FROM DF_CMN_NWT_XX_MKT a WHERE "
		+ "a.CMP_VAL = ? AND a.LNG_VAL = ? AND a.MKT_VAL = ? AND a.DSB_ROW = 'N' AND a.VLD_DAT = (SELECT MAX(VLD_DAT) FROM DF_CMN_NWT_XX_MKT b "
		+ "where b.CMP_VAL = a.CMP_VAL "
		+ "AND b.MKT_VAL = a.MKT_VAL "  
		+ "AND b.LNG_VAL = a.LNG_VAL " 
		+ "AND b.DSB_ROW = 'N' " 
		+ "AND (? IS NULL OR "
		+ "b.VLD_DAT <= ?))";

	try {
	    return jdbcTemplate.queryForObject(query,
		    new Object[]{
		    		cmpVal,
		    		lngVal,
		    		mktVal,
		    		vldDat,
		    		vldDat

		    		
	    },
		    new OCmnMktRowMapper());
	    

	}

	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MKT", "MKT_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}

	
    }

}
