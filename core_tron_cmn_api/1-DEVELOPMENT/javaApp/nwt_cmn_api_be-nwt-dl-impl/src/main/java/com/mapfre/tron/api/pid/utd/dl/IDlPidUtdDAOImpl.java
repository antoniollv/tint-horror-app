package com.mapfre.tron.api.pid.utd.dl;

import java.math.BigDecimal;
import java.util.Date;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.pid.utd.bo.OPidUtdS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import lombok.Data;

@Data
@Repository
public class IDlPidUtdDAOImpl implements IDlPidUtdDAO {
    
    protected static final String CLA_VAL = "CLA_VAL";

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
    public OPidUtdS get(BigDecimal cmpVal, String claVal, String lngVal, Date vldDat, String usrLngVal) {
	 
	final String query = "SELECT a.CLA_VAL "
                           + "FROM DF_PID_NWT_XX_UTD_LNG a "
                           + "WHERE a.CMP_VAL = TO_NUMBER(?) AND a.CLA_VAL = ? AND a.LNG_VAL = ? AND a.VLD_DAT = "
                           + "(SELECT MAX(b.VLD_DAT) FROM DF_PID_NWT_XX_UTD_LNG b WHERE b.CMP_VAL = a.CMP_VAL "
                           + "AND b.CLA_VAL = a.CLA_VAL AND b.LNG_VAL = a.LNG_VAL  AND b.VLD_DAT <= ? )";
	
	try {
	    return jdbcTemplate.queryForObject(query,
                   new Object[]{
                	        cmpVal,
                	        claVal,
                	        lngVal,
                	        vldDat,
                                },
                   new OPidUtdRowMapper());

       }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "UTD", CLA_VAL, cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
       }
	
    }

    @Override
    public OPidUtdS get_A9990010(BigDecimal cmpVal, String claVal, String vldDat, String usrLngVal) {
	 
	final String query = "SELECT a.COD_CLAUSULA AS CLA_VAL "
                           + "FROM A9990010 a "
                           + "WHERE a.COD_CLAUSULA = ? AND a.COD_CIA = TO_NUMBER(?) AND a.FEC_EDICION = TO_DATE(?,'dd/MM/yyyy') ";
	
	try {
	    return jdbcTemplate.queryForObject(query,
                   new Object[]{
                	   claVal,     
                	   cmpVal,
                	   vldDat,
                	       },
                   new OPidUtdRowMapper());

       }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "UTD", CLA_VAL, cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
        }
	
    }


    @Override
    public OPidUtdS get_A9990010_UtdLng(BigDecimal cmpVal, String claVal, String vldDat, String usrLngVal) {
	  
	final String query = "SELECT b.CLA_VAL "
                	   + "FROM DF_PID_NWT_XX_UTD_LNG b INNER JOIN A9990010 a "
                	   + "ON (b.CMP_VAL = a.COD_CIA AND b.CLA_VAL = a.COD_CLAUSULA AND b.VLD_DAT = a.FEC_EDICION) "
                	   + "WHERE b.CMP_VAL = TO_NUMBER(?) AND b.CLA_VAL = ?  AND b.VLD_DAT = TO_DATE(?,'dd/MM/yyyy') ";

	try {
	    return jdbcTemplate.queryForObject(query,
		    new Object[]{
			    cmpVal,
			    claVal,	    
			    vldDat,
                     		},
		    new OPidUtdRowMapper());


        }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "UTD", CLA_VAL, cmpVal);
            NwtException exception = new NwtException(error.getErrIdnVal());
            exception.add(error);
            throw exception;
        }
	
    }
    
}
