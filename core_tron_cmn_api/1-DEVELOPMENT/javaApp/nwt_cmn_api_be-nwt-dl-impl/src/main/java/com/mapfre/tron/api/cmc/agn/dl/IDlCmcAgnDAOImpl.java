package com.mapfre.tron.api.cmc.agn.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.thp.cmc.bo.OThpCmcS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class IDlCmcAgnDAOImpl implements IDlThpCmcAgnDAO {

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
    public OThpCmcS getCodTratamiento(BigDecimal cmpVal, String pocVal, String usrLngVal) {	
    	
    	final String query ="SELECT a.COD_TRATAMIENTO FROM G9990200 a WHERE a.COD_TRATAMIENTO = ?";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	pocVal
	                	
	                },
	                new OThpCmcAgnComRowMapper());


        }
        catch (EmptyResultDataAccessException e) {
		BigDecimal codError = new BigDecimal(20001);
		OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "POC_VAL", cmpVal);
		NwtException exception = new NwtException(error.getErrIdnVal());
		exception.add(error);
		throw exception;
        }
    }
    
   

}
