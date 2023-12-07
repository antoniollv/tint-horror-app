package com.mapfre.tron.api.pod.bnn.dl;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.pod.bnn.bo.OPodBnnS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlPodBnnDAOImpl implements IDlPodBnnDAO {

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
	public List<OPodBnnS> getEntidadBancaria(BigDecimal cmpVal, String bneVal, String lngVal, String usrLngVal) {    	

		final String query ="SELECT a.COD_ENTIDAD "
				+ "FROM A5020900 a "
				+ "WHERE a.COD_ENTIDAD = ? AND a.COD_CIA = TO_NUMBER(?) AND a.MCA_INH = 'N'";
		

		List<OPodBnnS> lvOPodBnn = jdbcTemplate.query(query,
				new Object[]{
						bneVal,
						cmpVal
		},
				new OPodBnnEntidadRowMapper());

		if (lvOPodBnn.isEmpty()) {


			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "BNE_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
		return lvOPodBnn;

	}

	@Override
	public List<OPodBnnS> getMarcaPagoAutorizado(BigDecimal cmpVal, String bneVal, String lngVal, String usrLngVal) {
		
		final String query ="SELECT a.BNE_VAL, a.LNG_VAL, a.PYM_ATD "
				+ "FROM DF_POD_NWT_XX_BNN a "
				+ "WHERE a.BNE_VAL = ? AND a.CMP_VAL = TO_NUMBER(?) AND a.PYM_ATD = 'S' AND a.LNG_VAL = ?";

		List<OPodBnnS> lvOPodBnn = jdbcTemplate.query(query,
				new Object[]{
						bneVal,
						cmpVal,
						lngVal
		},
				new OPodBnnMcaRowMapper());

		if (!lvOPodBnn.isEmpty()) {


			BigDecimal codError = new BigDecimal(20002);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "PYM_ATD", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
		return lvOPodBnn;
	}       



}
