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
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Dpr")
public class ValidationApiServiceDpr implements ValidationApiService {
    
    protected static final String CMP_VAL = "CMP_VAL";

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String EMY_DPR_NAM = "EMY_DPR_NAM";

    protected static final String EMY_DPR_VAL = "EMY_DPR_VAL";

    protected static final String LNG_VAL = "LNG_VAL";

    protected static final String VLD_DAT = "VLD_DAT";

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {

	String accion;
	ValidationError error;
	List<ValidationError> errors = new ArrayList<>();
	BigDecimal codError;
	OTrnErrS lvError;

	
	BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));
	
	if (body.getDataOld() == null) {
	    accion = CTrn.ACN_TYP_CRT;
	}
	else {
	    accion = CTrn.ACN_TYP_MDF;
	}

	if (accion.equals(CTrn.ACN_TYP_MDF)) {
	    
	    // No se puede modificar la compañia
	    if(!body.getDataOld().get(CMP_VAL).equals(body.getData().get(CMP_VAL))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DPR", CMP_VAL, cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(CMP_VAL)).build();
		errors.add(error);
	    }
	    
	    // No se puede modificar departamento de la empresa
	    if (!body.getDataOld().get(EMY_DPR_VAL).equals(body.getData().get(EMY_DPR_VAL))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DPR",EMY_DPR_VAL, cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(EMY_DPR_VAL)).build();
		errors.add(error);
	    }
	    
	    // No se puede modificar fecha de actualización
	    if (!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DPR", VLD_DAT,cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT)).build();
		errors.add(error);
	    }
	    
	    // No se puede modificar lenguaje
	    if (!body.getDataOld().get(LNG_VAL).equals(body.getData().get(LNG_VAL))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DPR", LNG_VAL,cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(LNG_VAL)).build();
		errors.add(error);
	    }
	    
	}
	    
	    // Validamos el departamento de la empresa 
	    if (body.getData().get(EMY_DPR_VAL) == null || body.getData().get(EMY_DPR_VAL).equals("")) {
		codError = new BigDecimal(20003);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DPR",EMY_DPR_VAL, cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(EMY_DPR_VAL)).build();
		errors.add(error);
	    }
	    
	    // Validamos la descripción del departamento en la empresa
	    if (body.getData().get(EMY_DPR_NAM) == null || body.getData().get(EMY_DPR_NAM).equals("")) {
		codError = new BigDecimal(20003);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DPR",EMY_DPR_NAM, cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(EMY_DPR_NAM)).build();
		errors.add(error);
	    }
	    
	    // Validamos si el campo Fecha validez está informado
	    if (body.getData().get(VLD_DAT) == null || body.getData().get(VLD_DAT).equals("")) {
	 	codError = new BigDecimal(20003);
	 	lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DPR", VLD_DAT, cmpVal);
	 	error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(VLD_DAT)).build();
	 	errors.add(error);
	    }
	
	 // Validacion IDIOMA
	    try {
		  // Validamos si el campo zona cuatro greográfica real está informado 
	 	    if (body.getData().get(LNG_VAL) == null || body.getData().get(LNG_VAL).equals("")) {
	 		codError = new BigDecimal(20003);
	 		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ZNF", LNG_VAL, cmpVal);
	 		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(LNG_VAL)).build();
	 		errors.add(error);
	 	    }
	 	   else {// Si su contenido no es nulo ni vacio, lo creamos
	 		   iDlCmnLngDAO.get(cmpVal, body.getData().get(LNG_VAL), body.getUser().getLanguage().toUpperCase());
	 	   	}
	 	}
	 	catch (NwtException e) {
	 	    	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LNG_VAL)).build();
	 	    	errors.add(error);
	 	}
	
	// Validación Marca Inhabilitado
	if (body.getData().get(DSB_ROW) == null || (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "DPR", DSB_ROW, cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW)).build();
	    errors.add(error);
	}
	
	return errors;
    }

}
