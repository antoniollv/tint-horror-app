package com.mapfre.tron.api.fdf.afn.dl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.fdf.afn.bo.OFdfAfnS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import lombok.Data;


@Data
@Repository
public class DlFdfAfnDAOImpl implements IDlFdfAfnDAO {

    protected static final String AFR_NDC_VAL = "AFR_NDC_VAL";

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
    public OFdfAfnS get(BigDecimal cmpVal, String afrNdcVal, Date vldDat,  String usrLngVal) {
	 
	final String query = "SELECT a.CMP_VAL, a.AFR_NDC_VAL, a.VLD_DAT, a.ISU_AFR_NDC, a.LSS_AFR_NDC, a.DSB_ROW "
	                   + "FROM DF_FDF_NWT_XX_AFN a "
	                   + "WHERE a.CMP_VAL = ? AND a.AFR_NDC_VAL = ? AND a.VLD_DAT = "
	                   + "(SELECT MAX(b.VLD_DAT) FROM DF_FDF_NWT_XX_AFN b WHERE b.CMP_VAL = a.CMP_VAL AND b.AFR_NDC_VAL = a.AFR_NDC_VAL AND b.VLD_DAT <= ? )";
        try {
            return jdbcTemplate.queryForObject(query,
                    new Object[]{
                 	         cmpVal   ,
                 	         afrNdcVal,
                 	         vldDat   ,
                                 },
                    new OFdfAfnRowMapper());


        }
         catch (EmptyResultDataAccessException e) {
             BigDecimal codError = new BigDecimal(20001);
	     OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "AFN", AFR_NDC_VAL, cmpVal);
	     NwtException exception = new NwtException(error.getErrIdnVal());
	     exception.add(error);
	     throw exception;
        }
    }

    @Override
    public OFdfAfnS get_afp(BigDecimal cmpVal, String afrNdcVal, String lngVal, String usrLngVal) {
	 
	final String query = "SELECT a.CMP_VAL, a.AFR_NDC_VAL, a.LNG_VAL, a.AFR_NDC_NAM "
                           + "FROM DF_FDF_NWT_XX_AFP a "
                           + "WHERE a.CMP_VAL = ? AND a.AFR_NDC_VAL = ? AND a.LNG_VAL = ? " ;
        try {
            return jdbcTemplate.queryForObject(query,
                    new Object[]{
     	                         cmpVal   ,
     	                         afrNdcVal,
     	                         lngVal   ,
                                },
                    new OFdfAfpRowMapper());

        }
         catch (EmptyResultDataAccessException e) {
             BigDecimal codError = new BigDecimal(20001);
             OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "AFN", AFR_NDC_VAL , cmpVal);
             NwtException exception = new NwtException(error.getErrIdnVal());
             exception.add(error);
             throw exception;
        }
    }

    @Override
    public List<OFdfAfnS> get_afnPT(BigDecimal cmpVal, String afrNdcVal, String usrLngVal) {
	 
	final String query = "SELECT a.CMP_VAL, a.AFR_NDC_VAL, a.LNG_VAL, a.AFR_NDC_NAM "
                           + "FROM DF_FDF_NWT_XX_AFP a "
                           + "WHERE a.CMP_VAL = ? AND a.AFR_NDC_VAL = ? " ;
        
        List<OFdfAfnS> lvOFdfAfnPT = jdbcTemplate.query(query,
                new Object[]{
	                     cmpVal   ,
	                     afrNdcVal
                            },
                new OFdfAfpRowMapper());
        //
        if (lvOFdfAfnPT.isEmpty()) {
		BigDecimal codError = new BigDecimal(20001);
	        OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "AFN", AFR_NDC_VAL, cmpVal);
	        NwtException exception = new NwtException(error.getErrIdnVal());
	        exception.add(error);
	        throw exception;
        }
        //            
        return lvOFdfAfnPT;
    }
        
}
