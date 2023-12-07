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

@Service("PstTpd")
public class ValidationApiServicePstTpd implements ValidationApiService {
    
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
	OCmnLngS   lvOCmnLngS;
	
	BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
	
	if (body.getDataOld() == null) {
	    accion = CTrn.ACN_TYP_CRT;
	}
	else {
	    accion = CTrn.ACN_TYP_MDF;
	}

	if (accion.equals(CTrn.ACN_TYP_MDF)) {
	    
	    // No se puede modificar la compañia
	    if(!body.getDataOld().get("CMP_VAL").equals(body.getData().get("CMP_VAL"))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST", "CMP_VAL", cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("CMP_VAL")).build();
		errors.add(error);
	    }
	    
	    // No se puede modificar cargo de la empresa
	    if (!body.getDataOld().get("EMY_PST_VAL").equals(body.getData().get("EMY_PST_VAL"))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST","EMY_PST_VAL", cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("EMY_PST_VAL")).build();
		errors.add(error);
	    }
	    
	    // No se puede modificar fecha de actualización
	    if (!body.getDataOld().get("VLD_DAT").equals(body.getData().get("VLD_DAT"))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST", "VLD_DAT",cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("VLD_DAT")).build();
		errors.add(error);
	    }
	    
	    // No se puede modificar lenguaje
	    if (!body.getDataOld().get("LNG_VAL").equals(body.getData().get("LNG_VAL"))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST", "LNG_VAL",cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("LNG_VAL")).build();
		errors.add(error);
	    }
	    
	}
		    
	    // Validamos el cargo de la empresa está informado
	    if (body.getData().get("EMY_PST_VAL") == null || body.getData().get("EMY_PST_VAL").equals("")) {
		codError = new BigDecimal(20003);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST","EMY_PST_VAL", cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("EMY_PST_VAL")).build();
		errors.add(error);
	    }
	    
	    // Validamos la descripción del cargo en la empresa está informado
	    if (body.getData().get("EMY_PST_NAM") == null || body.getData().get("EMY_PST_NAM").equals("")) {
		codError = new BigDecimal(20003);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST","EMY_PST_NAM", cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("EMY_PST_NAM")).build();
		errors.add(error);
	    }
	    
	    // Validamos si el campo Fecha validez está informado
	    if (body.getData().get("VLD_DAT") == null || body.getData().get("VLD_DAT").equals("")) {
	 	codError = new BigDecimal(20003);
	 	lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST", "VLD_DAT", cmpVal);
	 	error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("VLD_DAT")).build();
	 	errors.add(error);
	    }
	
	    // Validacion IDIOMA
	    try {
		  // Validamos si el campo zona cuatro greográfica real está informado 
	 	    if (body.getData().get("LNG_VAL") == null || body.getData().get("LNG_VAL").equals("")) {
	 		codError = new BigDecimal(20003);
	 		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", "LNG_VAL", cmpVal);
	 		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("LNG_VAL")).build();
	 		errors.add(error);
	           }
	 	   else {// Si su contenido no es nulo ni vacio, lo creamos
	 	       	lvOCmnLngS = iDlCmnLngDAO.get(cmpVal, body.getData().get("LNG_VAL"), body.getUser().getLanguage().toUpperCase());
	 	   }
	 	}
	 	catch (NwtException e) {
	 	    	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("LNG_VAL")).build();
	 		errors.add(error);
	 	}
	    
	// Validación Marca Inhabilitado
	if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PST", "DSB_ROW", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW")).build();
	    errors.add(error);
	}
	
	return errors;
    }

}
