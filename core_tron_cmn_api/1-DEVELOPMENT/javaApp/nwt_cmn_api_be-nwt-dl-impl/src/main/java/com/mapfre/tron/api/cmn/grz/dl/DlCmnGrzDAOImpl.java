package com.mapfre.tron.api.cmn.grz.dl;

import java.math.BigDecimal;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.grz.bo.OCmnGrzS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import lombok.Data;


@Data
@Repository
public class DlCmnGrzDAOImpl implements IDlCmnGrzDAO {

	protected static final String GRZ_VAL = "GRZ_VAL";

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
	public List<OCmnGrzS> get_grzPT(BigDecimal cmpVal, String grzVal, String usrLngVal){

		final String query = "SELECT a.CMP_VAL, a.GRZ_VAL, a.LNG_VAL ,a.GRZ_NAM, a.GRZ_TYP_VAL, a.DSB_ROW, a.USR_VAL, a.MDF_DAT  "
				+ "FROM DF_CMN_NWT_XX_GRZ a  "
				+ "WHERE a.CMP_VAL = ? AND a.GRZ_VAL = ? ";
		try {
		    return jdbcTemplate.query(query,
					new Object[]{
							cmpVal,
							grzVal,
			},
					new OCmnGrzRowMapper());


		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "GRZ", GRZ_VAL, cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}


	@Override
	public OCmnGrzS get(BigDecimal cmpVal, String grzVal, String grzTypVal, String usrLngVal){

		final String query = "SELECT a.CMP_VAL, a.GRZ_VAL, a.LNG_VAL ,a.GRZ_NAM, a.GRZ_TYP_VAL, a.DSB_ROW, a.USR_VAL, a.MDF_DAT "
				+ "FROM DF_CMN_NWT_XX_GRZ a "
				+ "WHERE a.CMP_VAL = ? AND a.GRZ_VAL = ? AND a.GRZ_TYP_VAL IN (?,'3') AND a.LNG_VAL = ?";
		try {
		    return jdbcTemplate.queryForObject(query,
					new Object[]{
							cmpVal,
							grzVal,
							grzTypVal,
							usrLngVal,
					},
					new OCmnGrzRowMapper());


		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "GRZ", GRZ_VAL, cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}


	@Override
	public OCmnGrzS get_grzVal(BigDecimal cmpVal, String grzVal, String usrLngVal){

		final String query = "SELECT a.CMP_VAL, a.GRZ_VAL, a.LNG_VAL ,a.GRZ_NAM, a.GRZ_TYP_VAL, a.DSB_ROW, a.USR_VAL, a.MDF_DAT "
				+ "FROM DF_CMN_NWT_XX_GRZ a "
				+ "WHERE a.CMP_VAL = ? AND a.GRZ_VAL = ? AND a.LNG_VAL = ? ";
		try {
		    return jdbcTemplate.queryForObject(query,
					new Object[]{
							cmpVal,
							grzVal,
							usrLngVal,
			},
					new OCmnGrzRowMapper());


		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "GRZ", GRZ_VAL, cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}

}
