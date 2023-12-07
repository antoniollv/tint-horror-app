package com.mapfre.tron.api.tpd.iif.dl;

import java.math.BigDecimal;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.tpd.iif.bo.OTpdIifS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlTpdIifDAOImpl implements IDlTpdIifDAO {

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
	public OTpdIifS getTipoGestion(BigDecimal cmpVal, String mngTypVal, String usrLngVal) {	

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
					new OTpdIifTipoGestionRowMapper());

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
	public OTpdIifS getCodigoImpacto(BigDecimal cmpVal, String imcCodTypVal, String usrLngVal) {   	

		final String query ="SELECT a.IMC_COD_TYP_VAL "
				+ "FROM DF_TPD_NWT_XX_IIF a "
				+ "WHERE a.IMC_COD_TYP_VAL = ?";
		try {
			return jdbcTemplate.queryForObject(query,
					new Object[]{
							imcCodTypVal
			},
					new OTpdIifCodigoImpactoRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "IMC_COD_TYP_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}


}
