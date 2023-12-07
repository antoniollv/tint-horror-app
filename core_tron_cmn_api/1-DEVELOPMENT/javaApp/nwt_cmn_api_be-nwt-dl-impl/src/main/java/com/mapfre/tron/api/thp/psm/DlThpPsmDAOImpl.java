package com.mapfre.tron.api.thp.psm;

import java.math.BigDecimal;
import java.util.Date;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.thp.psm.bo.OThpPsmS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.thp.psm.dl.IDlThpPsmDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import lombok.Data;

@Data
@Repository
public class DlThpPsmDAOImpl implements IDlThpPsmDAO {

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
	public OThpPsmS get(BigDecimal cmpVal, String psmVal, String lngVal, Date vldDat, String usrLngVal) {
	    
	    final String query = "SELECT a.CMP_VAL, a.PSM_VAL, a.PSM_TYP_VAL, a.LNG_VAL, a.VLD_DAT, a.PSM_NAM, a.DSB_ROW, a.MDF_DAT, a. USR_VAL"
			+ "FROM DF_TPD_NWT_XX_SME a "
			+ "WHERE a.CMP_VAL = ? AND a.PSM_VAL = ? AND a.LNG_VAL = ? "
			+ "AND a.VLD_DAT = (SELECT MAX(b.vld_dat) FROM DF_TPD_NWT_XX_SME b WHERE b.cmp_val = a.cmp_val AND b.psm_val = a.psm_val AND b.lng_val = a.lng_val AND b.vld_dat <= ? ) "
			+ "AND a.MCA_INH = 'N'";
	    try {
		return jdbcTemplate.queryForObject(query,
				new Object[]{
					cmpVal,
					psmVal,
					lngVal,
					vldDat,
				},
				new OThpPsmRowMapper());

	    }
		catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "THP", "PSM_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
	}

}
