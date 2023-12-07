package com.mapfre.tron.api.trn.ntf.dl;

import java.math.BigDecimal;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

import lombok.Data;

@Data
@Repository
public class DlTrnNtfDAOImpl implements IDlTrnNtfDAO {
    
	protected static final String DCN_VAL = "DCN_VAL";

	protected JdbcTemplate jdbcTemplate;

	    @Autowired
		DlTrnErr lvDlTrnErr;

	    @Autowired
	    public void setDataSource(@Qualifier("dsTwDl") final DataSource dataSource) {
	        jdbcTemplate = new JdbcTemplate(dataSource);
	        final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
	        jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
	    }   
	    
    // Hallar codigo de documento
    @Override 
    public OTrnNtfS get_Ntf_001(BigDecimal cmpVal, String dcnVal, Date vldDat, String usrLngVal) {
	
	final String query = "SELECT a.CMP_VAL, a.DCN_VAL, a.VLD_DAT "
		+ "FROM DF_TRN_NWT_XX_NTF a , DF_TRN_NWT_XX_NTF_DSP, G1010031 WHERE "
		+ "a.CMP_VAL = ? AND a.DCN_VAL = ? AND a.VLD_DAT = (SELECT MAX(VLD_DAT) "  
		+ "FROM DF_TRN_NWT_XX_NTF b  "  
		+ "where b.CMP_VAL = a.CMP_VAL  "  
		+ "AND b.DCN_VAL = a.DCN_VAL  "  
		+ "AND b.NTF_TYP_VAL = a.NTF_TYP_VAL  "  
		+ "AND (? IS NULL OR  "  
		+ "b.VLD_DAT <= ? ))  "  
		+ "AND DF_TRN_NWT_XX_NTF_DSP.CMP_VAL = a.CMP_VAL "
		+ "AND DF_TRN_NWT_XX_NTF_DSP.DCN_VAL = a.DCN_VAL "
		+ "AND DF_TRN_NWT_XX_NTF_DSP.LNG_VAL =? "
		+ "AND G1010031.COD_IDIOMA = DF_TRN_NWT_XX_NTF_DSP.LNG_VAL "
		+ "AND G1010031.COD_CAMPO = 'NTF_TYP_VAL' "         
		+ "AND a.NTF_TYP_VAL =G1010031.COD_VALOR "
		+ "AND G1010031.COD_RAMO  = '999' "
		+ "AND G1010031.COD_IDIOMA =? "
		+ "AND G1010031.COD_CIA = a.CMP_VAL";
	
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		dcnVal,
	                		vldDat,
	                		vldDat,
	                		usrLngVal,
	                		usrLngVal
	                },
	                new OTrnNtfRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", DCN_VAL, cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
}
    }

    //Hallar código de documento 2
    @Override
    public OTrnNtfS get_Ntf_002(BigDecimal cmpVal, String dcnVal, String ntfTypVal, Date vldDat, String usrLngVal) {
	final String query = "SELECT a.CMP_VAL, a.DCN_VAL, a.NTF_TYP_VAL, a.VLD_DAT "
		+ "FROM DF_TRN_NWT_XX_NTF a , DF_TRN_NWT_XX_NTF_DSP c, G1010031 d WHERE "
		+ "a.CMP_VAL = ? AND a.DCN_VAL = ? AND a.DSB_ROW='N' AND a.NTF_TYP_VAL=? AND a.VLD_DAT = (SELECT MAX(VLD_DAT) "  
		+ "FROM DF_TRN_NWT_XX_NTF b  "  
		+ "where b.CMP_VAL = a.CMP_VAL  "  
		+ "AND b.DCN_VAL = a.DCN_VAL  "  
		+ "AND b.NTF_TYP_VAL = a.NTF_TYP_VAL  "  
		+ "AND b.DSB_ROW='N' "
		+ "AND (? IS NULL OR  "  
		+ "b.VLD_DAT <= ? )) "  
		+ "AND c.CMP_VAL = a.CMP_VAL "
		+ "AND c.DCN_VAL = a.DCN_VAL "
		+ "AND c.LNG_VAL =? "
		+ "AND d.COD_IDIOMA = c.LNG_VAL "
		+ "AND d.COD_CAMPO = 'NTF_TYP_VAL' "         
		+ "AND a.NTF_TYP_VAL =d.COD_VALOR "
		+ "AND d.COD_RAMO  = '999' "
		+ "AND d.COD_IDIOMA =? "
		+ "AND d.COD_CIA = a.CMP_VAL";
	
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		dcnVal,
	                		ntfTypVal,
	                		vldDat,
	                		vldDat,
	                		usrLngVal,
	                		usrLngVal
	                },
	                new OTrnNtf2RowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", DCN_VAL, cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
}
    }

    //Hallar código de documento 3
    @Override
    public OTrnNtfS get_Ntf_003(BigDecimal cmpVal, String dcnVal, Date vldDat, String usrLngVal) {
	
	final String query = "SELECT a.CMP_VAL, a.DCN_VAL, a.VLD_DAT "
		+ "FROM DF_TRN_NWT_XX_NTF a , DF_TRN_NWT_XX_NTF_DSP, G1010031 WHERE "
		+ "a.CMP_VAL = ? AND a.DCN_VAL = ? AND DSB_ROW='N' AND a.VLD_DAT = (SELECT MAX(VLD_DAT) "  
		+ "FROM DF_TRN_NWT_XX_NTF b "  
		+ "where b.CMP_VAL = a.CMP_VAL "  
		+ "AND b.DCN_VAL = a.DCN_VAL "  
		+ "AND b.NTF_TYP_VAL = a.NTF_TYP_VAL "  
		+ "AND (? IS NULL OR "  
		+ "b.VLD_DAT <= ? )) "  
		+ "AND DF_TRN_NWT_XX_NTF_DSP.CMP_VAL = a.CMP_VAL "
		+ "AND DF_TRN_NWT_XX_NTF_DSP.DCN_VAL = a.DCN_VAL "
		+ "AND DF_TRN_NWT_XX_NTF_DSP.LNG_VAL =? "
		+ "AND G1010031.COD_IDIOMA = DF_TRN_NWT_XX_NTF_DSP.LNG_VAL "
		+ "AND G1010031.COD_CAMPO = 'NTF_TYP_VAL' "         
		+ "AND a.NTF_TYP_VAL =G1010031.COD_VALOR "
		+ "AND G1010031.COD_RAMO  = '999' "
		+ "AND G1010031.COD_IDIOMA =? "
		+ "AND G1010031.COD_CIA = a.CMP_VAL";
	
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		dcnVal,
	                		vldDat,
	                		vldDat,
	                		usrLngVal,
	                		usrLngVal
	                },
	                new OTrnNtfRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", DCN_VAL, cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
}
    }
    //Hallar el ID de operación
    
    @Override
    public OTrnNtfS get_Nod_002(BigDecimal cmpVal, String oprIdnVal, String usrLngVal) {
	
	final String query = "SELECT a.OPR_IDN_VAL FROM "
    		+ "DF_TRN_NWT_XX_NOD a WHERE "
    		+ "a.OPR_IDN_VAL=? "
    		+ "AND a.LNG_VAL = ? ";
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{	   
	                		oprIdnVal,
	                		usrLngVal
	                },
	                new OTrnNtfNodRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "OPR_IDN_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
    
    }
    
   
     // Hallar la actividad
   public OTrnNtfS get_thpAcvVal(BigDecimal cmpVal, BigDecimal thpAcvVal, String usrLngVal){
      
	final String query = "SELECT a.COD_ACT_TERCERO "
    		+ "FROM A1002200 a "
    		+ "WHERE a.COD_ACT_TERCERO=? AND "
    		+ "a.COD_CIA = ?";
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	      
	                		thpAcvVal,
	                		cmpVal
	                		
	                },
	                new OTrnNtfAcvValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "THP_ACV_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
   }
    // Hallar tipo de gestor
     
      public OTrnNtfS get_MnrTypVal(BigDecimal cmpVal, String mnrTypVal, String usrLngVal){
      
      final String query = "SELECT a.COD_CIA, a.TIP_GESTOR "
    		+ "FROM A5020200 a, G1010031 b "
    		+ "WHERE a.COD_CIA=? AND a.TIP_GESTOR=? AND "
    		+ "b.cod_ramo = 999 AND b.cod_campo = 'TIP_CLASE_GESTOR' " 
    		+ "AND b.cod_valor = a.tip_clase_gestor "
    		+ "AND b.cod_idioma = ? "
    		+ "AND a.COD_CIA = ? AND a.COD_CIA = b.COD_CIA";
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		mnrTypVal,
	                		usrLngVal,
	                		cmpVal
	                },
	                new OTrnNtfMnrTypValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "MNR_TYP_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
  }
    //Halla nivel
   
     public OTrnNtfS get_lvlVal(BigDecimal cmpVal, String lvlVal, String usrLngVal){
     
      final String query = "SELECT a.COD_NIVEL "
    		+ "FROM A1000709 a "
    		+ "WHERE a.COD_NIVEL =? AND "
    		+ "a.cod_cia = ?";
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	
	                		lvlVal,
	                		cmpVal
	                },
	                new OTrnNtfLvlValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "LVL_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
     }	
    
    // Hallar clase de asiento
     
     public OTrnNtfS get_encVal(BigDecimal cmpVal, String encVal, String usrLngVal){
   
       final String query = "SELECT a.COD_CLASE_ASTO FROM A5100715 a "
		+ "WHERE a.COD_CLASE_ASTO=? AND a.cod_cia = ?";
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		encVal,
	                		cmpVal
	                },
	                new OTrnNtfEncValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "ENC_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
   }
    // Hallar trámite
    @Override
    public OTrnNtfS get_pcsVal(BigDecimal cmpVal, String pcsVal, String usrLngVal) {
	
	final String query = "SELECT a.COD_CIA, a.COD_TRAMITE FROM g7500020 a "
		+ "WHERE a.COD_CIA = ? AND a.COD_TRAMITE=?";
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		pcsVal
	                		
	                },
	                new OTrnNtfPcsValRowMapper ());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "PCS_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
    
    }
    
    //Hallar nivel3
    @Override
    public OTrnNtfS get_thrLvl(BigDecimal cmpVal, BigDecimal frsLvlVal,BigDecimal scnLvlVal, BigDecimal thrLvlVal, String usrLngVal) {
	
	final String query = "SELECT b.COD_NIVEL1, b.COD_NIVEL2, b.COD_NIVEL3 FROM a1000702 b "
		+ "WHERE b.COD_NIVEL1=? AND b.COD_NIVEL2=? AND b.COD_NIVEL3 = ? "
		+ "AND b.cod_cia = ? "
		+ "AND b.cod_nivel1 = nvl(?,b.cod_nivel1) "
		+ "AND b.cod_nivel2 = nvl(?,b.cod_nivel2) "
		+ "AND b.mca_inh = 'N'";
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		frsLvlVal,
	                		scnLvlVal,
	                		thrLvlVal,
	                		cmpVal,
	                		frsLvlVal,
	                		scnLvlVal
	                		
	                },
	                new OtrnNtfThrLvlRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "THR_LVL_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
    
    }
    
     //Hallar acuerdo/contrato
    @Override
    public OTrnNtfS get_delVal(BigDecimal cmpVal, BigDecimal delVal, BigDecimal lobVal, String usrLngVal) {
	
	final String query = "SELECT a.COD_CIA, a.NUM_CONTRATO FROM g2990001 a WHERE a.COD_CIA=? AND a.NUM_CONTRATO=? "
		+ "AND a.cod_cia = TO_NUMBER(?) " 
		+ "AND EXISTS (SELECT '' FROM g2990000 WHERE cod_cia = TO_NUMBER(?) AND num_contrato = a.num_contrato AND cod_ramo IN (TO_NUMBER(?),999)) "  
		+ "AND EXISTS (SELECT '' FROM a2000010 WHERE cod_cia = TO_NUMBER(?) AND num_contrato = a.num_contrato AND num_poliza = NVL(null,a2000010.num_poliza))";
		
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		delVal,
	                		cmpVal,
	                		cmpVal,
	                		lobVal,
	                		cmpVal,
	                		
	                },
	                new OTrnNtfDelValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "DEL_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
    
    }
    
    //Hallar subcontrato
    @Override
    public OTrnNtfS get_sblVal(BigDecimal cmpVal,  BigDecimal lobVal, BigDecimal delVal, BigDecimal sblVal, String usrLngVal) {
	
	final String query = "SELECT a.COD_CIA, a.COD_RAMO, a.NUM_CONTRATO, a.NUM_SUBCONTRATO FROM g2990021 a, g2990018 b "
		+ "WHERE a.cod_cia = TO_NUMBER(?) "
		+ "AND a.cod_ramo = NVL(TO_NUMBER(?),a.cod_ramo) "
		+ "AND a.num_contrato = NVL(TO_NUMBER(?),a.num_contrato) "
		+ "AND a.cod_cia = b.cod_cia "
		+ "AND a.num_subcontrato = b.num_subcontrato "
		+ "AND a.num_subcontrato = ?";
	
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		lobVal,
	                		delVal,	            
	                		sblVal
	                		
	                },
	                new OTrnNtfSblValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "SBL_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
    
    }
    
    //Hallar Plan Tramitación
    @Override
    public OTrnNtfS get_hpnVal(BigDecimal cmpVal, String hpnVal, BigDecimal lobVal, String filTypVal, String usrLngVal) {
	
	final String query = "SELECT b.COD_CIA, b.COD_PLAN FROM G7500000 b "
		+ "WHERE b.COD_CIA=? AND b.COD_PLAN=? "
		+ "AND b.cod_cia=? "
		+ "AND b.mca_inh='N' "
		+ "AND b.cod_plan in (select cod_plan from G7000100 c where c.cod_cia=b.cod_cia and b.mca_inh='N' and c.tip_exp=? and c.cod_ramo=?)";
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                		cmpVal,
	                		hpnVal,
	                		cmpVal,
	                		filTypVal,
	                		lobVal
	                		
	                },
	                new OTrnNtfHpnValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "HPN_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
    
    }
    
    //Hallar tipo expediente
    @Override
    public OTrnNtfS get_filTypVal(BigDecimal cmpVal, String filTypVal, String usrLngVal) {
	
	final String query = "SELECT a.COD_CIA, a.TIP_EXP FROM g7000090 a "
		+ "WHERE a.TIP_EXP=? "
		+ "AND a.cod_cia=? ";
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	
	                		filTypVal,
	                		cmpVal
	                		
	                },
	                new OTrnNtfFilTypValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "FIL_TYP_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
    
    }

    //Hallar poliza de grupo
   @Override
    public OTrnNtfS get_gppVal_002(BigDecimal cmpVal, String gppVal, BigDecimal lobVal, String usrLngVal) {
	
	final String query = "SELECT A2000010.COD_CIA, A2000010.NUM_POLIZA FROM A2000010, g2990017 "
		+ "WHERE A2000010.COD_CIA=? AND A2000010.NUM_POLIZA=? "
		+ "AND a2000010.cod_cia = TO_NUMBER(?) AND g2990017.cod_cia = a2000010.cod_cia "
		+ "AND g2990017.num_poliza = a2000010.num_poliza AND EXISTS (SELECT '' FROM g2990000 "
		+ "WHERE g2990000.cod_cia = TO_NUMBER(?) AND g2990000.num_contrato = a2000010.num_contrato "
		+ "AND g2990000.cod_ramo IN ( DECODE(?,NULL,g2990000.cod_ramo,TO_NUMBER(?)),999)) "
		+ "AND a2000010.ROWID = (SELECT MIN(ROWID) FROM a2000010 b WHERE b.cod_cia = TO_NUMBER(?) AND b.num_poliza = a2000010.num_poliza) ";
	

	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	
	                		cmpVal,
	                		gppVal,
	                		cmpVal,
	                		cmpVal,
	                		lobVal,
	                		lobVal,
	                		cmpVal
	                		
	                },
	                new OTrnNtfGppValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "GPP_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
    }
    
    }
   
   //Hallar código de suplemento
   
   @Override
   public OTrnNtfS get_enrVal(BigDecimal cmpVal, BigDecimal enrVal, String enrTypVal, BigDecimal enrSbdVal, String usrLngVal) {
	
	final String query = "SELECT a.COD_SPTO FROM a2991800 a WHERE a.COD_SPTO=? AND a.SUB_COD_SPTO=?"
		+ "AND a.COD_CIA = ? AND a.MCA_INH ='N' AND a.TIP_SPTO=? ";
		
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	
	                	enrVal,
	                	enrSbdVal,
	                	cmpVal,
	                	enrTypVal
	                		
	                },
	                new OTrnNtfEnrValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "ENR_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
   }
   
   }
   
  //Hallar suplemento
   
   @Override
   public OTrnNtfS get_enrSbdVal(BigDecimal cmpVal, BigDecimal enrVal, String enrTypVal, BigDecimal enrSbdVal, String usrLngVal) {
	
	final String query = "SELECT a.SUB_COD_SPTO FROM a2991800 a WHERE a.SUB_COD_SPTO=? "
		+ "AND a.cod_cia = ? and a.MCA_INH ='N' AND a.TIP_SPTO=? and COD_SPTO=?";
		
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	
	                	enrSbdVal,
	                	cmpVal,	          	
	                	enrTypVal,
	                	enrVal
	                		
	                },
	                new OTrnNtfEnrSbdValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "ENR_SBD_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
   }
   
   }
   
   // Tercer canal distribución
   
   @Override
   public OTrnNtfS get_thrDstHnlNam(BigDecimal cmpVal, String thrDstHnlVal,String usrLngVal) {
	
	final String query = "SELECT a.COD_CIA, a.COD_CANAL3 FROM a1000723 a where " + 
		"a.COD_CIA=? AND a.COD_CANAL3=? " + 
		"AND a.COD_CIA = TO_NUMBER(?) and a.MCA_INH = 'N'";
	

	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	
	                	cmpVal,
	                	thrDstHnlVal,
	                	cmpVal
	                		
	                },
	                new OTrnNtfThrDstHnlNamRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "THR_DST_HNL_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
   }
   
   }

   // validar que el campo de la plantilla de diseño
   @Override
   public OTrnNtfS get_tplLytVal(BigDecimal cmpVal, String tplLytVal, String usrLngVal) {
	
	final String query = "select a.RER_IDN_DFN_VAL from DF_CMN_NWT_XX_RTC a where a.RER_IDN_DFN_VAL =? and a.DSB_ROW='N' ";
		
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	
	                	tplLytVal
	                		
	                },
	                new OTrnNtfTplTypValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "TPL_LYT_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
   }
   
   }
   
   // validar tipo notificación dependiendo de lista opciones
   @Override
   public OTrnNtfS get_NtfVal(BigDecimal cmpVal, String dcnVal, String ntfTypVal, Date vldDat, String usrLngVal) {
	
	final String query = "select a.CMP_VAL, a.DCN_VAL, a.NTF_TYP_VAL, a.VLD_DAT from DF_TRN_NWT_XX_NTF a where "  
		+ "a.CMP_VAL = ?  AND a.DSB_ROW = 'N' AND a.DCN_VAL =? "  
		+ "AND a.NTF_TYP_VAL =? "  
		+ "AND a.VLD_DAT = (SELECT MAX(VLD_DAT) "  
		+ "FROM DF_TRN_NWT_XX_NTF b " 
		+ "where b.CMP_VAL = a.CMP_VAL " 
		+ "AND b.DCN_VAL = a.DCN_VAL "  
		+ "AND b.NTF_TYP_VAL = a.NTF_TYP_VAL "  
		+ "AND b.DSB_ROW = 'N' "  
		+ "AND (? IS NULL OR "  
		+ "b.VLD_DAT <= ?))";
	
	try {
	    return jdbcTemplate.queryForObject(query,
	                new Object[]{
	                	
	                	cmpVal,
	                	dcnVal,
	                	ntfTypVal,
	                	vldDat,
	                	vldDat
	                		
	                },
	                new OTrnNtfNtfValRowMapper());
	
	}
	catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "NTF", "NTF_TYP_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
   }
   
   }
   
   
}
