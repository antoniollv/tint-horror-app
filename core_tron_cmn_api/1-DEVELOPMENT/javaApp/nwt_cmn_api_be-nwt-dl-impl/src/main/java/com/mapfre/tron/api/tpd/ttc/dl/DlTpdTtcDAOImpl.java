package com.mapfre.tron.api.tpd.ttc.dl;

import java.math.BigDecimal;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.tpd.ttc.bo.OTpdTtcS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlTpdTtcDAOImpl implements IDlTpdTtcDAO {

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
	public OTpdTtcS getActividad(BigDecimal cmpVal, BigDecimal thpAcvVal, String usrLngVal) {
		final String query ="SELECT a.COD_ACT_TERCERO "
				+ "FROM A1002200 a "
				+ "WHERE a.COD_ACT_TERCERO = ? "
				+ "AND a.COD_CIA= ?";
		try {
			return jdbcTemplate.queryForObject(query,
					new Object[]{
							thpAcvVal,
							cmpVal
			},
					new OTpdTtcActRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "THP_ACV_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}

	@Override
	public OTpdTtcS getRol(BigDecimal cmpVal, String rolVal, String usrLngVal) {
		final String query ="SELECT a.ROL_VAL "
				+ "FROM DF_CMN_NWT_XX_UAR a "
				+ "WHERE a.ROL_VAL = ? "
				+ "AND a.DSB_ROW = 'N' "
				+ "GROUP BY a.ROL_VAL";
		try {
			return jdbcTemplate.queryForObject(query,
					new Object[]{
							rolVal
			},
					new OTpdTtcRolRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "ROL_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}
	
	@Override
	public OTpdTtcS getConceptoLogico(BigDecimal cmpVal, String lgpVal, String usrLngVal) {   	

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
					new OTpdTtcConceptoRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "LGP_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}
	
	@Override
	public OTpdTtcS getPropiedad(BigDecimal cmpVal, String prpVal, String usrLngVal) {
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
					new OTpdTtcPropiedadRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "PRP_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}


}
