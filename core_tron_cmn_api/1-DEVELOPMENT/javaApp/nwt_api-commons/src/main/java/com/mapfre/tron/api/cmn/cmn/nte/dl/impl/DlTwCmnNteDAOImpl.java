package com.mapfre.tron.api.cmn.cmn.nte.dl.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.nte.bo.OCmnNteS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.cmn.nte.dl.IDlTwCmnNteDAO;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlTwCmnNteDAOImpl implements IDlTwCmnNteDAO {

	protected JdbcTemplate jdbcTemplate;

	@Autowired
	DlTrnErr lvDlTrnErr;

	@Autowired
	public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
		jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
	}
	
	// Seleccionar codigo nota versión 3
		@Override
		public OCmnNteS get_003(BigDecimal cmpVal, String nteVal, String usrLngVal) {

			final String query = "SELECT a.COD_NOTA " + "FROM A1003001 a " + "WHERE a.COD_NOTA=? "
					+ "AND a.COD_CIA = ? AND a.MCA_INH='N' AND MCA_NOTA_ENRIQUECIDA='S'";
			try {
				return jdbcTemplate.queryForObject(query, new Object[] { nteVal, cmpVal

				}, new OCmnNteRowMapper());

			} catch (EmptyResultDataAccessException e) {
				BigDecimal codError = new BigDecimal(20001);
				OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTE", "NTE_VAL", cmpVal);
				NwtException exception = new NwtException(error.getErrIdnVal());
				exception.add(error);
				throw exception;
			}
		}



	@Override
	public OCmnNteS getNoteDefinition(BigDecimal cmpVal, String nteVal, String lngVal) {

		final String sql = " SELECT  cod_cia,  cod_nota, "
				+" nom_nota, fec_edicion,  tip_nota,  tip_nivel_nota," 
				+" mca_inh,  cod_usr, fec_actu, mca_nota_enriquecida "
				+" FROM a1003001  WHERE cod_cia = ?  AND cod_nota = ? ";

		try {
			return jdbcTemplate.queryForObject(sql, new Object[] { cmpVal, nteVal }, new OCmnNteSRowMapper5());
		} catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, lngVal, "NTE", "NTE_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}

	}

	@Override
	public OCmnNteS getNoteTitles(BigDecimal cmpVal, String nteVal, String lngVal) {

		final String sql2 = " SELECT cod_cia, cod_nota, "
				+" cod_idioma,  nom_nota,  fec_actu  FROM a1003003 "
				+ "WHERE cod_cia = ?  AND cod_nota = ?  AND cod_idioma = ? ";

		try {
			return jdbcTemplate.queryForObject(sql2, new Object[] { cmpVal, nteVal, lngVal }, new OCmnNteSRowMapper2());
		} catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, lngVal, "NTE", "NTE_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}

	}

	@Override
	public List<OCmnNteS> getNotesTextDefinition(BigDecimal cmpVal, String nteVal, String lngVal) {

		final String sql3 = " SELECT  cod_cia,  cod_nota, "
				+" mca_txt_variable, num_secu, txt_nota, fec_edicion,  cod_idioma,  cod_usr, "
				+" fec_actu  FROM a1003002  WHERE cod_cia = ? "
				+" AND cod_nota = ?  AND cod_idioma = ?  AND fec_edicion = ( "
				+" SELECT MAX(fec_edicion) FROM a1003002  WHERE cod_cia = ? "
				+" AND cod_nota = ?  AND cod_idioma = ?  ) ORDER BY num_secu";

		try {
			return jdbcTemplate.query(sql3, new Object[] { cmpVal, nteVal, lngVal, cmpVal, nteVal, lngVal }, new OCmnNteSRowMapper3());
		} catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, lngVal, "NTE", "NTE_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}

	}

	@Override
	public OCmnNteS getEnrichedNotesTextDefinition(BigDecimal cmpVal, String nteVal, String lngVal) {

		final String sql4 = " SELECT  cmp_val,  nte_val, "
				+" lng_val,  vld_dat,  nte_enh_txt_val,  usr_val, "
				+" mdf_dat  FROM df_cmn_nwt_xx_nte  WHERE lng_val = ?  AND vld_dat = ( "
				+" SELECT MAX(vld_dat) FROM df_cmn_nwt_xx_nte  WHERE cmp_val = ? "
				+" AND nte_val = ?  AND lng_val = ?  ) ";

		try {
			return jdbcTemplate.queryForObject(sql4, new Object[] { lngVal, cmpVal, nteVal, lngVal }, new OCmnNteSRowMapper4());
		} catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, lngVal, "NTE", "NTE_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}

	}

}
