package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.tpd.avd.dl.IDlTpdAvdDAO;
import com.mapfre.tron.api.tpd.sib.dl.IDlTpdSibDAO;
import com.mapfre.tron.api.tpd.sib.uar.dl.IDlTpdSibUarDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;


@Service("TpdSibUar")
public class ValidationApiServiceTpdSibUar implements ValidationApiService {

    @Autowired
    DlTrnErr lvDlTrnErr;
    @Autowired
    IDlTpdAvdDAO iDlTpdAvdDAO;
    @Autowired
    IDlTpdSibDAO iDlTpdSibDAO;
    @Autowired
    IDlTpdSibUarDAO iDlTpdSibUarDAO;

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
	} else {
	    accion = CTrn.ACN_TYP_MDF;
	}

	// Si la acion es modificar
	if (accion.equals(CTrn.ACN_TYP_MDF)) {
	    if (!body.getDataOld().get("CMP_VAL").equals(body.getData().get("CMP_VAL"))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "CMP_VAL",
			cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(body.getDataOld().get("CMP_VAL").toString()).build();
		errors.add(error);
	    }

	    if (!body.getDataOld().get("THP_ACV_VAL").equals(body.getData().get("THP_ACV_VAL"))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
			"THP_ACV_VAL", cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(body.getDataOld().get("THP_ACV_VAL").toString()).build();
		errors.add(error);
	    }

	    if (!body.getDataOld().get("CMP_USR_VAL").equals(body.getData().get("CMP_USR_VAL"))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB", "CMP_USR_VAL",
			cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(body.getDataOld().get("CMP_USR_VAL").toString()).build();
		errors.add(error);
	    }
	}

	// Comprobamos si existe la actividad
	try {
	    BigDecimal actividad = new BigDecimal(body.getData().get("THP_ACV_VAL"));
	    iDlTpdAvdDAO.get(cmpVal, actividad, body.getUser().getLanguage().toUpperCase());
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("THP_ACV_VAL").toString())
		    .build();
	    errors.add(error);
	}
	
	// Comprobamos si existe el rol
	try {
	    String rol = body.getData().get("ROL_VAL");
	    iDlTpdSibDAO.get_Rol(cmpVal, rol, body.getUser().getLanguage().toUpperCase());
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("ROL_VAL").toString())
		    .build();
	    errors.add(error);
	}
	
	// Comprobamos si existe el usuario compania
	try {
	    String cmpUsrVal = body.getData().get("CMP_USR_VAL");
	    iDlTpdSibUarDAO.get_Usr(cmpVal, cmpUsrVal, body.getUser().getLanguage().toUpperCase());
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("CMP_USR_VAL").toString())
		    .build();
	    errors.add(error);
	}

	// Si algun campo obligatorio es null
	if (body.getData().get("THP_ACV_VAL") == null) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "THP_ACV_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("THP_ACV_VAL").toString()).build();
	    errors.add(error);
	}
	
	if (body.getData().get("CMP_USR_VAL") == null) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "SIB",
		    "CMP_USR_VAL", cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get("CMP_USR_VAL").toString()).build();
	    errors.add(error);
	}

	return errors;
    }

}
