package com.mapfre.tron.api.fdf.ccl.dl;

import java.math.BigDecimal;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.fdf.fdf.bo.OFdfFdfS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;


@Data
@Repository
public class DlFdfCclDAOImpl implements IDlFdfCclDAO {

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
    public OFdfFdfS get(BigDecimal cmpVal, String frdCclTypVal,  String usrLngVal, Date vldDat) {
	 
	final String query = "SELECT a.CMP_VAL, a.FRD_CCL_TYP_VAL, a.LNG_VAL, a.VLD_DAT, a.FRD_CCL_TYP_NAM, a.FRD_CHC, a.DSB_ROW "
	                   + "FROM DF_FDF_NWT_XX_FDF_CCL_TYP a "
	                   + "WHERE a.CMP_VAL = ? AND a.FRD_CCL_TYP_VAL = ? AND a.LNG_VAL = ? AND a.VLD_DAT = "
	                   + "(SELECT MAX(b.VLD_DAT) FROM DF_FDF_NWT_XX_FDF_CCL_TYP b WHERE b.CMP_VAL = a.CMP_VAL AND b.FRD_CCL_TYP_VAL = a.FRD_CCL_TYP_VAL AND b.LNG_VAL = a.LNG_VAL AND b.VLD_DAT <= ? )";
        try {
             return jdbcTemplate.queryForObject(query,
                    new Object[]{
                 	        cmpVal   ,
                 	       frdCclTypVal,
                                usrLngVal,
                 	        vldDat   ,
                                 },
                    new OFdfCclRowMapper());

        }
         catch (EmptyResultDataAccessException e) {
             BigDecimal codError = new BigDecimal(20001);
	     OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "FDF", "FRD_CCL_TYP_VAL", cmpVal);
	     NwtException exception = new NwtException(error.getErrIdnVal());
	     exception.add(error);
	     throw exception;
        }
    }

        
}
