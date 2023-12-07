package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.grz.bo.OCmnGrzS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.grz.dl.IDlCmnGrzDAO;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.cmn.typ.dl.IDlCmnTypDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Grz")
public class ValidationApiServiceGrz implements ValidationApiService {

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String GRZ_TYP_VAL = "GRZ_TYP_VAL";

    protected static final String GRZ_VAL = "GRZ_VAL";

    protected static final String LNG_VAL = "LNG_VAL";

    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;	

    @Autowired
    IDlCmnTypDAO iDlCmnTypDAO;	

    @Autowired
    IDlCmnGrzDAO iDlCmnGrzDAO;

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<>();
		ValidationError  error;
		String           accion;
		List<OCmnGrzS>   oCmnGrzPT;
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
	
		    // No se puede modificar GRZ_VAL
		    if(!body.getDataOld().get(GRZ_VAL).equals(body.getData().get(GRZ_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GRZ", GRZ_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(GRZ_VAL)).build();
				errors.add(error);
		    }
	
		    // No se puede modificar GRZ_TYP_VAL
		    if(!body.getDataOld().get(GRZ_TYP_VAL).equals(body.getData().get(GRZ_TYP_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GRZ", GRZ_TYP_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(GRZ_TYP_VAL)).build();
				errors.add(error);
		    }
	
		    // No se puede modificar LNG_VAL
		    if(!body.getDataOld().get(LNG_VAL).equals(body.getData().get(LNG_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GRZ", LNG_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(LNG_VAL)).build();
				errors.add(error);
		    }
		}
		
		// Si la accion es crear
		if (accion.equals(CTrn.ACN_TYP_CRT)) {
	
		    // Consultar si ya existe algun grzVal en algun idioma
		    try {
				oCmnGrzPT = iDlCmnGrzDAO.get_grzPT(cmpVal, body.getData().get(GRZ_VAL), body.getUser().getLanguage().toUpperCase());
		
				// Si existe comprobar que grzTypVal que recibimos es el mismo que el de la tabla
				if(!oCmnGrzPT.isEmpty() && !oCmnGrzPT.get(0).getGrzTypVal().equals(body.getData().get(GRZ_TYP_VAL))) {
				    codError = new BigDecimal(20120);
				    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GRZ", GRZ_TYP_VAL, cmpVal);
				    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(GRZ_TYP_VAL)).build();
				    errors.add(error);
				}
		    }
		    catch (NwtException e) {
		    	// Si no existe, podemos poner el grzTypVal que queramos
		    }
		}
	
		// Comprobamos que el idioma existe
		try {
		    iDlCmnLngDAO.get(cmpVal, body.getData().get(LNG_VAL), body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LNG_VAL)).build();
		    errors.add(error);
		}
	
		// Comprobamos que el TIP_ZONA_GEOGRAFICA existe
		try {
		    iDlCmnTypDAO.get(cmpVal, "TIP_ZONA_GEOGRAFICA", body.getData().get(GRZ_TYP_VAL), body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(GRZ_TYP_VAL)).build();
			errors.add(error);
		}	
	
		// Validacion MARCA INHABILITADO
		if (body.getData().get(DSB_ROW) == null || (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GRZ", DSB_ROW, cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW)).build();
		    errors.add(error);
		}
		
		return errors;
    }
}
