package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
 
@Service("Pst")
public class ValidationApiServicePst implements ValidationApiService {

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
	
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<ValidationError>();
		BigDecimal codError;
		OTrnErrS lvError;
		OCmnLngS lvOCmnLngS;
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
	
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		// Campos que no se pueden modificar (PK)
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    
		    // No se puede modificar la compañia
		    if(!body.getDataOld().get("CMP_VAL").equals(body.getData().get("CMP_VAL"))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST", "CMP_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("CMP_VAL").toString()).build();
			errors.add(error);
		    }
		    
		    // No se puede modificar el código de cargo
		    if (!body.getDataOld().get("PCE_PST_RLN_VAL").equals(body.getData().get("PCE_PST_RLN_VAL"))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST","PCE_PST_RLN_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("PCE_PST_RLN_VAL").toString()).build();
			errors.add(error);
		    }
		    
		    // No se puede modificar la fecha de validez
		    if (!body.getDataOld().get("VLD_DAT").equals(body.getData().get("VLD_DAT"))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST", "VLD_DAT",cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("VLD_DAT").toString()).build();
			errors.add(error);
		    }
		    
		    // No se puede modificar el idioma
		    if (!body.getDataOld().get("LNG_VAL").equals(body.getData().get("LNG_VAL"))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST", "LNG_VAL",cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("LNG_VAL").toString()).build();
			errors.add(error);
		    	}
		    }
		
			
		   // Validamos si el código de cargo es null
		   if (body.getData().get("PCE_PST_RLN_VAL") == null || body.getData().get("PCE_PST_RLN_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST", "PCE_PST_RLN_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("PCE_PST_RLN_VAL").toString()).build();
			errors.add(error);
		   }
		
		   // Validamos si la Descripción del cargo es null
		   if (body.getData().get("PCE_PST_RLN_NAM") == null || body.getData().get("PCE_PST_RLN_NAM").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST", "PCE_PST_RLN_NAM", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("PCE_PST_RLN_NAM").toString()).build();
			errors.add(error);
		   }
		
		
		//validamos el idioma
		try {
		    lvOCmnLngS = iDlCmnLngDAO.get(cmpVal, body.getData().get("LNG_VAL"),body.getUser().getLanguage().toUpperCase());
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("LNG_VAL").toString()).build();
		    errors.add(error);
		}

		// Validación Marca Inhabilitado
		if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES)&& !body.getData().get("DSB_ROW").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST", "DSB_ROW",cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
		    errors.add(error);
		}
	
		return errors;
    }

}
