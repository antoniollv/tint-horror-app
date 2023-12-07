package com.mapfre.tron.api.tpd.sps.dl;

import java.math.BigDecimal;
import java.util.Date;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.tpd.sps.bo.OTpdSpsS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlTpdSpsDAOImpl implements IDlTpdSpsDAO {

    protected static final String SPL_CTG_VAL = "SPL_CTG_VAL";

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
    public OTpdSpsS get(BigDecimal cmpVal, String tylVal, String lngVal, String usrLngVal) {

	final String query = "SELECT a.CMP_VAL, a.TYL_VAL, a.LNG_VAL ,a.TYL_NAM, a.DSB_ROW, a.USR_VAL, a.MDF_DAT, a.REA_TYL_VAL "
		+ "FROM DF_TPD_NWT_XX_SPS_TYL a " + "WHERE a.CMP_VAL = ? AND a.TYL_VAL = ? AND a.LNG_VAL = ?";
	try {
	    return jdbcTemplate.queryForObject(query, 
		    new Object[] { 
			    cmpVal, 
			    tylVal, 
			    lngVal,
			    },
		    new OTpdSpsTylRowMapper());

	    
	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SPS", "TYL_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

    @Override
    public OTpdSpsS get_splCtg(BigDecimal cmpVal, String splCtgVal, String lngVal, String usrLngVal) {

	final String query = "SELECT a.CMP_VAL, a.SPL_CTG_VAL, a.LNG_VAL, a.CTG_NAM, a.DSB_ROW, a.USR_VAL, a.MDF_DAT, a.REA_SPL_CTG_VAL "
		+ "FROM DF_TPD_NWT_XX_SPS_CTG a " + "WHERE a.CMP_VAL = ? AND a.SPL_CTG_VAL = ? AND a.LNG_VAL = ?";

	try {
	    return jdbcTemplate.queryForObject(query, 
		    new Object[] { 
			    cmpVal, 
			    splCtgVal, 
			    lngVal, 
			},
		    new OTpdSpsCtgRowMapper());

	    
	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SPS", SPL_CTG_VAL, cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

    @Override
    public OTpdSpsS get_tylTya(BigDecimal cmpVal, BigDecimal thpAcvVal, String tylVal, String usrLngVal) {

	final String query = "SELECT a.CMP_VAL, a.THP_ACV_VAL, a.TYL_VAL, a.DSB_ROW, a.USR_VAL, a.MDF_DAT "
		+ "FROM DF_TPD_NWT_XX_SPS_TYA a " + "WHERE a.CMP_VAL = ? AND a.THP_ACV_VAL = ? AND a.TYL_VAL = ?";

	try {
	    return jdbcTemplate.queryForObject(query, 
		    new Object[] {
			    cmpVal, 
			    thpAcvVal, 
			    tylVal, 
			},
		    new OTpdSpsTyaRowMapper());

	    
	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SPS", "TYL_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

    @Override
    public OTpdSpsS get_spsTca(BigDecimal cmpVal, BigDecimal thpAcvVal, String tylVal, String splCtgVal, Date vldDat,
	    String usrLngVal) {

	final String query = "SELECT a.CMP_VAL, a.THP_ACV_VAL, a.TYL_VAL, a.SPL_CTG_VAL, a.VLD_DAT, a.DSB_ROW, a.USR_VAL, a.MDF_DAT "
		+ "FROM DF_TPD_NWT_XX_SPS_TCA a "
		+ "WHERE a.cmp_val = ? AND a.thp_acv_val = ? AND a.tyl_val = ? AND a.spl_ctg_val = ? AND a.vld_dat = (SELECT MAX(b.vld_dat) FROM DF_TPD_NWT_XX_SPS_TCA b WHERE b.cmp_val = a.cmp_val AND b.thp_acv_val = a.thp_acv_val AND b.tyl_val = a.tyl_val AND b.spl_ctg_val = a.spl_ctg_val AND b.vld_dat <= ? ) ";

	try {
	    return jdbcTemplate.queryForObject(query,
		    new Object[] { 
			    cmpVal, 
			    thpAcvVal,
			    tylVal, 
			    splCtgVal, 
			    vldDat, 
			    }, 
		    new OTpdSpsTcaRowMapper());

	    
	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SPS", SPL_CTG_VAL, cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

    @Override
    public OTpdSpsS get_007(BigDecimal cmpVal, BigDecimal thpAcvVal, String tylVal, String splCtgVal, Date vldDat, String usrLngVal) {

	final String query = "SELECT a.CMP_VAL, a.THP_ACV_VAL, a.TYL_VAL, a.SPL_CTG_VAL, a.VLD_DAT "
		+ "FROM DF_TPD_NWT_XX_SPS_TCA a, DF_TPD_NWT_XX_SPS_CTG c "
		+ "WHERE a.cmp_val = ? AND a.thp_acv_val = ? AND a.tyl_val=? AND a.spl_ctg_val=? "
		+ "AND a.dsb_row = 'N' AND a.vld_dat = (SELECT MAX(b.vld_dat) FROM DF_TPD_NWT_XX_SPS_TCA b "
		+ "WHERE b.cmp_val = a.cmp_val AND b.thp_acv_val = a.thp_acv_val AND b.tyl_val = a.tyl_val "
		+ "AND b.spl_ctg_val = a.spl_ctg_val AND b.vld_dat <= ? ) "
		+ "AND c.cmp_val = a.cmp_val " 
		+ "AND c.spl_ctg_val = a.spl_ctg_val " 
		+ "AND c.rea_spl_ctg_val = 'S' "
		+ "AND c.lng_val = ?";
	try {
	    return jdbcTemplate.queryForObject(query,
		    new Object[] { 
			    
			    cmpVal, 
			    thpAcvVal, 
			    tylVal, 
			    splCtgVal, 
			    vldDat, 
			    usrLngVal 
			}, 
		    new OTpdSpsGet7RowMapper());

	    
	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "SPS", SPL_CTG_VAL, cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }
}
