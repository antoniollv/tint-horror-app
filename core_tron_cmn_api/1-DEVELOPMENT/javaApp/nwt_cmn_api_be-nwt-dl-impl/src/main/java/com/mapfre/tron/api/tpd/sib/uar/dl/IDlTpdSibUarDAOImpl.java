package com.mapfre.tron.api.tpd.sib.uar.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.tpd.sib.bo.OTpdSibS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class IDlTpdSibUarDAOImpl implements IDlTpdSibUarDAO {

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
    public OTpdSibS get_Usr(BigDecimal cmpVal, String cmpUsrVal,  String usrLngVal) {
    	
    	final String query = "select a.COD_USR_CIA "
    		+ "from G1002700 a "
    		+ "where a.COD_CIA = ? AND a.COD_USR_CIA = ?";
        
    	try {
    	return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	cmpVal, 
	                	cmpUsrVal,
	                },
	                new OTpdSibUarRowMapper());
	
	
		}catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SIB", "CMP_USR_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }
    
}
