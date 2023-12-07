package com.mapfre.tron.api.pgm.dl;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.trn.pgm.bo.OTrnPgmS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.pgm.dl.IDlPgmDAO;
import com.mapfre.tron.api.pgm.dl.OPgmRowMapper;
import com.mapfre.tron.api.pgm.dl.OPgmAppValRowMapper;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class IDlPgmDAOImpl implements IDlPgmDAO {
    
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
    public OTrnPgmS get_pgmVal(BigDecimal cmpVal, String pgmVal, String usrLngVal) {
	// TODO Auto-generated method stub
	
	final String query = "SELECT a.COD_PGM "
                + "FROM PROGRAMAS a "
                + "WHERE a.COD_PGM = ? "
                + "AND a.COD_PGM IN (SELECT DISTINCT(b.cod_pgm) from G1010210 b WHERE b.cod_cia= ?)";
	
	try {
	    OTrnPgmS lvOTrnPgmS = jdbcTemplate.queryForObject(query,
                   new Object[]{
                	   	pgmVal,       
                	   	cmpVal
                                },
                   new OPgmRowMapper());

            return lvOTrnPgmS;
       }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "PGM", "PGM_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
       }
	
    }
    
    
    @Override
    public OTrnPgmS get_pgmAppVal(BigDecimal cmpVal, String appVal, String usrLngVal) {
	// TODO Auto-generated method stub
	
	
	final String query = "SELECT a.APP_VAL "
                           + "FROM DF_TRN_NWT_XX_PGM_APP a "
                           + "WHERE (a.dsb_app='N' OR a.dsb_app IS NULL) "
                           + "AND a.vld_dat = (SELECT MAX(b.vld_dat) "
                           + "FROM DF_TRN_NWT_XX_PGM_APP b "
                           + "WHERE b.app_val= a.app_val "
                           + "AND b.vld_dat <= TRUNC(SYSDATE)) "
                           + "AND a.APP_VAL = ?";
	try {
	    OTrnPgmS lvOTrnPgmS = jdbcTemplate.queryForObject(query,
                   new Object[]{
                	   	appVal,               	       
                                },
                   new OPgmAppValRowMapper());

            return lvOTrnPgmS;
       }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "PGM", "APP_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
       }
	
    }
    
    @Override
    public OTrnPgmS get_oprIdnVal(BigDecimal cmpVal, String oprIndVal, String usrLngVal) {
	// TODO Auto-generated method stub
	final String query = "SELECT a.TXT_IDN "
			   + "FROM DF_TRN_NWT_XX_TXT_SET a "
	                   + "WHERE a.lng_val = ? "
	                   + "AND a.ins_val IN (SELECT b.val_param FROM G0000000 b WHERE b.cod_param = 'COD_INSTALACION' AND b.cod_cia = ? ) "
	                   + "AND a.TXT_IDN = ? ";
	try {
	    OTrnPgmS lvOTrnPgmS = jdbcTemplate.queryForObject(query,
	        new Object[]{
	        		usrLngVal,
	        		cmpVal,
	        		oprIndVal,
	                    },
	        new OPgmOprIdnValRowMapper());

	    return lvOTrnPgmS;
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "PGM", "OPR_IDN_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }
}
