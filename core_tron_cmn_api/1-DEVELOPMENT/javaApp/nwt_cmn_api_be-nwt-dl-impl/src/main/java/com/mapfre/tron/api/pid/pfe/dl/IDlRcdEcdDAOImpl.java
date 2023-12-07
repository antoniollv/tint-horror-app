package com.mapfre.tron.api.pid.pfe.dl;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.rcd.ecd.bo.ORcdEcdS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.cmn.typ.dl.OCmnTypRowMapper;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class IDlRcdEcdDAOImpl implements IDlRcdEcdDAO {
    
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
    public ORcdEcdS get_eccVal(BigDecimal cmpVal, BigDecimal eccVal, String usrLngVal) {
	 
	final String query = "SELECT a.COD_ECO "
                           + "FROM g2000161 a "
                           + "WHERE a.COD_ECO = ? AND a.cod_cia = TO_NUMBER(?)";
	try {
	    return jdbcTemplate.queryForObject(query,
                   new Object[]{
                	   	eccVal,
                	        cmpVal,                	       
                                },
                   new ORcdEcdEccValRowMapper());

       }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "ECD", "ECC_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
       }
	
    }

    @Override
    public ORcdEcdS get_ecmBrwCncVal4(BigDecimal cmpVal, BigDecimal ecmBrwCncVal, BigDecimal eccVal, String usrLngVal) {
	 
	final String query = "select COD_DESGLOSE FROM G2000170 where g2000170.cod_desglose = ? "  
			   + "AND g2000170.cod_cia = TO_NUMBER(?) " 
			   + "AND g2000170.fec_validez = (SELECT MAX(b.fec_validez) FROM g2000170 b WHERE b.cod_cia = g2000170.cod_cia AND b.cod_desglose = g2000170.cod_desglose ) " 
			   + "and g2000170.cod_eco IN (?, '999') ";

	try {
	    return jdbcTemplate.queryForObject(query,
                   new Object[]{
                	   	ecmBrwCncVal,
                	        cmpVal, 
                	        eccVal,
                                },
                   new ORcdEcdEcmBrwCncVal4RowMapper());

       }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "ECD", "ECM_BRW_CNC_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
       }
	
    }
    
    @Override
    public List<OCmnTypS> get(BigDecimal cmpVal, String fldVal, String itcVal, String usrLngVal) {

	final String query = "SELECT g.COD_CAMPO, g.NUM_ORDEN, g.COD_VALOR, g.COD_IDIOMA, g.NOM_VALOR "
			+ "FROM g1010031 g "
			+ "WHERE g.cod_cia = ? AND g.cod_campo= ? AND g.cod_valor = ?";
	
	List<OCmnTypS> oCmnTypPT;
	
	oCmnTypPT = jdbcTemplate.query(query,
			new Object[]{
				cmpVal,
				fldVal,
				itcVal,
			},
		new OCmnTypRowMapper());
	
	if (oCmnTypPT.isEmpty()) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "ECD", "TYP_GRG_CMS", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
		
	return oCmnTypPT;
    }
   
}
