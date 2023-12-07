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

@Service("AvdDef")
public class ValidationApiServiceAvdDef implements ValidationApiService {

    protected static final String CMP_VAL = "CMP_VAL";

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String ECM_ACV_NAM = "ECM_ACV_NAM";

    protected static final String ECM_ACV_VAL = "ECM_ACV_VAL";

    protected static final String LNG_VAL = "LNG_VAL";

    protected static final String VLD_DAT = "VLD_DAT";

    protected static final String VLN_LVL_TYP_VAL = "VLN_LVL_TYP_VAL";

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
		// Campos que no se pueden modificar (PK)
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    
		    // No se puede modificar la compañia
		    if(!body.getDataOld().get(CMP_VAL).equals(body.getData().get(CMP_VAL))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD", CMP_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(CMP_VAL)).build();
			errors.add(error);
		    }
		    
		    // No se puede modificar el código de actividad económica 
		    if (!body.getDataOld().get(ECM_ACV_VAL).equals(body.getData().get(ECM_ACV_VAL))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD",ECM_ACV_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(ECM_ACV_VAL)).build();
			errors.add(error);
		    }
		    
		    // No se puede modificar fecha de actualización
		    if (!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD", VLD_DAT,cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT)).build();
			errors.add(error);
		    }
		    
		    // No se puede modificar lenguaje
		    if (!body.getDataOld().get(LNG_VAL).equals(body.getData().get(LNG_VAL))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD", LNG_VAL,cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(LNG_VAL)).build();
			errors.add(error);
		    	}
		    }
		
			
		    // Validamos si código de actividad económica es null 
		    if (body.getData().get(ECM_ACV_VAL) == null || body.getData().get(ECM_ACV_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD",ECM_ACV_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(ECM_ACV_VAL)).build();
			errors.add(error);
		    }
		
		    // Validamos si la descripción actividad económica es null
		    if (body.getData().get(ECM_ACV_NAM) == null || body.getData().get(ECM_ACV_NAM).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD", ECM_ACV_NAM, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(ECM_ACV_NAM)).build();
			errors.add(error);
		    }
		    
		    // Validamos si nivel de riesgo de vulnerabilidad es null
		    if (body.getData().get(VLN_LVL_TYP_VAL) == null || body.getData().get(VLN_LVL_TYP_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD",VLN_LVL_TYP_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLN_LVL_TYP_VAL)).build();
			errors.add(error);
		    }
		
		    // Validamos el idioma
		    try {
			iDlCmnLngDAO.get(cmpVal, body.getData().get(LNG_VAL),body.getUser().getLanguage().toUpperCase());
		    } catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LNG_VAL).toString()).build();
			errors.add(error);
		    }

		    // Validación Marca Inhabilitado
		    if (body.getData().get(DSB_ROW) == null || (!body.getData().get(DSB_ROW).equals(CIns.YES)&& !body.getData().get(DSB_ROW).equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AVD", DSB_ROW,cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW).toString()).build();
			errors.add(error);
		    }
	
		return errors;
    }

}
