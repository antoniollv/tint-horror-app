package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.nwt.trn.pid.utd.bo.OPidUtdS;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.pid.utd.dl.IDlPidUtdDAO;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;


@Service("UtdLng")
public class ValidationApiServiceUtdLng implements ValidationApiService{
    
    @Autowired
    IDlPidUtdDAO iDlPidUtdDAO;
    
    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	 
	List<ValidationError> errors = new ArrayList<ValidationError>();
	ValidationError error;
	String accion;
	OCmnLngS lvOCmnLngS;
	OPidUtdS lvOPidUtdS;
	OTrnErrS lvError;
	BigDecimal codError;
	BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
	
	// Comprobar si es Crear o Modificar
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
	
	// Validacion CLA_VAL (CLAUSULA)
	 // si es modificar
	if (accion.equals(CTrn.ACN_TYP_MDF)) {
		//
		String indClausula = new String(body.getDataOld().get("CLA_VAL"));
		String usrLngVal =  body.getUser().getLanguage().toUpperCase();
		Date vldDat = Date.from(LocalDateTime.parse(body.getDataOld().get("VLD_DAT")).atZone(ZoneId.systemDefault()).toInstant());
		DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		String fechaValidez = formatoFecha.format(vldDat);
		//
		try {
		    lvOPidUtdS = iDlPidUtdDAO.get_A9990010(cmpVal, indClausula, fechaValidez, usrLngVal);
		    // No se puede modificar clausula
		    if(!body.getDataOld().get("CLA_VAL").equals(body.getData().get("CLA_VAL"))) {
				codError = new BigDecimal(20120);
			 	lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "UTD", "CLA_VAL", cmpVal);
			 	error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("CLA_VAL").toString()).build();
			 	errors.add(error);
		    }
		    // No se puede modificar el idioma
		    if(!body.getDataOld().get("LNG_VAL").equals(body.getData().get("LNG_VAL"))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "UTD", "LNG_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("LNG_VAL").toString()).build();
			errors.add(error);
		    }
		    // No se puede modificar la fecha validez
		    if(!body.getDataOld().get("VLD_DAT").equals(body.getData().get("VLD_DAT"))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "UTD", "VLD_DAT", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("VLD_DAT").toString()).build();
			errors.add(error);
		    }
		}
		catch (NwtException e) {
		    //Si da error por inexistencia se permite modificar el registro
		}
	    
	    }
	    
	
	// Validacion MARCA TEXTO VARIABLE
	if (body.getData().get("VRB_TXT") == null || (!body.getData().get("VRB_TXT").equals(CIns.YES) && !body.getData().get("VRB_TXT").equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "UTD", "VRB_TXT",cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("VRB_TXT").toString()).build();
	    errors.add(error);
	}
	
	// Validacion MARCA INHABILITADO
	if (body.getData().get("DSB_ROW") == null || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "UTD", "DSB_ROW",cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW").toString()).build();
	    errors.add(error);
	}
	
	return errors;
    }
    
}
