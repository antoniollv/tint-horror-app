package com.mapfre.tron.api.cmn.men.dl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.pgm.bo.OTrnPgmS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.pgm.dl.IDlPgmDAOImpl;
import com.mapfre.tron.api.pgm.dl.OPgmRowMapper;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@Data
public class OCmnMenDaoImpl implements OCmnMenDao {
    
    protected JdbcTemplate jdbcTemplate;
    
    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
    }
    
    private final static String QUERY = "select "
    	+ "d.cmp_val, "
    	+ "d.agr_val_prn, "
    	+ "d.opr_idn_val, "
    	+ "i.txt_nam, "
    	+ "case when h.fun_opr_url is not null then CONCAT(a.bas_url, h.fun_opr_url) else null end as app_url, " 
    	+ "d.pgm_dsb, "
    	+ "d.men_sqn_val, "
    	+ "h.pgm_val, "
    	+ "DECODE(j.mca_tar, 'S', h.pgm_val, NULL) as tsk_val "
    	+ "FROM G1010210 b, "  
    	+      "G1010220 c, " 
    	+      "RL_CMN_NWT_XX_MEN d, " 
    	+      "T_TRN_TRN_D_PGM_LNK h, "
    	+      "DF_TRN_NWT_XX_TXT_SET i, " 
    	+      "PROGRAMAS j, "
    	+      "DF_TRN_NWT_XX_PGM_APP a ";
    private static final String RESTRICTION = "WHERE "
    	+ "c.cod_usr = :usrVal " + 
    	"AND b.cod_rol = c.cod_rol " + 
    	"AND b.cod_pgm = h.pgm_val " + 
    	"AND h.opr_idn_val = d.opr_idn_val " + 
    	"AND h.opr_idn_val = i.txt_idn " + 
    	"AND i.lng_val = :lngVal " + 
    	"AND d.cmp_val = :cmpVal " + 
    	"AND b.cod_pgm = j.cod_pgm " + 
    	"AND nvl(h.app_val,'ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ') = a.app_val " +
    	"GROUP BY d.cmp_val, d.agr_val_prn, d.opr_idn_val, i.txt_nam, "
    	+ "a.bas_url, " + 
    	"d.men_sqn_val, h.fun_opr_url, d.pgm_dsb, h.pgm_val, " + 
    	"DECODE(j.mca_tar, 'S', h.pgm_val, NULL) " + 
    	"ORDER BY d.agr_val_prn, d.men_sqn_val";
    private static final OCmnMenRowMapper MAPPER = new OCmnMenRowMapper();
    
    @Autowired @Qualifier("jdbcFetch1000") protected NamedParameterJdbcTemplate jdbcTemplateAll;
    @Autowired @Qualifier("jdbcFetch1") protected NamedParameterJdbcTemplate jdbcTemplateOne;
    
    @Override
    @Cacheable("Menu")
    public List<OCmnMenS> getAll(BigDecimal cmpVal, String usrVal, String lngVal) {
	try {
	    return jdbcTemplateAll.query(QUERY + RESTRICTION, getParametersMap(cmpVal,usrVal,lngVal), MAPPER);
	} catch (EmptyResultDataAccessException e) {
	    log.error("Error recovering menu: {}", e);
	    throw new NwtException("Can't recover menu");
	}
    }
    
    private Map<String,Object> getParametersMap(BigDecimal cmpVal, String usrVal, String lngVal){
	Map<String,Object> parameters = new HashMap<>();
	parameters.put("cmpVal", cmpVal);
	parameters.put("usrVal", usrVal);
	parameters.put("lngVal",lngVal);
	return parameters;
	
    }

    @Override
    public String getUrlBase(String urlBase) {
	try {
	    Map<String,String> urlBaseMap = new HashMap<>();
	    urlBaseMap.put("urlBase", urlBase);
	    return jdbcTemplateOne.query("SELECT BAS_URL FROM DF_TRN_NWT_XX_PGM_APP t "
										   + "where APP_VAL = :urlBase", urlBaseMap , new ResultSetExtractor<String>(){
		@Override
		public String extractData(ResultSet rs)
		throws SQLException, DataAccessException {
		    rs.next(); 
		    return rs.getString("BAS_URL");
		} 
	    });
	} catch (EmptyResultDataAccessException e) {
	    log.error("Error recovering urlBase: {}", e);
	    throw new NwtException("Can't recover urlBase");
	}
    }
    
    @Override
    public List<String> getOprIdnValLst(String usrVal, BigDecimal cmpVal) {
	try {
	    Map<String,Object> urlBaseMap = new HashMap<>();
	    urlBaseMap.put("cmpVal", cmpVal);
	    urlBaseMap.put("usrVal", usrVal);
	    return jdbcTemplateOne.queryForList(
		"SELECT d.opr_idn_val " + 
	    	"FROM G1010210 b, " + 
	    	"G1010220 c, " + 
	    	"RL_CMN_NWT_XX_MEN d, " + 
	    	"T_TRN_TRN_D_PGM_LNK h " + 
	    	"WHERE c.cod_usr = :usrVal " + 
	    	"AND b.cod_rol = c.cod_rol " + 
	    	"AND b.cod_cia = c.cod_cia " + 
	    	"AND b.cod_pgm = h.pgm_val " + 
	    	"AND h.opr_idn_val = d.opr_idn_val " + 
	    	"AND d.cmp_val = :cmpVal " + 
	    	"GROUP BY d.cmp_val, d.agr_val_prn, d.opr_idn_val, d.men_sqn_val, d.pgm_dsb " + 
	    	"ORDER BY d.agr_val_prn, d.men_sqn_val", urlBaseMap ,String.class);

 } catch (EmptyResultDataAccessException e) {
     log.error("Error recovering identifiers: {}", e);
     throw new NwtException("Can't recover menu identifiers");
 }	    
    }
    
    
    
    @Override
    public OCmnMenS get_agrValPrn(BigDecimal cmpVal, String agrValPrn, String usrLngVal) {
	// TODO Auto-generated method stub
	final String query = "SELECT a.txt_idn "
                + "FROM DF_TRN_NWT_XX_TXT_SET a "
                + "WHERE a.lng_val = ? "
                + "AND a.ins_val IN (SELECT b.val_param FROM G0000000 b WHERE b.cod_param ='COD_INSTALACION' AND b.cod_cia = ?) "
                + "AND a.txt_idn IN (SELECT DISTINCT(b.opr_idn_val) FROM T_TRN_TRN_D_PGM_LNK b WHERE b.opr_idn_val IS NOT NULL AND b.fun_opr_url IS NULL) "
                + "AND a.txt_idn = ?";
	
	try {
	    OCmnMenS lvOCmnMenS = jdbcTemplate.queryForObject(query,
                   new Object[]{
                	   	usrLngVal,       
                	   	cmpVal,
                	   	agrValPrn,
                                },
                   new OMenAgrValPrnRowMapper());

            return lvOCmnMenS;
       }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MEN", "AGR_VAL_PRN", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
       }
	
    }
    
    @Override
    public OCmnMenS get_oprIdnVal(BigDecimal cmpVal, String oprIdnVal, String usrLngVal) {
	// TODO Auto-generated method stub
	final String query = "SELECT a.txt_idn "
                + "FROM DF_TRN_NWT_XX_TXT_SET a "
                + "WHERE a.lng_val = ? "
                + "AND a.ins_val IN (SELECT b.val_param FROM G0000000 b WHERE b.cod_param ='COD_INSTALACION' AND b.cod_cia = ?) "
                + "AND a.txt_idn IN (SELECT DISTINCT(b.opr_idn_val) FROM T_TRN_TRN_D_PGM_LNK b WHERE b.opr_idn_val IS NOT NULL) "
                + "AND a.txt_idn = ?";
	
	try {
	    OCmnMenS lvOCmnMenS = jdbcTemplate.queryForObject(query,
                   new Object[]{
                	   	usrLngVal,       
                	   	cmpVal,
                	   	oprIdnVal,
                                },
                   new OMenOprIdnValRowMapper());

            return lvOCmnMenS;
       }
        catch (EmptyResultDataAccessException e) {
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MEN", "OPR_IDN_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
       }
	
    }
    
    @Override
    public OCmnMenS get_menSqnVal(BigDecimal cmpVal, String agrValPrn,BigDecimal menSqnVal, String usrLngVal) {
	// TODO Auto-generated method stub
	final String query = "SELECT a.men_sqn_val "
                + "FROM RL_CMN_NWT_XX_MEN a "
                + "WHERE a.cmp_val = ? "
                + "AND a.agr_val_prn = ? "
                + "AND a.men_sqn_val = ? ";
	
	OCmnMenS lvOCmnMenS = null ;
	try {
	     lvOCmnMenS = jdbcTemplate.queryForObject(query,
                   new Object[]{
                	   	cmpVal,
                	   	agrValPrn,
                	   	menSqnVal, 
                                },
                   new OMenSqnValRowMapper());
	    
	    
            return lvOCmnMenS;
       }
        catch (EmptyResultDataAccessException e) {
            
            BigDecimal codError = new BigDecimal(20001);
            OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "MEN", "MEN_SQN_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
       }
	
    }
}
