package com.mapfre.tron.api.thp.cmc.dl;

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
public class IDlThpCmcDAOImpl implements IDlThpCmcDAO {

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
    public OThpCmcS getCodCuaCom(BigDecimal cmpVal, BigDecimal cmcVal, String usrLngVal) {	
    	
    	final String query ="SELECT a.COD_CIA, a.COD_CUADRO_COM FROM A1001752 a WHERE a.COD_CIA = ? AND a.COD_CUADRO_COM = ?";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	cmpVal,
	                	cmcVal
	                	
	                },
	                new OThpCmcCuaComRowMapper());


        }
        catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "CMC_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    }
    
    @Override
    public OThpCmcS getCodRamo(BigDecimal cmpVal, BigDecimal lobVal, String usrLngVal) {	
    	
    	final String query ="SELECT a.COD_CIA, a.COD_RAMO "
    			+ "FROM A1001800 a "
    			+ "WHERE  a.COD_CIA = ? AND a.COD_RAMO = ?";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	cmpVal,
	                	lobVal,
	                },
	                new OThpCmcCodRamoRowMapper());


        }
        catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "LOB_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    } 
    
    @Override
    public OThpCmcS getCodMod(BigDecimal cmpVal, BigDecimal mdtVal, String usrLngVal) {	
    	
    	final String query ="SELECT a.COD_CIA, a.COD_MODALIDAD FROM G2990004 a WHERE a.COD_CIA = ? AND a.COD_MODALIDAD = ?";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	cmpVal,
	                	mdtVal,
	                },
	                new OThpCmcCodModRowMapper());


        }
        catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "MDT_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    }
    
    @Override
    public OThpCmcS getCodCob(BigDecimal cmpVal, BigDecimal lobVal, BigDecimal cvrVal,  String usrLngVal) {  	
	
       
       final String query ="SELECT a.COD_CIA, a.COD_RAMO, a.COD_MODALIDAD, a.COD_COB, a.FEC_VALIDEZ "+
               "FROM A1002150 a "+ 
               "WHERE a.COD_CIA = ? "+ 
               "AND a.COD_RAMO = ? "+ 
               "AND a.COD_COB = ? "+
               "AND a.FEC_VALIDEZ <= (SELECT MAX(b.FEC_VALIDEZ) FROM a1002150 b WHERE b.COD_CIA=a.COD_CIA AND b.COD_RAMO=a.COD_RAMO) "+
               "AND rownum=1";
    	
        try {            
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	cmpVal,
	                	lobVal,
	                	cvrVal
	                },
	                new OThpCmcCodCobRowMapper());


        }
        catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "CVR_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    }
    
    @Override
    public OThpCmcS getCodAgente(BigDecimal cmpVal, BigDecimal agnVal, String usrLngVal) {   	
    	//2 valor fijo, indica que el cod_tercero es un agente
    	final String query ="SELECT COD_TERCERO "+
                        "FROM V1001390 "+
                        "WHERE COD_CIA       = ? "+
                        "AND COD_ACT_TERCERO = 2 "+ 
                        "AND COD_TERCERO     = ?";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	cmpVal,
	                	agnVal,
	                },
	                new OThpCmcCodAgenteRowMapper());


        }
        catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "AGN_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    } 
    
    @Override
    public OThpCmcS getCodNivel1(BigDecimal cmpVal, String frsLvlVal, String usrLngVal) {
	
	final String query = "SELECT a.COD_CIA, a.COD_NIVEL1 FROM A1000700 a WHERE a.COD_CIA = ? AND a.COD_NIVEL1 = ?";
	
        try {
            
            BigDecimal lvFrsLvlVal = new BigDecimal(frsLvlVal);
             
            return jdbcTemplate.queryForObject(query,
                    new Object[]{
                	    cmpVal,
                	    lvFrsLvlVal,
                    },
                    new OThpCmcNivel1RowMapper());

 


        }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "FRS_LVL_VAL", cmpVal);
            NwtException exception = new NwtException(error.getErrIdnVal());
            exception.add(error);
            throw exception;
        }
    }  
    
    @Override
    public OThpCmcS getCodNivel2(BigDecimal cmpVal, String scnLvlVal, String usrLngVal) {
	
	final String query = "SELECT a.COD_CIA, a.COD_NIVEL2 FROM a1000701 a WHERE a.COD_CIA = ? AND a.COD_NIVEL2 = ?";
	
        try {
            
            BigDecimal lvScnLvlVal = new BigDecimal(scnLvlVal);
            
            return jdbcTemplate.queryForObject(query,
                    new Object[]{
                	    cmpVal,
                	    lvScnLvlVal,
                    },
                    new OThpCmcNivel2RowMapper());

 


        }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "SCN_LVL_VAL", cmpVal);
            NwtException exception = new NwtException(error.getErrIdnVal());
            exception.add(error);
            throw exception;
        }
    }
    
    @Override
    public OThpCmcS getCodNivel3(BigDecimal cmpVal, String thrLvlVal, String usrLngVal) {
	
	final String query = "SELECT a.COD_CIA, a.COD_NIVEL3 FROM a1000702 a WHERE a.COD_CIA = ? AND a.COD_NIVEL3 = ?";
	
        try {
            
            BigDecimal lvThrLvlVal = new BigDecimal(thrLvlVal);
            
            return jdbcTemplate.queryForObject(query,
                    new Object[]{
                	    cmpVal,
                	    lvThrLvlVal,
                    },
                    new OThpCmcNivel3RowMapper());

 

        }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "THR_LVL_VAL", cmpVal);
            NwtException exception = new NwtException(error.getErrIdnVal());
            exception.add(error);
            throw exception;
        }
    } 
    
    @Override
    public OThpCmcS getCodCanal1(BigDecimal cmpVal, String frsDstHnlVal, String usrLngVal) {
	
	final String query = "SELECT a.COD_CIA, a.COD_CANAL1 FROM A1000721 a WHERE a.COD_CIA = ? AND a.COD_CANAL1 = ?";
	
        try {
            
            return jdbcTemplate.queryForObject(query,
                    new Object[]{
                	    cmpVal,
                	    frsDstHnlVal,
                    },
                    new OThpCmcCanal1RowMapper());

 


        }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "FRS_DST_HNL_VAL", cmpVal);
            NwtException exception = new NwtException(error.getErrIdnVal());
            exception.add(error);
            throw exception;
        }
    }

    @Override
    public OThpCmcS getCodCanal2(BigDecimal cmpVal, String scnDstHnlVal, String usrLngVal) {
	
	final String query = "SELECT a.COD_CIA, a.COD_CANAL2 FROM A1000722 a WHERE a.COD_CIA = ? AND a.COD_CANAL2 = ?";
	
        try {
            
            return jdbcTemplate.queryForObject(query,
                    new Object[]{
                	    cmpVal,
                	    scnDstHnlVal,
                    },
                    new OThpCmcCanal2RowMapper());

 


        }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "SCN_DST_HNL_VAL", cmpVal);
            NwtException exception = new NwtException(error.getErrIdnVal());
            exception.add(error);
            throw exception;
        }
    }

    @Override
    public OThpCmcS getCodCanal3(BigDecimal cmpVal, String thrDstHnlVal, String usrLngVal) {
	
	final String query = "SELECT a.COD_CIA, a.COD_CANAL3 FROM A1000723 a WHERE a.COD_CIA = ? AND a.COD_CANAL3 = ?";
	
        try {
            
            return jdbcTemplate.queryForObject(query,
                    new Object[]{
                	    cmpVal,
                	    thrDstHnlVal,
                    },
                    new OThpCmcCanal3RowMapper());

 


        }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CMC", "THR_DST_HNL_VAL", cmpVal);
            NwtException exception = new NwtException(error.getErrIdnVal());
            exception.add(error);
            throw exception;
        }
    }

}
