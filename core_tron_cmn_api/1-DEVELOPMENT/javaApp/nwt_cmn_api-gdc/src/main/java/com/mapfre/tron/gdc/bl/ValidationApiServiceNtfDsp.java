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
import com.mapfre.nwt.trn.cmn.mkt.bo.OCmnMktS;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.cmn.typ.dl.IDlCmnTypDAO;
import com.mapfre.tron.api.tpd.sps.dl.IDlTpdSpsDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
 
@Service("NtfDsp")
public class ValidationApiServiceNtfDsp implements ValidationApiService {

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
		    
		    // No se puede modificar la compañía
		    if(!body.getDataOld().get("CMP_VAL").equals(body.getData().get("CMP_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "CMP_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("CMP_VAL")).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar el código de documento
		    if(!body.getDataOld().get("DCN_VAL").equals(body.getData().get("DCN_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "DCN_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("DCN_VAL")).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar el idioma
		    if(!body.getDataOld().get("LNG_VAL").equals(body.getData().get("LNG_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "LNG_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("LNG_VAL")).build();
				errors.add(error);
		    }		

		}
		
		// Validamos si el el código de documento está informado
		if (body.getData().get("DCN_VAL") == null || body.getData().get("DCN_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "DCN_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DCN_VAL")).build();
			errors.add(error);
		}
		
		// Validamos si el tipo de idioma está informado
		if (body.getData().get("LNG_VAL") == null || body.getData().get("LNG_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "LNG_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("LNG_VAL")).build();
			errors.add(error);
		}
		
		// Validamos si nombre del documento está informado
		if (body.getData().get("DCN_NAM") == null || body.getData().get("DCN_NAM").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "DCN_NAM", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DCN_NAM")).build();
			errors.add(error);
		}
				
		// Validacion IDIOMA
		try {
		    lvOCmnLngS = iDlCmnLngDAO.get(cmpVal, body.getData().get("LNG_VAL"),  body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("LNG_VAL")).build();
		    errors.add(error);
		}
		
		
				
		return errors;
    }

}
