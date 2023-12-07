package com.mapfre.tron.api.ply.sdt.dl;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.ply.sdt.bo.OPlySdtS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlPlySdtDAOImpl implements IDlPlySdtDAO {

	protected JdbcTemplate jdbcTemplate;
	static final String FROM = "FROM A2000030 a ";	
	static final String SUBQUERY1= "AND a.NUM_SPTO = (SELECT MAX(b.NUM_SPTO) FROM A2000030 b WHERE b.NUM_POLIZA = ?) ";
	static final String SUBQUERY2= "AND a.NUM_SPTO = (SELECT MAX(b.NUM_SPTO) FROM A2000030 b WHERE b.NUM_POLIZA = ? AND b.NUM_APLI = ?)";

	@Autowired
	DlTrnErr lvDlTrnErr;

	@Autowired
	public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
		jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
	}

	@Override
	public List<OPlySdtS> getPoliza(BigDecimal cmpVal, String plyVal, String usrLngVal){    	

		final String query ="SELECT a.NUM_POLIZA "
				+ FROM
				+ "WHERE a.NUM_POLIZA = ? AND a.cod_cia = TO_NUMBER(?) "
				+ SUBQUERY1;

		List<OPlySdtS> lvOPlySdtPT = jdbcTemplate.query(query,
				new Object[]{
						plyVal,
						cmpVal,
						plyVal
				},
				new OPlySdtRowMapper());
		if (lvOPlySdtPT.isEmpty()) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SDT", "PLY_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;

		}else {
			final String query2 ="SELECT a.NUM_POLIZA "
					+ FROM
					+ "WHERE a.NUM_POLIZA = ? AND a.cod_cia = TO_NUMBER(?) AND a.MCA_PROVISIONAL = 'N' "
					+ SUBQUERY1;
			
			lvOPlySdtPT = jdbcTemplate.query(query2,
					new Object[]{
							plyVal,
							cmpVal,
							plyVal
					},
					new OPlySdtRowMapper());
			if (lvOPlySdtPT.isEmpty()) {
				BigDecimal codError = new BigDecimal(20032);
				OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SDT", "PLY_VAL", cmpVal);
				NwtException exception = new NwtException(error.getErrIdnVal());
				exception.add(error);
				throw exception;
			}


		}
		return lvOPlySdtPT;
	}       

	public List<OPlySdtS> getAplicacion(BigDecimal cmpVal, String plyVal, BigDecimal aplVal, String usrLngVal){    	

		final String query ="SELECT a.NUM_POLIZA, a.NUM_APLI "
				+ FROM
				+ "WHERE a.NUM_POLIZA = ? AND a.cod_cia = TO_NUMBER(?) AND a.NUM_APLI = ? "
				+ SUBQUERY2;

		List<OPlySdtS> lvOPlySdtPT = jdbcTemplate.query(query,

				new Object[]{
						plyVal,
						cmpVal,
						aplVal,
						plyVal,
						aplVal
		},
				new OPlySdtAplRowMapper());

		if (lvOPlySdtPT.isEmpty()) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SDT", "APL_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;

		}else{
			final String query2 ="SELECT a.NUM_POLIZA, a.NUM_APLI "
					+ FROM
					+ "WHERE a.NUM_POLIZA = ? AND a.cod_cia = TO_NUMBER(?) AND a.NUM_APLI = ? AND a.MCA_PROVISIONAL = 'N' "
					+ SUBQUERY2;

			lvOPlySdtPT = jdbcTemplate.query(query2,

					new Object[]{
							plyVal,
							cmpVal,
							aplVal,
							plyVal,
							aplVal
			},
					new OPlySdtAplRowMapper());

			if (lvOPlySdtPT.isEmpty()) {
				BigDecimal codError = new BigDecimal(20032);
				OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SDT", "APL_VAL", cmpVal);
				NwtException exception = new NwtException(error.getErrIdnVal());
				exception.add(error);
				throw exception;

			}
		}
		return lvOPlySdtPT;
	}  

}
