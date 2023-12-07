package com.mapfre.tron.api.thp.acv.dl;

import java.math.BigDecimal;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmu.thl.bo.OCmuThlS;
import com.mapfre.nwt.trn.thp.acv.bo.OThpAcvS;
import com.mapfre.nwt.trn.thp.bno.bo.OThpBnoS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import lombok.Data;

@Data
@Repository
public class DlThpAcvDAO2Impl implements IDlThpAcvDAO2 {

    protected static final String MNR_VAL = "MNR_VAL";

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
    public OThpAcvS get_AG(BigDecimal cmpVal, String mnrVal, String usrLngVal) {

		final String query = "SELECT a.COD_CIA, a.TIP_DOCUM, a.COD_DOCUM, a.COD_ACT_TERCERO  "
			+ "FROM A1001390 a  "
			+ "WHERE a.COD_CIA = ? AND a.COD_TERCERO = ? AND a.COD_ACT_TERCERO = '2'";
	
		try {
		    return jdbcTemplate.queryForObject(query,
			    new Object[]{
				        cmpVal,
			    		mnrVal,
		    },
			    new OThpAcvRowMapper());
	

		}
		catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MNR", MNR_VAL, cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }
    
    @Override
    public OThpAcvS get_CICOCP(BigDecimal cmpVal, String mnrVal, String usrLngVal) {

		final String query = "SELECT a.COD_CIA, a.TIP_DOCUM, a.COD_DOCUM, a.COD_ACT_TERCERO "
			+ "FROM A1001390 a "
			+ "WHERE a.COD_CIA = ? AND a.COD_TERCERO = ? AND  a.COD_ACT_TERCERO = '12'";
	
		try {
		    return jdbcTemplate.queryForObject(query,
			    new Object[]{
				    	cmpVal,
				    	mnrVal,
		    },
			    new OThpAcvRowMapper());
	

		}
		catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MNR", MNR_VAL, cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }
    
    @Override
    public OThpAcvS get_GP(BigDecimal cmpVal, String mnrVal, String usrLngVal) {

		final String query = "SELECT a.COD_CIA, a.TIP_DOCUM, a.COD_DOCUM, a.COD_ACT_TERCERO "
			+ "FROM A1001390 a "
			+ "WHERE a.COD_CIA = ? AND a.COD_TERCERO = ? AND  a.COD_ACT_TERCERO = '13'";
	
		try {
		    return jdbcTemplate.queryForObject(query,
			    new Object[]{
				    	cmpVal,
			    		mnrVal,
		    },
			    new OThpAcvRowMapper());
	

		}
		catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MNR", MNR_VAL, cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }
    
    @Override
    public OThpBnoS get_BADBTA(BigDecimal cmpVal, String mnrVal, String usrLngVal) {

		final String query = "SELECT A5020910.COD_ENTIDAD, A5020910.COD_OFICINA "
			+ "FROM A5020910,  A5020900 "
			+ "WHERE A5020900.cod_entidad = A5020910.cod_entidad "
			+ "AND A5020910.COD_CIA = ?   "
			+ "AND A5020910.COD_CIA = A5020900.COD_CIA "
			+ "AND A5020910.COD_ENTIDAD||A5020910.COD_OFICINA = ? ";
	
		try {
		    return jdbcTemplate.queryForObject(query,
			    new Object[]{
			    		cmpVal,
			    		mnrVal,
		    },
			    new OThpBnoRowMapper());
	
		}
		catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MNR", MNR_VAL, cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }
    
    @Override
    public OCmuThlS get_GDOF(BigDecimal cmpVal, String mnrVal, String usrLngVal) {

		final String query = "SELECT a.COD_CIA, a.COD_NIVEL3 "
			+ "FROM A1000702 a"
			+ "WHERE a.COD_CIA = ? AND a.COD_NIVEL3 = ? ";
	
		try {
		    return jdbcTemplate.queryForObject(query,
			    new Object[]{
			    		cmpVal,
			    		mnrVal,
		    },
			    new OCmuThlRowMapper());
	

		}
		catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MNR", MNR_VAL, cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }

}
