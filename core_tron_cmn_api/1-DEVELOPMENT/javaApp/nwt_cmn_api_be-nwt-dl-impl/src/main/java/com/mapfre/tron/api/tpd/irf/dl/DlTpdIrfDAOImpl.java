package com.mapfre.tron.api.tpd.irf.dl;

import java.math.BigDecimal;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.tpd.irf.bo.OTpdIrfS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlTpdIrfDAOImpl implements IDlTpdIrfDAO {

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
    public OTpdIrfS getTipoGestion(BigDecimal cmpVal, String mngTypVal, String usrLngVal) {	

	final String query ="SELECT a.COD_VALOR "
		+ "FROM G1010031 a "
		+ "WHERE a.COD_CIA = ? "
		+ "AND a.COD_VALOR = ? "
		+ "AND a.COD_IDIOMA = ? "
		+ "AND a.COD_CAMPO = 'ICC_TYP_VAL' ";

	try {
	    return jdbcTemplate.queryForObject(query,
		    new Object[]{
			    cmpVal,
			    mngTypVal,
			    usrLngVal
	    },
		    new OTpdIrfTipoGestionRowMapper());

	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "MNG_TYP_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }           

    @Override
    public OTpdIrfS getTipoMovimiento(BigDecimal cmpVal, String iccMvmTypVal, String usrLngVal) {   	

	final String query ="SELECT a.COD_VALOR "
		+ "FROM G1010031 a "
		+ "WHERE a.COD_CIA = ? "
		+ "AND a.COD_VALOR = ? "
		+ "AND a.COD_IDIOMA = ? "
		+ "AND a.COD_CAMPO = 'ICC_MVM_TYP_VAL' ";
	try {
	    return jdbcTemplate.queryForObject(query,
		    new Object[]{			    
			    cmpVal,
			    iccMvmTypVal,
			    usrLngVal
	    },
		    new OTpdIrfTipoMovimientoRowMapper());

	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "ICC_MVM_TYP_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

    @Override
    public OTpdIrfS getMotivo(BigDecimal cmpVal, String iccRsnVal, String usrLngVal) {   	

	final String query ="SELECT a.ICC_RSN_VAL "
		+ "FROM DF_TPD_NWT_XX_IRF a "
		+ "WHERE a.ICC_RSN_VAL = ?";
	try {
	    return jdbcTemplate.queryForObject(query,
		    new Object[]{
			    iccRsnVal
	    },
		    new OTpdIrfMotivoRowMapper());

	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "ICC_RSN_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }


}
