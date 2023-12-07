package com.mapfre.tron.api.grs.znt.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.grs.znt.bo.OGrsZntS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlGrsZntDAOImpl implements IDlGrsZntDAO {

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
	public OGrsZntS get(BigDecimal cmpVal, String cnyVal, BigDecimal sttVal, String usrLngVal){

		final String query = "SELECT a.COD_PAIS, a.COD_ESTADO, a.NOM_ESTADO ,a.ABR_ESTADO, a.NOM_VERNACULO, a.MCA_INH, a.MCA_ESTADO_REAL  "
				+ "FROM A1000104 a "
				+ "WHERE a.COD_PAIS = ? AND a.COD_ESTADO = ? AND a.MCA_INH = 'N' ";
		try {
		    return jdbcTemplate.queryForObject(query,
					new Object[]{
							cnyVal,
							sttVal,
					},
					new OGrsZntRowMapper());


		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "ZNT", "STT_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}
}
