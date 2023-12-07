package com.mapfre.tron.api.cmn.sub.dl;

import java.math.BigDecimal;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.sub.bo.OCmnSubS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlCmnSubDAOImpl implements IDlCmnSubDAO {

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
    public OCmnSubS getAgrupacion(BigDecimal cmpVal, String sugVal, String usrLngVal){    	
    	
    	final String query ="SELECT a.SUG_VAL, a.CMP_VAL, a.LNG_VAL, a.VLD_DAT "
    				+ "FROM DF_CMN_NWT_XX_SUB_SUG a "
    				+ "WHERE a.CMP_VAL = ? AND a.SUG_VAL = ? AND a.LNG_VAL = ?";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		sugVal,
	                		usrLngVal,
	                },
	                new OCmnSugRowMapper());

        }
        catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "SUG_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    }           
    
    @Override
    public OCmnSubS getSublimite(BigDecimal cmpVal, String subVal, String usrLngVal){    	
    	
    	final String query ="SELECT a.SUB_VAL FROM DF_CMN_NWT_XX_SUB a where a.CMP_VAL = ? "
    		+ "AND a.DSB_ROW='N' AND a.SUB_VAL = ? AND a.LNG_VAL = ? AND a.VLD_DAT = (SELECT MAX(vld_dat) "
    		+ "FROM DF_CMN_NWT_XX_SUB b WHERE a.CMP_VAL = b.CMP_VAL AND a.SUB_VAL = b.SUB_VAL AND a.LNG_VAL = b.LNG_VAL)";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		subVal,
	                		usrLngVal,
	                },
	                new OCmnSubRowMapper());

        }
        catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "SUB_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    }   

    
}
