package com.mapfre.tron.api.cmn.opm.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.opm.bo.OCmnOpmS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class IDlCmnOpmDAOImpl implements IDlCmnOpmDAO {
    
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
    public OCmnOpmS get_insVal(BigDecimal cmpVal, String insVal, String usrLngVal) {
	// TODO Auto-generated method stub
	final String query = "SELECT a.VAL_PARAM "
                           + "FROM g0000000 a "
                           + "WHERE a.VAL_PARAM = ? AND a.cod_cia = TO_NUMBER(?) AND a.COD_PARAM IN ('COD_INSTALACION')";
	try {
	    OCmnOpmS lvOCmnOpmS = jdbcTemplate.queryForObject(query,
                   new Object[]{
                	   	insVal,
                	        cmpVal,
                                },
                   new OCmnOpmInsValRowMapper());

            return lvOCmnOpmS;
       }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "OPM", "INS_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
       }
	
    }
   
}
