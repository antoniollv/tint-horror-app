package com.mapfre.tron.api.tpd.icf.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.tpd.icf.bo.OTpdIcfS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlTpdIcfDAOImpl implements IDlTpdIcfDAO {

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
    public OTpdIcfS get(BigDecimal cmpVal, BigDecimal lobVal, String usrLngVal){    	
    	
    	final String query ="SELECT a.COD_CIA, a.COD_RAMO "
    			+ "FROM A1001800 a "
    			+ "WHERE  a.COD_CIA = ? AND a.COD_RAMO = ?";
        try {
            return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		lobVal,
	                },
	                new OTpdIcfRowMapper());

        }
        catch (EmptyResultDataAccessException e) {
		    BigDecimal codError = new BigDecimal(20001);
		    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "ICF", "LOB_VAL", cmpVal);
		    NwtException exception = new NwtException(error.getErrIdnVal());
		    exception.add(error);
		    throw exception;
        }
    } 

    @Override
    public OTpdIcfS get_002(BigDecimal cmpVal, String clfVal, String iccTypVal, String usrLngVal) {
	
	final String query ="SELECT a.CMP_VAL, a.CLF_VAL "
			+ "FROM DF_TPD_NWT_XX_ICF_RSN_CLF a "
			+ "WHERE a.CMP_VAL = ? AND a.CLF_VAL=? AND a.ICC_TYP_VAL=? "
			+ "AND a.VLD_DAT= (SELECT MAX(b.vld_dat) FROM DF_TPD_NWT_XX_ICF_RSN_CLF b "  
			+ "WHERE b.cmp_val=a.cmp_val AND b.ICC_TYP_VAL=a.ICC_TYP_VAL " 
			+ "AND b.CLF_VAL=a.CLF_VAL "  
			+ "AND b.LNG_VAL=a.LNG_VAL "  
			+ "AND b.vld_dat <= TRUNC(SYSDATE)) "   
			+ "AND a.lng_val=?";
	try {
	    return jdbcTemplate.queryForObject(query,
		    	new Object[]{
                		cmpVal,
                		clfVal,
                		iccTypVal,
                		usrLngVal
                	},
		    	new OTpdIcfGetRowMapper());

          }
	  catch (EmptyResultDataAccessException e) {
	      		BigDecimal codError = new BigDecimal(20001);
	      		OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "ICF", "CLF_VAL", cmpVal);
	      		NwtException exception = new NwtException(error.getErrIdnVal());
	      		exception.add(error);
	      		throw exception;
	}
    } 
    
}
