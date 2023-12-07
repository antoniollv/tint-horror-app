package com.mapfre.tron.api.tpd.ssd.dl;

import java.math.BigDecimal;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.tpd.ssd.bo.OTpdSsdS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlTpdSsdDAOImpl implements IDlTpdSsdDAO {

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
    public OTpdSsdS get(BigDecimal cmpVal, String ssvVal, String lngVal, String usrLngVal) {
    	
    	final String query = "SELECT t.CMP_VAL, t.SSV_VAL, t.LNG_VAL, t.DSB_ROW, t.SSV_NAM, t.SSV_TYP_VAL, t.USR_VAL, t.MDF_DAT  from df_tpd_nwt_xx_ssd t where t.cmp_val = ? and t.ssv_val = ? and t.lng_val = ?";
        
    	try {
    	return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	cmpVal, 
	                	ssvVal,
	                	lngVal,
	                },
	                new OTpdSsdRowMapper());

	
		}catch (EmptyResultDataAccessException e) {
			BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SSD", "SSV_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
		}
    }
    
    @Override
    public List<OTpdSsdS> get_ssdPT(BigDecimal cmpVal, String ssvVal, String usrLngVal) {
    	
    	final String query = "SELECT t.CMP_VAL, t.SSV_VAL, t.LNG_VAL, t.DSB_ROW, t.SSV_NAM, t.SSV_TYP_VAL, t.USR_VAL, t.MDF_DAT  "
    			+ "FROM df_tpd_nwt_xx_ssd t "
    			+ "WHERE t.CMP_VAL = ? and t.SSV_VAL = ?";
    	
        List<OTpdSsdS> lvOTpdSsdPT = jdbcTemplate.query(query,
                new Object[]{
                	cmpVal, 
                	ssvVal,
                },
                new OTpdSsdRowMapper());
        
        if (lvOTpdSsdPT.isEmpty()) {
			BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SSD", "SSV_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
        
        return lvOTpdSsdPT;
    }
}
