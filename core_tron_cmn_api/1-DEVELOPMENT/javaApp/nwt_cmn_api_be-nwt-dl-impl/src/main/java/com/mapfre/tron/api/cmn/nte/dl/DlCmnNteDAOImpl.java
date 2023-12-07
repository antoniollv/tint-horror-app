package com.mapfre.tron.api.cmn.nte.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlCmnNteDAOImpl implements IDlCmnNteDAO{
    
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	DlTrnErr lvDlTrnErr;
	    
	@Autowired
	public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
	    jdbcTemplate = new JdbcTemplate(dataSource);
	    final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
	    jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
	}

    // Seleccionar codigo nota versión 3
    @Override
    public OCmnNteS get_003(BigDecimal cmpVal, String nteVal, String usrLngVal) {

	final String query ="SELECT a.COD_NOTA "
			+ "FROM A1003001 a "
			+ "WHERE a.COD_NOTA=? "
			+ "AND a.COD_CIA = ? AND a.MCA_INH='N' AND MCA_NOTA_ENRIQUECIDA='S'";
	try {
	    return jdbcTemplate.queryForObject(query,
                new Object[]{
                		nteVal,
                		cmpVal
                		
             },
                new OCmnNteRowMapper());


	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTE", "NTE_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

}
