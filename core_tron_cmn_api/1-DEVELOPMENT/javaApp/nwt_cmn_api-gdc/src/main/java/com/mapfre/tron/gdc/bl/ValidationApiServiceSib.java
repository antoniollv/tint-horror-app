package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.tpd.avd.dl.IDlTpdAvdDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Sib")
public class ValidationApiServiceSib implements ValidationApiService {

    @Autowired
    DlTrnErr lvDlTrnErr;
    @Autowired
    IDlTpdAvdDAO iDlTpdAvdDAO;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<ValidationError>();
		BigDecimal codError;
		OTrnErrS lvError;

	    BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
	
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
			if(!body.getDataOld().get("THP_ACV_VAL").equals(body.getData().get("THP_ACV_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "THP_ACV_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("THP_ACV_VAL").toString()).build();
				errors.add(error);
			}
		}
	
		// Comprobamos si existe la actividad
		try {
		    BigDecimal actividad = new BigDecimal(body.getData().get("THP_ACV_VAL"));
		    iDlTpdAvdDAO.get(cmpVal, actividad, body.getUser().getLanguage().toUpperCase());
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("THP_ACV_VAL").toString()).build();
		    errors.add(error);
		}
	
		if (body.getData().get("SPL_RTE") == null || (!body.getData().get("SPL_RTE").equals(CIns.YES) && !body.getData().get("SPL_RTE").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "SPL_RTE", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SPL_RTE").toString()).build();
		    errors.add(error);
		}
	
		if (body.getData().get("AZO") == null || (!body.getData().get("AZO").equals(CIns.YES) && !body.getData().get("AZO").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "AZO", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("AZO").toString()).build();
		    errors.add(error);
		}
	
		if (body.getData().get("SSV_NRM") == null || (!body.getData().get("SSV_NRM").equals(CIns.YES) && !body.getData().get("SSV_NRM").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "SSV_NRM", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SSV_NRM").toString()).build();
		    errors.add(error);
		}
	
		if (body.getData().get("SSV_ADD_VAL") == null || (!body.getData().get("SSV_ADD_VAL").equals(CIns.YES) && !body.getData().get("SSV_ADD_VAL").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "SSV_ADD_VAL", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("SSV_ADD_VAL").toString()).build();
		    errors.add(error);
		}
	
		if (body.getData().get("ATE_DAA") == null || (!body.getData().get("ATE_DAA").equals(CIns.YES) && !body.getData().get("ATE_DAA").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "ATE_DAA", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("ATE_DAA").toString()).build();
		    errors.add(error);
		}
	
		if (body.getData().get("CTY") == null || (!body.getData().get("CTY").equals(CIns.YES) && !body.getData().get("CTY").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "CTY", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("CTY").toString()).build();
		    errors.add(error);
		}
	
		if (body.getData().get("ICC") == null || (!body.getData().get("ICC").equals(CIns.YES) && !body.getData().get("ICC").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "ICC", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("ICC").toString()).build();
		    errors.add(error);
		}
	
		if (body.getData().get("FRD") == null || (!body.getData().get("FRD").equals(CIns.YES) && !body.getData().get("FRD").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "FRD", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("FRD").toString()).build();
		    errors.add(error);
		}
	
		if (body.getData().get("VLT") == null || (!body.getData().get("VLT").equals(CIns.YES) && !body.getData().get("VLT").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "VLT", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("VLT").toString()).build();
		    errors.add(error);
		}
	
		if (body.getData().get("TNG") == null || (!body.getData().get("TNG").equals(CIns.YES) && !body.getData().get("TNG").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "TNG", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("TNG").toString()).build();
		    errors.add(error);
		}
	
	
		return errors;
    }

}
