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
import com.mapfre.nwt.trn.cmn.lng.bo.OCmnLngS;
import com.mapfre.nwt.trn.cmn.typ.bo.OCmnTypS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.nwt.trn.trn.ntf.bo.OTrnNtfS;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;	
import com.mapfre.tron.api.cmn.typ.dl.IDlCmnTypDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.ntf.dl.IDlTrnNtfDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
 
@Service("NtfDst")
public class ValidationApiServiceNtfDst implements ValidationApiService {

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;
    
    @Autowired
    IDlCmnTypDAO iDlCmnTypDAO;
    
    @Autowired
    IDlTrnNtfDAO iDlTrnNtfDAO;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
	
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<ValidationError>();
		BigDecimal codError;
		OTrnErrS lvError;
		OCmnLngS lvOCmnLngS;
		List<OCmnTypS> oCmnTypPT;
		OTrnNtfS lvOTrnNtfS;
		
		
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
		    
		    // No se puede modificar el Canl de distribución
		    if(!body.getDataOld().get("DST_TYP_VAL").equals(body.getData().get("DST_TYP_VAL"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "DST_TYP_VAL",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("DST_TYP_VAL")).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar el código del documento
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
		    		  
		 // No se puede modificar fecha de validez
		    if(!body.getDataOld().get("VLD_DAT").equals(body.getData().get("VLD_DAT"))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "VLD_DAT",cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("VLD_DAT")).build();
				errors.add(error);
		    }

		}
		
		// Validamos si el canal de distribución está informado
		if (body.getData().get("DST_TYP_VAL") == null || body.getData().get("DST_TYP_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "DST_TYP_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DST_TYP_VAL")).build();
			errors.add(error);
		}
				
		// Validamos si el el código de documento está informado
		if (body.getData().get("DCN_VAL") == null || body.getData().get("DCN_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "DCN_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DCN_VAL")).build();
			errors.add(error);
		}
		
		// Validamos si fecha de validez está informado
		if (body.getData().get("VLD_DAT") == null || body.getData().get("VLD_DAT").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "VLD_DAT", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("VLD_DAT")).build();
			errors.add(error);
		}
		
		// Validamos si el idioma está informado
		if (body.getData().get("LNG_VAL") == null || body.getData().get("LNG_VAL").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "LNG_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("LNG_VAL")).build();
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
		
		// Validacion MARCA INHABILITADO
		if (body.getData().get("DSB_ROW") == null || body.getData().get("DSB_ROW").equals("") || (!body.getData().get("DSB_ROW").equals(CIns.YES) && !body.getData().get("DSB_ROW").equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "DSB_ROW",cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("DSB_ROW")).build();
		    errors.add(error);
		}
		
		// Si código campo no es de la misma familia que tipo distribución

		if (!body.getData().get("NTF_COD_VAL").startsWith("E") && body.getData().get("DST_TYP_VAL").equals("2")){  
			    codError = new BigDecimal(20005);
			    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "NTF_COD_VAL",cmpVal);
			    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("NTF_COD_VAL")).build();
			    errors.add(error);				    
		}
		
		// Si código campo no es de la misma familia que tipo distribución
		 if(!body.getData().get("NTF_COD_VAL").startsWith("F") && body.getData().get("DST_TYP_VAL").equals("3")) {
		     codError = new BigDecimal(20005);
			    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "NTF_COD_VAL",cmpVal);
			    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("NTF_COD_VAL")).build();
			    errors.add(error);
	
		 }
		 
		// Si código campo no es de la misma familia que tipo distribución
		if(!body.getData().get("NTF_COD_VAL").startsWith("S") && body.getData().get("DST_TYP_VAL").equals("4")) {			    
		    codError = new BigDecimal(20005);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", "NTF_COD_VAL",cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("NTF_COD_VAL")).build();
		    errors.add(error);    
		}

		//Comprobamos si el canal de distribucion existe
		try {
		    if(body.getData().get("DST_TYP_VAL") != null  && !(body.getData().get("DST_TYP_VAL").equals("")))
			oCmnTypPT = iDlCmnTypDAO.get(cmpVal, "DST_TYP_VAL", body.getData().get("DST_TYP_VAL"), body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("DST_TYP_VAL")).build();
		    errors.add(error);
		}
			
		//Comprobamos si el tipo de codigo de campo/atributo existe
		try {
		    String dstTypVal = new String(body.getData().get("DST_TYP_VAL"));
		    
		    if(body.getData().get("NTF_COD_VAL").startsWith(dstTypVal))
			oCmnTypPT = iDlCmnTypDAO.get(cmpVal, "NTF_COD", body.getData().get("NTF_COD_VAL"), body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("NTF_COD_VAL")).build();
		    errors.add(error);
		}
		 // Comprobamos que el código de documento está vigente	
		try {
		    if((body.getData().get("DCN_VAL") != null  && !(body.getData().get("DCN_VAL").equals(""))) && (body.getData().get("VLD_DAT") != null  && !(body.getData().get("VLD_DAT").equals("")))) {
		    
			String dcnVal = new String(body.getData().get("DCN_VAL"));
			Date vldDat = Date.from(LocalDateTime.parse(body.getData().get("VLD_DAT")).atZone(ZoneId.systemDefault()).toInstant());
			
			lvOTrnNtfS = iDlTrnNtfDAO.get_Ntf_003(cmpVal, dcnVal,vldDat, body.getUser().getLanguage().toUpperCase());
		    }
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("DCN_VAL").toString()).build();
			errors.add(error);
	    }
		return errors;
    }

}
