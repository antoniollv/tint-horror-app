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
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.cmn.typ.dl.IDlCmnTypDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Rdf")
public class ValidationApiServiceRdf implements ValidationApiService {

    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;	

    @Autowired
    IDlCmnTypDAO iDlCmnTypDAO;	

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		ValidationError  error;
		String           accion;
		OCmnLngS         lvOCmnLngS;
		List<OCmnTypS>   oCmnTypPT;
		OTrnErrS         lvError;
		BigDecimal       codError;
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
	
		// Comprobar si es Crear o Modificar
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		
		// Si la accion es modificar
		if (accion.equals(CTrn.ACN_TYP_MDF)) {

		    // No se puede modificar la compañia
		    if(!body.getDataOld().get("CMP_VAL").equals(body.getData().get("CMP_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "RDF", "CMP_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("CMP_VAL").toString()).build();
				errors.add(error);
		    }
    
		    // No se puede modificar Fecha validez
		    if(!body.getDataOld().get("VLD_DAT").equals(body.getData().get("VLD_DAT"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "RDF", "VLD_DAT", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("VLD_DAT").toString()).build();
				errors.add(error);
		    }

		    // No se puede modificar el Motivo del Fraude
		    if(!body.getDataOld().get("FRD_RSN_VAL").equals(body.getData().get("FRD_RSN_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "RDF", "FRD_RSN_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("FRD_RSN_VAL").toString()).build();
				errors.add(error);
		    }

		    // No se puede modificar Código Idioma
		    if(!body.getDataOld().get("LNG_VAL").equals(body.getData().get("LNG_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "RDF", "LNG_VAL", cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("LNG_VAL").toString()).build();
				errors.add(error);
		    }
		}
		
		// Validamos  si la descripcion del Motivo del Fraude está informado
		if (body.getData().get("FRD_RSN_NAM") == null || body.getData().get("FRD_RSN_NAM").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "RDF", "FRD_RSN_NAM", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("FRD_RSN_NAM").toString()).build();
			errors.add(error);
		}
	
		// Comprobamos que DSB_ROW valga S o N
		if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "RDF", "DSB_ROW", cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
		    errors.add(error);
		}
		
		// Comprobamos si el idioma existe
		try {
		    lvOCmnLngS = iDlCmnLngDAO.get(cmpVal, body.getData().get("LNG_VAL"), body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("LNG_VAL")).build();
		    errors.add(error);
		}	
	
		return errors;
    }
}
