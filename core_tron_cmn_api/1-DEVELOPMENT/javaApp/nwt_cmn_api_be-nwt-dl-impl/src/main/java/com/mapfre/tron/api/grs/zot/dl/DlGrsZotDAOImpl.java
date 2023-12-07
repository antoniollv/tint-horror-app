package com.mapfre.tron.api.grs.zot.dl;

import java.math.BigDecimal;
import java.util.Date;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.grs.zot.bo.OGrsZotS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import lombok.Data;

@Data
@Repository
public class DlGrsZotDAOImpl implements IDlGrsZotDAO {

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
	public OGrsZotS get(BigDecimal cmpVal, String cnyVal, BigDecimal sttVal, BigDecimal pvcVal, String usrLngVal){

		final String query = "SELECT a.COD_PAIS, a.COD_ESTADO, a.COD_PROV ,a.NOM_PROV, a.ABR_PROV, a.NOM_VERNACULO, a.MCA_INH, a.MCA_PROV_REAL "
				+ "FROM A1000100 a "
				+ "WHERE a.COD_PAIS = ? AND a.COD_ESTADO = ? AND a.COD_PROV = ? AND a.MCA_INH = 'N'";
		try {
			OGrsZotS lvOGrsZotS = jdbcTemplate.queryForObject(query,
					new Object[]{
							cnyVal,
							sttVal,
							pvcVal,
					},
					new OGrsZotRowMapper());

			return lvOGrsZotS;
		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "ZOT", "PVC_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}

}
