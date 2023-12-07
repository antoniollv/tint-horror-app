package com.mapfre.tron.api.tpd.sef.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.tpd.sef.bo.OTpdSefS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class IDlTpdSefDAOImpl implements IDlTpdSefDAO {

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
    public OTpdSefS get(BigDecimal cmpVal, String rteVal, String usrLngVal) {
    	
    	final String query = "select a.CMP_VAL, a.RTE_VAL, a.LNG_VAL, a.RTE_NAM, a.DSB_ROW, a.USR_VAL, a.MDF_DAT from DF_TPD_NWT_XX_SEF a WHERE a.CMP_VAL = ? AND a.RTE_VAL = ? AND a.LNG_VAL = ?";
        
    	try {
    	return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	cmpVal, 
	                	rteVal,
	                	usrLngVal,
	                },
	                new OTpdSefRowMapper());
	
	
		}catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SEF", "SSV_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }

    @Override
    public OTpdSefS get_cnc(BigDecimal cmpVal, String rteCncVal, String usrLngVal) {
    
	final String query = "select a.CMP_VAL, a.RTE_CNC_VAL, a.LNG_VAL, a.RTE_CNC_NAM, a.DSB_ROW, a.USR_VAL, a.MDF_DAT from DF_TPD_NWT_XX_SEF_CNC a WHERE a.CMP_VAL = ? AND a.RTE_CNC_VAL = ? AND a.LNG_VAL = ?";
        
    	try {
    	return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	cmpVal, 
	                	rteCncVal,
	                	usrLngVal,
	                },
	                new OTpdSefCncRowMapper());
	
	
		}catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SEF", "RTE_CNC_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }
    
    
}
