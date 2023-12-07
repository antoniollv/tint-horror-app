package com.mapfre.tron.api.cmn.mkd.dl;

import java.math.BigDecimal;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.mkd.bo.OCmnMkdS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlCmnMkdDAOImpl implements IDlCmnMkdDao{
    
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
    public OCmnMkdS get(BigDecimal cmpVal, BigDecimal mkdVal, String lngVal, Date vldDat,  String usrLngVal) {

	final String query = "SELECT a.CMP_VAL, a.MKD_VAL, a.LNG_VAL, a.VLD_DAT FROM DF_CMN_NWT_XX_MKD a  WHERE "
		+ "a.CMP_VAL = ? AND a.LNG_VAL = ? AND a.MKD_VAL = ? AND a.VLD_DAT = (SELECT MAX(VLD_DAT)  FROM DF_CMN_NWT_XX_MKD b "
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
		    		mkdVal,
		    		vldDat,
		    		vldDat
		    		
	    },
		    new OCmnMkdRowMapper());


	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MKD", "MKD_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }


@Override
public OCmnMkdS get_002(BigDecimal cmpVal, BigDecimal mkdVal, Date vldDat,  String usrLngVal) {
    
    final String query = "SELECT a.CMP_VAL, a.MKD_VAL, a.MKT_VAL, a.LNG_VAL, a.VLD_DAT FROM DF_CMN_NWT_XX_MKD a  WHERE "
		+ "a.CMP_VAL = ? AND a.LNG_VAL = ? AND DSB_ROW = 'N' AND a.MKD_VAL = ? AND a.VLD_DAT = (SELECT MAX(VLD_DAT)  FROM DF_CMN_NWT_XX_MKD b "
		+ "where b.CMP_VAL = a.CMP_VAL "
		+ "AND b.MKD_VAL = a.MKD_VAL "
		+ "AND b.MKT_VAL = a.MKT_VAL "  
		+ "AND b.LNG_VAL = a.LNG_VAL "  
		+ "AND b.DSB_ROW = 'N' " 
		+ "AND (? IS NULL OR " 
		+ "b.VLD_DAT <= ?))";
		
	
	try {
	    return jdbcTemplate.queryForObject(query,
		    new Object[]{
		    		cmpVal,
		    		usrLngVal,
		    		mkdVal,
		    		vldDat,
		    		vldDat
    		
	    },
		    new OCmnMkdRowMapper());

	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MKD", "MKD_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
}

}
