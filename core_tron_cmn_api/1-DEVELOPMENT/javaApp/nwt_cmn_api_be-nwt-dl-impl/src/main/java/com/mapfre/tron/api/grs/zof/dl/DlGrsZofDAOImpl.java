package com.mapfre.tron.api.grs.zof.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.grs.zof.bo.OGrsZofS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlGrsZofDAOImpl implements IDlGrsZofDAO {

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
	public OGrsZofS get(BigDecimal cmpVal, String cnyVal, BigDecimal sttVal, BigDecimal pvcVal, BigDecimal twnVal, String pslCodVal, String usrLngVal) {

		final String query = "SELECT a.COD_PAIS, a.COD_POSTAL, a.COD_ESTADO, a.COD_PROV, a.COD_LOCALIDAD, a.MCA_INH, a.MCA_CODPOSTAL_REAL  "
				+ "FROM A1000103 a "
				+ "WHERE a.COD_PAIS = ? AND a.COD_ESTADO = ? AND a.COD_PROV = ? AND a.COD_LOCALIDAD = ? AND a.COD_POSTAL = ? AND a.MCA_INH = 'N' ";

		try {
		    return jdbcTemplate.queryForObject(query,
					new Object[]{
							cnyVal, 
							sttVal,
							pvcVal,
							twnVal,
							pslCodVal,
					},
					new OGrsZofRowMapper());


		}catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "ZOF", "PSL_COD_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}
}
