package com.mapfre.tron.api.acf.dag.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.acf.dag.bo.OAcfDagS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;


@Data
@Repository
public class IDlAcfDagDAOImpl implements IDlAcfDagDAO{
    
    protected JdbcTemplate jdbcTemplate;

    @Autowired
	DlTrnErr lvDlTrnErr;

    @Autowired
    public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }   
    
    // Hallar moneda
    @Override
    public OAcfDagS get_moneda(BigDecimal cmpVal, BigDecimal crnVal, String usrLngVal) {
	final String query ="SELECT a.COD_MON FROM A1000400 a "
		+ "WHERE a.COD_CIA=? AND COD_MON=? "
		+ "AND a.COD_MON = a.COD_MON AND a.COD_CIA = ?" ;
	
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{	        
	                		cmpVal,
	                		crnVal,
	                		cmpVal
	                },
	                new OAcfDagMonedaRowMapper());
	

	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "DAG", "CRN_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

    @Override
    public OAcfDagS get_cobro(BigDecimal cmpVal, String cloPymCncVal, String usrLngVal){
	final String query ="select a.cod_cto_cob_pag from a5021101 a, a5100121 b "
		+ "where a.cod_cia=? and a.cod_cto_cob_pag=? "  
		+ "AND a.tip_cto = 'AC' AND a.cod_agrup_ctable = b.cod_agrup_ctable "  
		+ "AND a.cod_cto_cob_pag = b.cod_cto_ctable AND a.cod_agrup_ctable = 'PC' "  
		+ "AND a.COD_CIA =? AND a.COD_CIA = b.COD_CIA " ;
	
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{	        
	                		cmpVal,
	                		cloPymCncVal,
	                		cmpVal
	                },
	                new OAcfDagCobroRowMapper());
	

	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "DAG", "CLO_PYM_CNC_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
	
    }


}
