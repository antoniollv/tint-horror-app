package com.mapfre.tron.api.acd.acc.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.acd.acc.bo.OAcdAccS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;


@Data
@Repository
public class DlAcdAccDAOImpl implements IDlAcdAccDAO {

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
	public OAcdAccS get(BigDecimal cmpVal, String acgGrgVal, String cloPymCncVal, String usrLngVal){

		final String query = "SELECT a.COD_CIA, a.COD_AGRUP_CTABLE, a.COD_CTO_CTABLE, a.NOM_CTO_CTABLE ,a.NOM_COR_CTO, a.MCA_INH, a.FEC_INH, a.COD_USR, a.FEC_ACTU "
				+ "FROM A5100121 a "
				+ "WHERE a.COD_CIA = ? AND a.COD_AGRUP_CTABLE = ? AND a.COD_CTO_CTABLE = ?";
		try {
		    return jdbcTemplate.queryForObject(query,
					new Object[]{
							cmpVal,
							acgGrgVal,
							cloPymCncVal,
					},
					new OAcdAccRowMapper());


		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "ACC", "CLO_PYM_CNC_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}
	
	
	@Override
	public OAcdAccS get_TpdTap(BigDecimal cmpVal, String cloPymCncVal, String acgGrgVal, String usrLngVal){

		final String query = "SELECT a.COD_CIA, a.COD_AGRUP_CTABLE, a.COD_CTO_CTABLE, a.NOM_CTO_CTABLE ,a.NOM_COR_CTO, a.MCA_INH, a.FEC_INH, a.COD_USR, a.FEC_ACTU "
				+ "FROM A5100121 a "
				+ "WHERE a.COD_AGRUP_CTABLE = ? AND a.COD_CTO_CTABLE = ?";
		try {
		    return jdbcTemplate.queryForObject(query,
					new Object[]{
							acgGrgVal,
							cloPymCncVal,
					},
					new OAcdAccRowMapper());


		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "ACC", "CLO_PYM_CNC_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}

}
