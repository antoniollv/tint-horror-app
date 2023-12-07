package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
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
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.cmn.mkt.dl.IDlCmnMktDao;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
 
@Service("Mkd")
public class ValidationApiServiceMkd implements ValidationApiService {

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String LNG_VAL = "LNG_VAL";

    protected static final String MKD_NAM = "MKD_NAM";

    protected static final String MKD_VAL = "MKD_VAL";

    protected static final String MKT_VAL = "MKT_VAL";

    protected static final String VLD_DAT = "VLD_DAT";

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;

    @Autowired
    IDlCmnMktDao iDlCmnMktDAO;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
	
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<>();
		BigDecimal codError;
		OTrnErrS lvError;

		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));

		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		// Campos que no se pueden modificar (PK)
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    // No se puede modificar el código de la marca
		    if(!body.getDataOld().get(MKD_VAL).equals(body.getData().get(MKD_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", MKD_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(MKD_VAL)).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar el código de tipo de marca
		    if(!body.getDataOld().get(MKT_VAL).equals(body.getData().get(MKT_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", MKT_VAL,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(MKT_VAL)).build();
				errors.add(error);
		    }
		    
		 // No se puede modificar el idioma
		    if(!body.getDataOld().get(LNG_VAL).equals(body.getData().get(LNG_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", LNG_VAL,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(LNG_VAL)).build();
				errors.add(error);
		    }
		    
		 // No se puede modificar fecha de validez
		    if(!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", VLD_DAT,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT)).build();
				errors.add(error);
		    }
		}
		
		// Validamos si el código de marca está informado
		if (body.getData().get(MKD_VAL) == null || body.getData().get(MKD_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", MKD_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(MKD_VAL)).build();
			errors.add(error);
		}
		
		// Validamos si el campo Nombre de la marca está informado
		if (body.getData().get(MKD_NAM) == null || body.getData().get(MKD_NAM).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", MKD_NAM, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(MKD_NAM)).build();
			errors.add(error);
		}
		
		// Validamos si código de tipo de marca está informado
		if (body.getData().get(MKT_VAL) == null || body.getData().get(MKT_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", MKT_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(MKT_VAL)).build();
			errors.add(error);
		}
		
		// Validamos si fecha de validez está informado
		if (body.getData().get(VLD_DAT) == null || body.getData().get(VLD_DAT).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", VLD_DAT, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(VLD_DAT)).build();
			errors.add(error);
		}
		
		// Validamos si el idioma está informado
		if (body.getData().get(LNG_VAL) == null || body.getData().get(LNG_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", LNG_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(LNG_VAL)).build();
			errors.add(error);
		}
				
		// Validacion IDIOMA
		try {
		    iDlCmnLngDAO.get(cmpVal, body.getData().get(LNG_VAL), body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LNG_VAL)).build();
		    errors.add(error);
		}
		
		// Validación del código del tipo  de la marca
		try {
		    if((body.getData().get(MKT_VAL) != null  && !(body.getData().get(MKT_VAL).equals(""))) && (body.getData().get(VLD_DAT) != null  && !(body.getData().get(VLD_DAT).equals("")))){
			BigDecimal marca = new BigDecimal(body.getData().get(MKT_VAL));
			Date vldDat = Date.from(LocalDateTime.parse(body.getData().get(VLD_DAT)).atZone(ZoneId.systemDefault()).toInstant());

			iDlCmnMktDAO.get_002(cmpVal, marca, body.getData().get(LNG_VAL), vldDat, body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(MKT_VAL).toString()).build();
		    errors.add(error);
		}
		
		// Validacion MARCA INHABILITADO
		if (body.getData().get(DSB_ROW) == null || body.getData().get(DSB_ROW).equals("") || (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", DSB_ROW,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW)).build();
		    errors.add(error);
		}
		
	
		return errors;
    }

}
