package com.mapfre.tron.api.rcd.exd.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.rcd.exd.bo.ORcdExdS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlRcdExdDAOImpl implements IDlRcdExdDAO {

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
    public ORcdExdS get(BigDecimal cmpVal, BigDecimal agnVal, String usrLngVal) {

		final String query = "SELECT a.COD_CIA, a.COD_TERCERO "
			+ "FROM v1001390_trn a " 
		    + "WHERE a.cod_cia = ? AND a.cod_tercero = ? AND a.cod_act_tercero = 2";
	
	        try {
	            return jdbcTemplate.queryForObject(query,
		                new Object[]{
		                		cmpVal,
		                		agnVal,
		                },
		                new ORcdExdRowMapper());

		         
	        }
	        catch (EmptyResultDataAccessException e) {
				BigDecimal codError = new BigDecimal(20001);
			    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "EXD", "AGN_VAL", cmpVal);
			    NwtException exception = new NwtException(error.getErrIdnVal());
			    exception.add(error);
			    throw exception;
	        }
    }
    @Override
    public ORcdExdS getSector(BigDecimal cmpVal, BigDecimal secVal, String usrLngVal) {

		final String query = "SELECT a.COD_CIA, a.COD_SECTOR "
			+ "FROM a1000200 a " 
		    + "WHERE a.cod_cia = ? AND a.cod_sector = ?";
	
	        try {
	            return jdbcTemplate.queryForObject(query,
		                new Object[]{
		                		cmpVal,
		                		secVal,
		                },
		                new ORcdExdSectorRowMapper());


	        }
	        catch (EmptyResultDataAccessException e) {
				BigDecimal codError = new BigDecimal(20001);
			    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "EXD", "SEC_VAL", cmpVal);
			    NwtException exception = new NwtException(error.getErrIdnVal());
			    exception.add(error);
			    throw exception;
	        }
    }
}
