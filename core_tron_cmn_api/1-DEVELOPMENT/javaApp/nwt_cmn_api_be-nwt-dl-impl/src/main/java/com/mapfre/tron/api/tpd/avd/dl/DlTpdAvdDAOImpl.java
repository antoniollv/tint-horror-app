package com.mapfre.tron.api.tpd.avd.dl;

import java.math.BigDecimal;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.tpd.avd.bo.OTpdAvdS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import lombok.Data;

@Data
@Repository
public class DlTpdAvdDAOImpl implements IDlTpdAvdDAO {

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
    public OTpdAvdS get(BigDecimal cmpVal, BigDecimal thpAcvVal, String usrLngVal) {

		final String query = "SELECT a.COD_CIA, a.COD_ACT_TERCERO, a.NOM_ACT_TERCERO, a.COD_USR ,a.FEC_ACTU, a.COD_EST, a.COD_GRP_EST, a.MCA_COD_TERCERO, a.MCA_GEN_COD_TERCERO, a.MCA_PROVEEDOR "
			+ "FROM A1002200 a "
			+ "WHERE a.COD_CIA = ? AND a.COD_ACT_TERCERO = ? AND "
			+ "MCA_PROVEEDOR = 'S'";
	
		try {
		    return jdbcTemplate.queryForObject(query,
			    new Object[]{
			    		cmpVal,
			    		thpAcvVal,
		    },
			    new OTpdAvdRowMapper());
	
		}
		catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "AVD", "THP_ACV_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }

    @Override
    public OTpdAvdS get_stuGrpVal(BigDecimal cmpVal, String stuGrpVal, String usrLngVal) {

		final String query = "SELECT t.COD_CIA, t.COD_GRP_EST, t.NOM_GRP_EST "
			+ "FROM G9990002 t "
			+ "WHERE t.COD_CIA = ? AND t.COD_GRP_EST = ?";
	
		try {
		    return jdbcTemplate.queryForObject(query,
			    new Object[]{
				        cmpVal,
			    		stuGrpVal,
		    },
			    new OTpdAvdRowMapper2());
	
		}
		catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "AVD", "STU_GRP_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }
    
    
    @Override
    public OTpdAvdS get_TpdTpa(BigDecimal cmpVal, BigDecimal thpAcvVal, String usrLngVal) {

		final String query = "SELECT a.COD_CIA, a.COD_ACT_TERCERO, a.NOM_ACT_TERCERO, a.COD_USR ,a.FEC_ACTU, a.COD_EST, a.COD_GRP_EST, a.MCA_COD_TERCERO, a.MCA_GEN_COD_TERCERO, a.MCA_PROVEEDOR "
			+ "FROM A1002200 a "
			+ "WHERE a.COD_CIA = ? AND a.COD_ACT_TERCERO = ? ";
	
		try {
		    return jdbcTemplate.queryForObject(query,
			    new Object[]{
			    		cmpVal,
			    		thpAcvVal,
		    },
			    new OTpdAvdRowMapper());
	
		}
		catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "TPD", "THP_ACV_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }
}
