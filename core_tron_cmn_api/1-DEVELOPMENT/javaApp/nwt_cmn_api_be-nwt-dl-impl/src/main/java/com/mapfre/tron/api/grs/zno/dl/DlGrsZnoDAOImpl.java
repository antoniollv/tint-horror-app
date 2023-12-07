package com.mapfre.tron.api.grs.zno.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.grs.zno.bo.OGrsZnoS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlGrsZnoDAOImpl implements IDlGrsZnoDAO {

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
	public OGrsZnoS get(BigDecimal cmpVal, String cnyVal, String usrLngVal) {

		final String query = "SELECT a.COD_PAIS, a.NOM_PAIS, a.NOM_COR_PAIS, a.NOM_VERNACULO, a.COD_IDIOMA "
				+ "FROM A1000101 a "
				+ "WHERE a.COD_PAIS = ? AND a.MCA_INH = 'N' ";

		try {
		    return jdbcTemplate.queryForObject(query,
					new Object[]{
							cnyVal,
					},
					new OGrsZnoRowMapper());


		}catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "ZNO", "CNY_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}

}
