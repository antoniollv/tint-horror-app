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
import com.mapfre.tron.api.cmn.typ.dl.IDlCmnTypDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.ntf.dl.IDlTrnNtfDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Ntf")
public class ValidationApiServiceNtf implements ValidationApiService {
    
    protected static final String APL_RTN = "APL_RTN";

    protected static final String CMP_VAL = "CMP_VAL";

    protected static final String DCN_VAL = "DCN_VAL";

    protected static final String DMS_DST = "DMS_DST";

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String EML_DST = "EML_DST";

    protected static final String EXF_DST = "EXF_DST";

    protected static final String FAX_DST = "FAX_DST";

    protected static final String FIL_SYS_DST = "FIL_SYS_DST";

    protected static final String LCL_DST = "LCL_DST";

    protected static final String NTF_TYP_VAL = "NTF_TYP_VAL";

    protected static final String SGT_ONL_DST = "SGT_ONL_DST";

    protected static final String SMS_DST = "SMS_DST";

    protected static final String TOL_GNR_RER_TYP_VAL = "TOL_GNR_RER_TYP_VAL";

    protected static final String TPL_LYT_VAL = "TPL_LYT_VAL";

    protected static final String VLD_DAT = "VLD_DAT";

    protected static final String WBP_VAL = "WBP_VAL";

