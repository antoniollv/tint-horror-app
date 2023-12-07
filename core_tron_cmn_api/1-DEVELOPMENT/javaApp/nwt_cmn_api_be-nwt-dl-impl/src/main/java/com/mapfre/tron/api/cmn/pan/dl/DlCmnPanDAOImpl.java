package com.mapfre.tron.api.cmn.pan.dl;

import java.math.BigDecimal;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.pan.bo.OCmnPanS;
import com.mapfre.nwt.trn.cmn.uar.bo.OCmnUarS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlCmnPanDAOImpl implements IDlCmnPanDAO {

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
	public OCmnPanS getRol(BigDecimal cmpVal, String pirVal, Date vldDat, String usrLngVal) {   	

		final String query ="SELECT a.PIR_VAL "
				+ "FROM DF_CMN_NWT_XX_PIR a "
				+ "WHERE a.PIR_VAL = ? "
				+ "AND a.VLD_DAT = (SELECT MAX(b.VLD_DAT) FROM DF_CMN_NWT_XX_PIR b WHERE "
				+ "b.PIR_VAL = a.PIR_VAL "
				+ "AND a.DSB_ROW = 'N' "
				+ "AND b.VLD_DAT <= ?)";		
		try {
			return jdbcTemplate.queryForObject(query,
					new Object[]{
							pirVal,
							vldDat,
			},
					new OCmnPanRolRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "PAN", "PIR_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}           

	@Override
	public OCmnPanS getConceptoLogico(BigDecimal cmpVal, String lgpVal, String usrLngVal) {   	

		final String query ="SELECT a.TEM_VAL "
				+ "FROM DF_TRN_NWT_XX_GLS a "
				+ "WHERE a.TEM_VAL = ? "
				+ "AND a.LNG_VAL = ? "
				+ "GROUP BY a.TEM_VAL";
		try {
			return jdbcTemplate.queryForObject(query,
					new Object[]{
							lgpVal,
							usrLngVal,
			},
					new OCmnPanConceptoLogicoRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "PAN", "LGP_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}

	@Override
	public OCmnPanS getPropiedad(BigDecimal cmpVal, String prpVal, String usrLngVal) {
		final String query = "SELECT a.PRP_IDN "
				+ "FROM DF_TRN_NWT_XX_PRP a "
				+ "WHERE a.PRP_IDN = ? "
				+ "AND a.LNG_VAL = ? "
				+ "GROUP BY a.PRP_IDN";

		try {
			return jdbcTemplate.queryForObject(query,
					new Object[]{
							prpVal,
							usrLngVal,
			},
					new OCmnPanPropiedadRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "PAN", "PRP_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}


	@Override
	public OCmnUarS getAplicacion(BigDecimal cmpVal, String appVal, String usrLngVal) {
		final String query ="SELECT a.APP_VAL "
				+ "FROM DF_TRN_NWT_XX_PGM_APP a "
				+ "WHERE a.APP_VAL = ? "
				+ "AND a.VLD_DAT = (SELECT MAX(b.VLD_DAT) FROM DF_TRN_NWT_XX_PGM_APP b "
				+ "WHERE b.APP_VAL = a.APP_VAL "
				+ "AND (a.DSB_APP = 'N' OR a.DSB_APP IS NULL) "
				+ "AND b.VLD_DAT <= SYSDATE)";

		try {
			return jdbcTemplate.queryForObject(query,
					new Object[]{	                		
							appVal,
			},
					new OCmnPanAppRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "PAN", "APP_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}

	@Override
	public OCmnUarS getUsuarioAplicacion(BigDecimal cmpVal, String appUsrVal, String usrLngVal) {
		final String query ="SELECT a.COD_USR_CIA "
				+ "FROM G1002700 a "
				+ "WHERE a.COD_CIA = ? "
				+ "AND a.COD_USR_CIA = ?";
		try {
			return jdbcTemplate.queryForObject(query,
					new Object[]{
							cmpVal,
							appUsrVal,
			},
					new OCmnPanUsuarioAppRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "PAN", "APP_USR_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}

	@Override
	public OCmnUarS getCodigoRol(BigDecimal cmpVal, String rolVal, Date vldDat, String usrLngVal) {
		final String query ="SELECT a.PIR_VAL "
				+ "FROM DF_CMN_NWT_XX_PIR a "
				+ "WHERE a.PIR_VAL = ? "
				+ "AND a.LNG_VAL = ? "
				+ "AND a.CMP_VAL = ? "
				+ "AND a.VLD_DAT = (SELECT MAX(b.VLD_DAT) FROM DF_CMN_NWT_XX_PIR b "
				+ "WHERE b.PIR_VAL = a.PIR_VAL "
				+ "AND a.DSB_ROW = 'N' "
				+ "AND b.VLD_DAT <= ?)";
		try {
			return jdbcTemplate.queryForObject(query,
					new Object[]{	                		
							rolVal,
							usrLngVal,
							cmpVal,
							vldDat,
			},
					new OCmnPanCodigoRolRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "PAN", "ROL_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}

	@Override
	public OCmnUarS getTipoRol(BigDecimal cmpVal, String rolTypVal, String usrLngVal) {
		final String query ="SELECT a.COD_VALOR "
				+ "FROM G1010031 a "
				+ "WHERE a.COD_CIA = ? "
				+ "AND a.COD_VALOR = ? "
				+ "AND a.COD_IDIOMA = ? "
				+ "AND a.COD_CAMPO = 'ROL_TYP_VAL' "
				+ "GROUP BY a.COD_VALOR";
		try {
			return jdbcTemplate.queryForObject(query,
					new Object[]{	
							cmpVal,
							rolTypVal,
							usrLngVal,
			},
					new OCmnPanTipoRolRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "PAN", "ROL_TYP_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}
}
