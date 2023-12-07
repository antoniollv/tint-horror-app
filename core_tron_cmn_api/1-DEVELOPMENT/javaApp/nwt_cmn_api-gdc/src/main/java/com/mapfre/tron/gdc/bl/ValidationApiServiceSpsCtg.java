package com.mapfre.tron.gdc.bl;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;


@Service("SpsCtg")
public class ValidationApiServiceSpsCtg implements ValidationApiService {

    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;

    @Autowired
	DlTrnErr lvDlTrnErr;
	
	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		ValidationError error;
		String accion;
        OCmnLngS   lvOCmnLngS;
		OTrnErrS lvError;
		BigDecimal codError;

	    BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
		
		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		}
		else {
			accion = CTrn.ACN_TYP_MDF;
		}
		
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
			if(!body.getDataOld().get("SPL_CTG_VAL").equals(body.getData().get("SPL_CTG_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "SPL_CTG_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("SPL_CTG_VAL").toString()).build();
				errors.add(error);
			}
			if(!body.getDataOld().get("LNG_VAL").equals(body.getData().get("LNG_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "LNG_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("LNG_VAL").toString()).build();
				errors.add(error);
			}
		}
		
		// Validacion IDIOMA
		try {
			lvOCmnLngS = iDlCmnLngDAO.get(cmpVal, body.getData().get("LNG_VAL"),  body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("LNG_VAL")).build();
		    errors.add(error);
		}
		
		// Validacion MARCA INHABILITADO
		if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "DSB_ROW", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
			errors.add(error);
		}
		// Validacion CATEGORÍA DE PROVEEDOR REAL
		if (body.getData().get("REA_SPL_CTG_VAL") == null || (!body.getData().get("REA_SPL_CTG_VAL").equals(CIns.YES) && !body.getData().get("REA_SPL_CTG_VAL").equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SPS", "REA_SPL_CTG_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("REA_SPL_CTG_VAL").toString()).build();
			errors.add(error);
		}
		
		return errors;
	}
}