    protected static final String WEB_DST = "WEB_DST";

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
		// Campos que no se pueden modificar (PK)
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    // No se puede modificar la compañía
		    if(!body.getDataOld().get(CMP_VAL).equals(body.getData().get(CMP_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", CMP_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(CMP_VAL)).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar el código de documento
		    if(!body.getDataOld().get(DCN_VAL).equals(body.getData().get(DCN_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", DCN_VAL,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(DCN_VAL)).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar fecha de validez
		    if(!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", VLD_DAT,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT)).build();
				errors.add(error);
		    }
		    
		 // No se puede modificar el tipo de notificación
		    if(!body.getDataOld().get(NTF_TYP_VAL).equals(body.getData().get(NTF_TYP_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", NTF_TYP_VAL,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(NTF_TYP_VAL)).build();
				errors.add(error);
		    }

		}
		
		// Validamos si el el código de documento está informado
		if (body.getData().get(DCN_VAL) == null || body.getData().get(DCN_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", DCN_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DCN_VAL)).build();
			errors.add(error);
		}
		
		// Validamos si el tipo de notificación está informado
		if (body.getData().get(NTF_TYP_VAL) == null || body.getData().get(NTF_TYP_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", NTF_TYP_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(NTF_TYP_VAL)).build();
			errors.add(error);
		}
		
		// Validamos si fecha de validez está informado
		if (body.getData().get(VLD_DAT) == null || body.getData().get(VLD_DAT).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", VLD_DAT, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(VLD_DAT)).build();
			errors.add(error);
		}
				
		// Validacion MARCA INHABILITADO
		if (body.getData().get(DSB_ROW) == null || body.getData().get(DSB_ROW).equals("") || (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", DSB_ROW,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW)).build();
		    errors.add(error);
		}
		
		// Validacion DISTRIBUIR DE FORMA LOCAL
		if (body.getData().get(LCL_DST) == null || body.getData().get(LCL_DST).equals("") || (!body.getData().get(LCL_DST).equals(CIns.YES) && !body.getData().get(LCL_DST).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", LCL_DST,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(LCL_DST)).build();
		    errors.add(error);
		}
		
		// Validacion DISTRIBUIR POR EMAIL
		if (body.getData().get(EML_DST) == null || body.getData().get(EML_DST).equals("") || (!body.getData().get(EML_DST).equals(CIns.YES) && !body.getData().get(EML_DST).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", EML_DST,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(EML_DST)).build();
		    errors.add(error);
		}
		
		// Validacion DISTRIBUIR POR FAX
		if (body.getData().get(FAX_DST) == null || body.getData().get(FAX_DST).equals("") || (!body.getData().get(FAX_DST).equals(CIns.YES) && !body.getData().get(FAX_DST).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", FAX_DST,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(FAX_DST)).build();
		    errors.add(error);
		}
		
		// Validacion DISTRIBUIR POR SMS
		if (body.getData().get(SMS_DST) == null || body.getData().get(SMS_DST).equals("") || (!body.getData().get(SMS_DST).equals(CIns.YES) && !body.getData().get(SMS_DST).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", SMS_DST,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(SMS_DST)).build();
		    errors.add(error);
		}
		
		// Validacion DISTRIBUIR EN LOS FICHEROS DEL SISTEMA
		if (body.getData().get(FIL_SYS_DST) == null || body.getData().get(FIL_SYS_DST).equals("") || (!body.getData().get(FIL_SYS_DST).equals(CIns.YES) && !body.getData().get(FIL_SYS_DST).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", FIL_SYS_DST,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(FIL_SYS_DST)).build();
		    errors.add(error);
		}
		
		// Validacion DISTRIBUIR EN LA WEB
		if (body.getData().get(WEB_DST) == null || body.getData().get(WEB_DST).equals("") || (!body.getData().get(WEB_DST).equals(CIns.YES) && !body.getData().get(WEB_DST).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", WEB_DST,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(WEB_DST)).build();
		    errors.add(error);
		}
		
		// Validacion DISTRIBUIR EN EL SISTEMA DE GESTIÓN DOCUMENTAL
		if (body.getData().get(DMS_DST) == null || body.getData().get(DMS_DST).equals("") || (!body.getData().get(DMS_DST).equals(CIns.YES) && !body.getData().get(DMS_DST).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", DMS_DST,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DMS_DST)).build();
		    errors.add(error);
		}
		
		// Validacion DISTRIBUIR EN PEX
		if (body.getData().get(EXF_DST) == null || body.getData().get(EXF_DST).equals("") || (!body.getData().get(EXF_DST).equals(CIns.YES) && !body.getData().get(EXF_DST).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", EXF_DST,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(EXF_DST)).build();
		    errors.add(error);
		}
		
		// Validacion DISTRIBUIR EN FIRMA ELECTRÓNICA
		if (body.getData().get(SGT_ONL_DST) == null || body.getData().get(SGT_ONL_DST).equals("") || (!body.getData().get(SGT_ONL_DST).equals(CIns.YES) && !body.getData().get(SGT_ONL_DST).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", SGT_ONL_DST,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(SGT_ONL_DST)).build();
		    errors.add(error);
		}
		
		// Validacion DISTRIBUIR EN WEBPLUS
		if (body.getData().get(WBP_VAL) == null || body.getData().get(WBP_VAL).equals("") || (!body.getData().get(WBP_VAL).equals(CIns.YES) && !body.getData().get(WBP_VAL).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", WBP_VAL,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(WBP_VAL)).build();
		    errors.add(error);
		}
		
		// Validacion APLICA RETENCIÓN
		if (body.getData().get(APL_RTN) == null || body.getData().get(APL_RTN).equals("") || (!body.getData().get(APL_RTN).equals(CIns.YES) && !body.getData().get(APL_RTN).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NTF", APL_RTN,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(APL_RTN)).build();
		    errors.add(error);
		}
		
		//Comprobamos si el Tipo de notificación existe
		try {
		    if(body.getData().get(NTF_TYP_VAL) != null  && !(body.getData().get(NTF_TYP_VAL).equals(""))) {
			
		   
			iDlCmnTypDAO.get(cmpVal, NTF_TYP_VAL, body.getData().get(NTF_TYP_VAL), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(NTF_TYP_VAL)).build();
		    errors.add(error);
		}
				
		 // Comprobamos que el código de documento está vigente	
		try {
		    if((body.getData().get(DCN_VAL) != null  && !(body.getData().get(DCN_VAL).equals(""))) && (body.getData().get(VLD_DAT) != null  && !(body.getData().get(VLD_DAT).equals(""))) 
			    && (body.getData().get(NTF_TYP_VAL) != null  && !(body.getData().get(NTF_TYP_VAL).equals("")))) {
		    
			String dcnVal = body.getData().get(DCN_VAL);
			Date vldDat = Date.from(LocalDateTime.parse(body.getData().get(VLD_DAT)).atZone(ZoneId.systemDefault()).toInstant());
			String ntfTypVal = body.getData().get(NTF_TYP_VAL);
			
			iDlTrnNtfDAO.get_Ntf_002(cmpVal, dcnVal, ntfTypVal, vldDat, body.getUser().getLanguage().toUpperCase());
		    }
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(DCN_VAL)).build();
			errors.add(error);
	    }

		// Comprobamos si la plantilla de diseño existe
		try {
		    if((body.getData().get(TOL_GNR_RER_TYP_VAL) != null  && !(body.getData().get(TOL_GNR_RER_TYP_VAL).equals(""))) && (body.getData().get(TOL_GNR_RER_TYP_VAL).equals("1") && (body.getData().get(TPL_LYT_VAL) != null  && (!body.getData().get(TPL_LYT_VAL).equals(""))))) {

			    iDlTrnNtfDAO.get_tplLytVal(cmpVal, body.getData().get(TPL_LYT_VAL), body.getUser().getLanguage().toUpperCase());

		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(TPL_LYT_VAL)).build();
		    errors.add(error);
		}
		
		// Si notificación de código de campo es diferente del tipo de notificación seleccionado
		
		try {
		    if((body.getData().get(DCN_VAL) != null  && !(body.getData().get(DCN_VAL).equals(""))) && (body.getData().get(NTF_TYP_VAL) != null  && !(body.getData().get(NTF_TYP_VAL).equals("")))) {
			
			String dcnVal = body.getData().get(DCN_VAL);
			String ntfTypVal = body.getData().get(NTF_TYP_VAL);
			Date vldDat = Date.from(LocalDateTime.parse(body.getData().get(VLD_DAT)).atZone(ZoneId.systemDefault()).toInstant());

			iDlTrnNtfDAO.get_NtfVal(cmpVal, dcnVal, ntfTypVal, vldDat, body.getUser().getLanguage().toUpperCase());
		    }
		}
		 catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(NTF_TYP_VAL)).build();
			errors.add(error);
	    }
		
		return errors;
    }

} 
