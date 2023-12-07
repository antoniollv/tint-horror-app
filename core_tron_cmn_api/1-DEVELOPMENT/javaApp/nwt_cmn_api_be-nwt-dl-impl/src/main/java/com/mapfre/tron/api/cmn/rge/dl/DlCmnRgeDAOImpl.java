package com.mapfre.tron.api.cmn.rge.dl;

import java.math.BigDecimal;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.rge.bo.OCmnRgeS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import lombok.Data;

@Data
@Repository
public class DlCmnRgeDAOImpl implements IDlCmnRgeDAO {

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
	public OCmnRgeS getRgeVldVal(BigDecimal cmpVal, String rgeVldVal, String usrLngVal) {
		final String query = new StringBuilder()
	            .append("SELECT a.RGE_VLD_VAL 			")
	            .append("	FROM DF_CMN_NWT_XX_TLG_RGE a")
	            .append("		WHERE a.RGE_VLD_VAL = ?	")
	            .append("		AND   a.CMP_VAL = ?		")
	            .toString();		
		   
		try {
			return jdbcTemplate.queryForObject(query,
					new Object[]{
							rgeVldVal,
							cmpVal
			},
					new OCmnRgeVldValNamRowMapper());

		}
		catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
			OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, null, "RGE_VLD_VAL", cmpVal);
			NwtException exception = new NwtException(error.getErrIdnVal());
			exception.add(error);
			throw exception;
		}
	}
}
