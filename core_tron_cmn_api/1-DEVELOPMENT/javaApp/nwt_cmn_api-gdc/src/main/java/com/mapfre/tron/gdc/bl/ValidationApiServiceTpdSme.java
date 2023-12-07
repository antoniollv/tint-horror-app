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


@Service("TpdSme")
public class ValidationApiServiceTpdSme implements ValidationApiService {

    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;

    @Autowired
	DlTrnErr lvDlTrnErr;
	
	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		ValidationError error;
		String accion;
		OCmnLngS lvOCmnLngS;
		OTrnErrS lvError;
		BigDecimal codError;

	        BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
	        
		
        // Comprobamos si es crear o modificar
		if (body.getDataOld() == null) {
			accion = CTrn.ACN_TYP_CRT;
		}
		else {
			accion = CTrn.ACN_TYP_MDF;
		}
		
		// Validacion IDIOMA
		try {
		    lvOCmnLngS = iDlCmnLngDAO.get(cmpVal, body.getData().get("LNG_VAL"),  body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("LNG_VAL")).build();
		    errors.add(error);
		}
		
		// Si la accion es modificar
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		        if(!body.getDataOld().get("CMP_VAL").equals(body.getData().get("CMP_VAL"))) {
		            	codError = new BigDecimal(20120);
		            	lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PSM", "CMP_VAL", cmpVal);
		            	error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("CMP_VAL").toString()).build();
		            	errors.add(error);
		        }
		        if(!body.getDataOld().get("PSM_VAL").equals(body.getData().get("PSM_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PSM", "PSM_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("PSM_VAL").toString()).build();
				errors.add(error);
			}
			if(!body.getDataOld().get("LNG_VAL").equals(body.getData().get("LNG_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PSM", "LNG_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("LNG_VAL").toString()).build();
				errors.add(error);
			}
			if(!body.getDataOld().get("VLD_DAT").equals(body.getData().get("VLD_DAT"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PSM", "VLD_DAT", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("VLD_DAT").toString()).build();
				errors.add(error);
			}
		}
		
		
		// Si tipo de métrica (PSM_TYP_VAL) es la opción C(Check)  y objetivo de la metrica con valor 0 o 1
		if (body.getData().get("TGT_PSM_VAL") != null){  
		    if( body.getData().get("PSM_TYP_VAL").equals("C") && !(body.getData().get("TGT_PSM_VAL").equals("0") || body.getData().get("TGT_PSM_VAL").equals("1"))) {
			codError = new BigDecimal(20005);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PSM", "TGT_PSM_VAL",cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("TGT_PSM_VAL")).build();
			errors.add(error);	
		    }
		}
		
		// Si tipo de métrica (PSM_TYP_VAL) es la opción P (Porcentaje) y objetivo de la metrica con valores entre 0 y 100
		if (body.getData().get("TGT_PSM_VAL") != null){  
		    if(body.getData().get("PSM_TYP_VAL").equals("P") && (Integer.parseInt(body.getData().get("TGT_PSM_VAL"))) < 0 || (Integer.parseInt(body.getData().get("TGT_PSM_VAL"))) > 100) {
			codError = new BigDecimal(20005);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PSM", "TGT_PSM_VAL",cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("TGT_PSM_VAL")).build();
			errors.add(error);	
		    }
		}
		
		// Si porcentaje objetivo de la  métrica (PER_PSM_VAL) es la opción P (Porcentaje) con valores entre 0 y 100		
		if (body.getData().get("PER_PSM_VAL") != null){  
		    if((Integer.parseInt(body.getData().get("PER_PSM_VAL"))) < 0 || (Integer.parseInt(body.getData().get("PER_PSM_VAL"))) > 100) {
			codError = new BigDecimal(20005);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PSM", "PER_PSM_VAL",cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("PER_PSM_VAL")).build();
			errors.add(error);	
		    }
		}
		
		
		// Validacion MARCA INHABILITADO
		if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PSM", "DSB_ROW", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
			errors.add(error);
		}
		
		return errors;
	}
}
