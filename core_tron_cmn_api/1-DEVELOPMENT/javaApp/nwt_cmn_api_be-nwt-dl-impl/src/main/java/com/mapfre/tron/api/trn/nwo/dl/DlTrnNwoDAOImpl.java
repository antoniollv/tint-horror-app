package com.mapfre.tron.api.trn.nwo.dl;

import java.math.BigDecimal;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.nwo.bo.OTrnNwoS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlTrnNwoDAOImpl implements IDlTrnNwoDAO {

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
    public OTrnNwoS getRamo(BigDecimal cmpVal, BigDecimal lobVal, String usrLngVal){    	
    	
    	final String query ="SELECT a.COD_RAMO "
    				+ "FROM A1001800 a "
    				+ "WHERE a.COD_CIA = ? AND a.COD_RAMO = ? AND a.MCA_INH = 'N'";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		lobVal,
	                },
	                new OTrnNwoLobRowMapper());

        }
        catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "LOB_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    }           
    
    @Override
    public OTrnNwoS getUsuario(BigDecimal cmpVal, String acsUsrVal, String usrLngVal){    	
    	
    	final String query ="SELECT a.COD_USR_CIA FROM G1002700 a where a.COD_CIA = ? "
    		+ "AND a.COD_USR_CIA= ?";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		acsUsrVal,
	                },
	                new OTrnNwoUsrRowMapper());

        }
        catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "ACS_USR_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    }   
    
    @Override
    public OTrnNwoS getInstalacion(BigDecimal cmpVal, String insVal, String usrLngVal){    	
    	
    	final String query ="SELECT a.COD_INSTALACION "
    				+ "FROM G1010000 a "
    				+ "WHERE a.COD_INSTALACION = ? ";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		insVal,
	                },
	                new OTrnNwoInsRowMapper());

        }
        catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "INS_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    }       
    
    @Override
    public OTrnNwoS getOperacion(BigDecimal cmpVal, String oprIdnVal, String usrLngVal){    	
    	
    	final String query ="SELECT a.OPR_IDN_VAL FROM DF_TRN_NWT_XX_NOD a where UPPER(a.OPR_IDN_VAL) = ? "
    		+ "AND a.LNG_VAL= ?";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		oprIdnVal,
	                		usrLngVal
	                },
	                new OTrnNwoOprRowMapper());

        }
        catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "OPR_IDN_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    }   

    
}
