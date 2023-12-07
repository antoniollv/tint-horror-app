package com.mapfre.tron.api.lsf.lsf.dl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.lsf.lsf.bo.OLsfLsfS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.prc.bo.OTrnPrcS;
import com.mapfre.tron.api.cmn.dl.CustomSQLErrorCodeTranslator;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.prc.dl.OTrnPrcRowMapper;

import lombok.Data;

@Data
@Repository
public class DlLsfLsfDAOImpl implements IDlLsfLsfDAO {

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
    public OLsfLsfS getCodRamo(BigDecimal cmpVal, BigDecimal lobVal, String usrLngVal) {

	final String query = "SELECT a.COD_CIA, a.COD_RAMO " + "FROM A1001800 a "
		+ "WHERE  a.COD_CIA = ? AND a.COD_RAMO = ?";
	try {
	    return jdbcTemplate.queryForObject(query, new Object[] { cmpVal, lobVal, }, new OLsfLsfCodRamRowMapper());

	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "LSF", "LOB_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

    @Override
    public OLsfLsfS getCodCob(BigDecimal cmpVal, BigDecimal lobVal, BigDecimal cvrVal, String usrLngVal) {

	final String query = "SELECT a.COD_CIA, a.COD_RAMO, a.COD_COB " + "FROM A1002150 a "
		+ "WHERE a.COD_CIA = ? " + "AND a.COD_RAMO = ? " + "AND a.COD_COB = ? "
		+ "AND a.FEC_VALIDEZ <= (SELECT MAX(b.FEC_VALIDEZ) FROM a1002150 b WHERE b.COD_CIA=a.COD_CIA AND b.COD_RAMO=a.COD_RAMO) "
		+ "AND rownum=1";

	try {
	    return jdbcTemplate.queryForObject(query, new Object[] { cmpVal, lobVal, cvrVal },
		    new OLsfLsfCodCobRowMapper());

	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "LSF", "CVR_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

    // Hallar tipo expediente
    @Override
    public OLsfLsfS getFilTypVal(BigDecimal cmpVal, String filTypVal, String usrLngVal) {

	final String query = "SELECT a.COD_CIA, a.TIP_EXP FROM g7000090 a " + "WHERE a.TIP_EXP=? " + "AND a.cod_cia=? ";

	try {
	    return jdbcTemplate.queryForObject(query, new Object[] {

		    filTypVal, cmpVal

	    }, new OLsfLsfFilTypValRowMapper());

	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "LSF", "FIL_TYP_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

    @Override
    public OTrnPrcS getNomPrg(String nomPrg, String usrLngVal, BigDecimal cmpVal, String msgVal) {
	final String query = "SELECT 0 as PRD_NAM " + "FROM dba_procedures a "
		+ "WHERE ((SUBSTR(A.OBJECT_NAME,0,LENGTH(A.OBJECT_NAME)-4) = upper(substr(?,0, INSTR(?,'.') -1 )) "
		+ "AND A.PROCEDURE_NAME = upper(substr(?,INSTR(?,'.')+1 , LENGTH(?)))) "
		+ "OR (A.OBJECT_NAME = upper(substr(?,0, INSTR(?,'.') -1 )) "
		+ "AND A.PROCEDURE_NAME = upper(substr(?,INSTR(?,'.')+1 , LENGTH(?))))) "
		+ "OR ((SUBSTR(A.OBJECT_NAME,0,LENGTH(A.OBJECT_NAME)-4) = (upper(?)) "
		+ "OR A.OBJECT_NAME = (upper(?))) " + "AND OBJECT_TYPE IN ('PROCEDURE', 'FUNCTION'))";

	try {
	    return jdbcTemplate.queryForObject(query, new Object[] { nomPrg, nomPrg, nomPrg, nomPrg, nomPrg, nomPrg,
		    nomPrg, nomPrg, nomPrg, nomPrg, nomPrg, nomPrg, }, new OTrnPrcRowMapper());

	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "LSF", msgVal, cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

    // Hallar acuerdo/contrato
    @Override
    public OLsfLsfS getDelVal(BigDecimal cmpVal, BigDecimal delVal, BigDecimal lobVal, String usrLngVal) {

	final String query = "SELECT a.COD_CIA, a.NUM_CONTRATO FROM g2990001 a WHERE a.COD_CIA=? AND a.NUM_CONTRATO=? "
		+ "AND a.cod_cia = TO_NUMBER(?) "
		+ "AND EXISTS (SELECT '' FROM g2990000 WHERE cod_cia = TO_NUMBER(?) AND num_contrato = a.num_contrato AND cod_ramo IN (TO_NUMBER(?),999)) "
		+ "AND EXISTS (SELECT '' FROM a2000010 WHERE cod_cia = TO_NUMBER(?) AND num_contrato = a.num_contrato AND num_poliza = NVL(null,a2000010.num_poliza))";

	try {
	    return jdbcTemplate.queryForObject(query, new Object[] { cmpVal, delVal, cmpVal, cmpVal, lobVal, cmpVal,

	    }, new OLsfLsfDelValRowMapper());

	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "LSF", "DEL_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}

    }

    // Hallar subcontrato
    @Override
    public OLsfLsfS getSblVal(BigDecimal cmpVal, BigDecimal lobVal, BigDecimal delVal, BigDecimal sblVal,
	    String usrLngVal) {

	final String query = "SELECT a.COD_CIA, a.COD_RAMO, a.NUM_CONTRATO, a.NUM_SUBCONTRATO FROM g2990021 a, g2990018 b "
		+ "WHERE a.cod_cia = TO_NUMBER(?) " + "AND a.cod_ramo = NVL(TO_NUMBER(?),a.cod_ramo) "
		+ "AND a.num_contrato = NVL(TO_NUMBER(?),a.num_contrato) " + "AND a.cod_cia = b.cod_cia "
		+ "AND a.num_subcontrato = b.num_subcontrato " + "AND a.num_subcontrato = ?";

	try {
	    return jdbcTemplate.queryForObject(query, new Object[] { cmpVal, lobVal, delVal, sblVal

	    }, new OLsfLsfSblValRowMapper());

	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "LSF", "SBL_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}

    }

    @Override
    public OLsfLsfS getSugVal(BigDecimal cmpVal, String sugVal, String usrLngVal) {

	final String query = "SELECT a.SUG_VAL, a.CMP_VAL " + "FROM DF_CMN_NWT_XX_SUB_SUG a "
		+ "WHERE a.CMP_VAL = ? AND a.SUG_VAL = ? AND a.LNG_VAL = ?";
	try {
	    return jdbcTemplate.queryForObject(query, new Object[] { cmpVal, sugVal, usrLngVal, },
		    new OLsfLsfSugRowMapper());

	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "LSF", "SUG_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }

    @Override
    public OLsfLsfS getSubVal(BigDecimal cmpVal, String subVal, String usrLngVal) {

	final String query = "SELECT DISTINCT(a.SUB_VAL) " + "FROM DF_CMN_NWT_XX_SUB a "
		+ "WHERE a.CMP_VAL = ? AND a.SUB_VAL = ? AND a.LNG_VAL = ?";
	try {
	    return jdbcTemplate.queryForObject(query, new Object[] { cmpVal, subVal, usrLngVal, },
		    new OLsfLsfSubRowMapper());

	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "LSF", "SUB_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }
    
    @Override
    public OLsfLsfS getSubVal2(BigDecimal cmpVal, String sugVal, String subVal, String usrLngVal) {

	final String query = "SELECT a.SUB_VAL FROM DF_CMN_NWT_XX_SUB_SUG_RLN a, DF_CMN_NWT_XX_SUB b " 
		+ "WHERE a.CMP_VAL= ? AND a.DSB_ROW = 'N' AND a.SUG_VAL = ? AND a.SUB_VAL = ? AND a.VLD_DAT = (SELECT MAX(VLD_DAT) "
		+ "FROM DF_CMN_NWT_XX_SUB_SUG_RLN c WHERE c.CMP_VAL = a.CMP_VAL AND c.SUG_VAL = a.SUG_VAL AND c.SUB_VAL = a.SUB_VAL) "
		+ "AND a.CMP_VAL = b.CMP_VAL AND a.SUB_VAL = b.SUB_VAL AND b.DSB_ROW ='N' AND b.LNG_VAL = ? "
		+ "AND b.VLD_DAT = (SELECT MAX(VLD_DAT) FROM DF_CMN_NWT_XX_SUB c WHERE c.CMP_VAL = a.CMP_VAL "
		+ "AND c.SUB_VAL = a.SUB_VAL AND c.LNG_VAL = b.LNG_VAL) ";
	
	
	try {
	    return jdbcTemplate.queryForObject(query, new Object[] { cmpVal, sugVal, subVal, usrLngVal, },
		    new OLsfLsfSubRowMapper());

	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "LSF", "SUB_VAL", cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }    

    // Hallar moneda
    @Override
    public OLsfLsfS getMoneda(BigDecimal cmpVal, BigDecimal crnVal, String usrLngVal, String msgVal) {
	final String query = "SELECT a.COD_MON FROM A1000400 a " + "WHERE a.COD_CIA=? AND COD_MON=? "
		+ "AND a.COD_MON = a.COD_MON AND a.COD_CIA = ?";

	try {
	    return jdbcTemplate.queryForObject(query, new Object[] { cmpVal, crnVal, cmpVal },
		    new OLsfLsfMonedaRowMapper());

	} catch (EmptyResultDataAccessException e) {
	    BigDecimal codError = new BigDecimal(20001);
	    OTrnErrS error = lvDlTrnErr.getError(codError, usrLngVal, "LSF", msgVal, cmpVal);
	    NwtException exception = new NwtException(error.getErrIdnVal());
	    exception.add(error);
	    throw exception;
	}
    }
}
