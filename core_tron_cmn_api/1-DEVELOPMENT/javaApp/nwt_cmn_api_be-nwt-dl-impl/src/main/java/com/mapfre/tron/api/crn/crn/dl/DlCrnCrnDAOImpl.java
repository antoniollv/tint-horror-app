package com.mapfre.tron.api.crn.crn.dl;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.crn.crn.bo.OCrnCrnS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlCrnCrnDAOImpl implements IDlCrnCrnDAO {

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
    public List<OCrnCrnS> get(BigDecimal cmpVal, String crnVal, String usrLngVal) {

		final String query = "SELECT a.COD_MON, a.NOM_MON, a.COD_MON_ISO, a.MCA_MON_REAL, a.NUM_DECIMALES "
			+ "FROM a1000400 a " 
		    + "WHERE a.cod_cia = ? AND a.cod_mon = ? AND a.mca_mon_real = 'S'";
	
		List<OCrnCrnS> oCrnCrnPT;
	
		oCrnCrnPT = jdbcTemplate.query(query,
			new Object[]{
				cmpVal,
				crnVal,
			},
			new OCrnCrnRowMapper());
	
		if (oCrnCrnPT.isEmpty()) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "CRN", "CRN_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
		
		return oCrnCrnPT;
    }
}
