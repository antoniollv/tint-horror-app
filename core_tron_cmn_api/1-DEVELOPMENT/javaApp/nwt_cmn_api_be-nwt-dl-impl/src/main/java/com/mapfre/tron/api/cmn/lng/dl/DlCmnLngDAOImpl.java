package com.mapfre.tron.api.cmn.lng.dl;

import java.math.BigDecimal;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import lombok.Data;

@Data
@Repository
public class DlCmnLngDAOImpl implements IDlCmnLngDAO {

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
	public OCmnLngS get(BigDecimal cmpVal, String lngVal, String usrLngVal) {

		final String query = "SELECT a.cod_idioma, a.nom_idioma,a.abr_idioma ,a.fec_actu "
				+ "FROM g1010010 a "
				+ "WHERE a.cod_idioma = ?";

		try {
		    return jdbcTemplate.queryForObject(query,
					new Object[]{
							lngVal,
					},
					new OCmnLngRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "LNG", "LNG_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}

}
