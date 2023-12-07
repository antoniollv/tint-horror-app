package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.grz.bo.OCmnGrzS;
import com.mapfre.nwt.trn.tpd.sef.bo.OTpdSefS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.grz.dl.IDlCmnGrzDAO;
import com.mapfre.tron.api.crn.crn.dl.IDlCrnCrnDAO;
import com.mapfre.tron.api.tpd.sef.dl.IDlTpdSefDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("SefAcz")
public class ValidationApiServiceSefAcz implements ValidationApiService {

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    IDlTpdSefDAO iDlTpdSefDAO;

    @Autowired
    IDlCmnGrzDAO iDlCmnGrzDAO;

    @Autowired
    IDlCrnCrnDAO iDlCrnCrnDAO;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<ValidationError>();
		BigDecimal codError;
		OTrnErrS lvError;
		OTpdSefS oTpdSefS;
		OCmnGrzS oCmnGrzS;

		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
		
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
	
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    if(!body.getDataOld().get("RTE_VAL").equals(body.getData().get("RTE_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "RTE_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("RTE_VAL").toString()).build();
				errors.add(error);
		    }
		    if(!body.getDataOld().get("RTE_CNC_VAL").equals(body.getData().get("RTE_CNC_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "RTE_CNC_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("RTE_CNC_VAL").toString()).build();
				errors.add(error);
		    }
		    if(!body.getDataOld().get("GRZ_VAL").equals(body.getData().get("GRZ_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "GRZ_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("GRZ_VAL").toString()).build();
				errors.add(error);
		    }
	
		}
	
		try {
		    oTpdSefS = iDlTpdSefDAO.get(cmpVal, body.getData().get("RTE_VAL"), body.getUser().getLanguage().toUpperCase());
		    if (oTpdSefS.getDsbRow().equals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
				    codError = new BigDecimal(20020);
				    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "RTE_VAL", cmpVal);
				    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("RTE_VAL").toString()).build();
				    errors.add(error);
				}
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get("DSB_ROW").equals(CIns.NO)) {
				    codError = new BigDecimal(20020);
				    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "RTE_VAL", cmpVal);
				    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("RTE_VAL").toString()).build();
				    errors.add(error);
				}
		    }
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("RTE_VAL").toString()).build();
		    errors.add(error);
		}
	
		try {
		    oTpdSefS = iDlTpdSefDAO.get_cnc(cmpVal, body.getData().get("RTE_CNC_VAL"), body.getUser().getLanguage().toUpperCase());
		    if (oTpdSefS.getDsbRow().equals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
				    codError = new BigDecimal(20020);
				    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "RTE_CNC_VAL", cmpVal);
				    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("RTE_CNC_VAL").toString()).build();
				    errors.add(error);
				}
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get("DSB_ROW").equals(CIns.NO)) {
				    codError = new BigDecimal(20020);
				    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "RTE_CNC_VAL", cmpVal);
				    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("RTE_CNC_VAL").toString()).build();
				    errors.add(error);
				}
		    }
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("RTE_CNC_VAL").toString()).build();
		    errors.add(error);
		}
	
		/*
		 * Comprobar zona geografica
		 */
		try {
		    oCmnGrzS = iDlCmnGrzDAO.get(cmpVal, body.getData().get("GRZ_VAL"), "1", body.getUser().getLanguage().toUpperCase());
		    if (oCmnGrzS.getDsbRow().equals(CIns.YES)) {
				if (accion.equals(CTrn.ACN_TYP_CRT)) {
				    codError = new BigDecimal(20020);
				    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "GRZ_VAL", cmpVal);
				    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("GRZ_VAL").toString()).build();
				    errors.add(error);
				}
				if (accion.equals(CTrn.ACN_TYP_MDF) && body.getData().get("DSB_ROW").equals(CIns.NO)) {
				    codError = new BigDecimal(20020);
				    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "GRZ_VAL", cmpVal);
				    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("GRZ_VAL").toString()).build();
				    errors.add(error);
				}
		    }
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("GRZ_VAL").toString()).build();
		    errors.add(error);
		}
		
		// Existe moneda
		try {
		    iDlCrnCrnDAO.get(cmpVal, body.getData().get("CRN_VAL"), body.getUser().getLanguage().toUpperCase());
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("CRN_VAL").toString()).build();
		    errors.add(error);
		}
	
		// Validacion MARCA INHABILITADO
		if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SEF", "DSB_ROW", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
		    errors.add(error);
		}
	
		return errors;
    }

}
