package com.mapfre.tron.api.tpd.sib.dl;

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
public class IDlTpdSibDAOImpl implements IDlTpdSibDAO {

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
    public OTpdSibS get_Rol(BigDecimal cmpVal, String rolVal, String usrLngVal) {
    	
    	final String query = "select a.COD_ROL "
    		+ "from G1010200 a "
    		+ "where a.COD_CIA = ? AND a.COD_ROL = ?";
        
    	try {
    	return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	cmpVal, 
	                	rolVal,
	                },
	                new OTpdSibRowMapper());
	
	
		}catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SIB", "ROL_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }
    
}
