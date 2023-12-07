package com.mapfre.tron.api.thp.gtp.dl;

import java.math.BigDecimal;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.thp.gtp.bo.OThpGtpS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import lombok.Data;

@Data
@Repository
public class DlThpGtpDAOImpl implements IDlThpGtpDAO {

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
    public OThpGtpS get(BigDecimal cmpVal, String dcmTypVal, String usrLngVal) {

		final String query = "SELECT a.TIP_DOCUM "
			+ "FROM A1002300 a "
			+ "WHERE a.COD_CIA = ? AND  a.TIP_DOCUM = ? AND a.MCA_DOCUM_ALT= 'N'";
	
		try {
		    return jdbcTemplate.queryForObject(query,
			    new Object[]{
			    		cmpVal,
			    		dcmTypVal,
		    },
			    new OThpGtpRowMapper());
	
		}
		catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "GTP", "TIP_DOCUM", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }
}
