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

@Service("Cst")
public class ValidationApiServiceCst implements ValidationApiService {

    protected static final String CMP_VAL = "CMP_VAL";

    protected static final String CST_CTG_NAM = "CST_CTG_NAM";

    protected static final String CST_CTG_VAL = "CST_CTG_VAL";

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String LNG_VAL = "LNG_VAL";

    protected static final String PHY_PRS = "PHY_PRS";

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
		// Campos que no se pueden modificar (PK)
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    
		// No se puede modificar la compañia
		    if(!body.getDataOld().get(CMP_VAL).equals(body.getData().get(CMP_VAL))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CST", CMP_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(CMP_VAL)).build();
			errors.add(error);
		    }
		    
		    // No se puede modificar Categoría cliente 
		    if (!body.getDataOld().get(CST_CTG_VAL).equals(body.getData().get(CST_CTG_VAL))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CST",CST_CTG_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(CST_CTG_VAL)).build();
			errors.add(error);
		    }
		    
		    // No se puede modificar fecha de actualización
		    if (!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CST", VLD_DAT,cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT)).build();
			errors.add(error);
		    }
		    
		    // No se puede modificar lenguaje
		    if (!body.getDataOld().get(LNG_VAL).equals(body.getData().get(LNG_VAL))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CST", LNG_VAL,cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(LNG_VAL)).build();
			errors.add(error);
		    	}
		    }
		
			
		    // Validamos si Categoría cliente es null 
		    if (body.getData().get(CST_CTG_VAL) == null || body.getData().get(CST_CTG_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CST",CST_CTG_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CST_CTG_VAL)).build();
			errors.add(error);
		    }
		
		    // Validamos si la Descripción de categoría de cliente es null
		    if (body.getData().get(CST_CTG_NAM) == null || body.getData().get(CST_CTG_NAM).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CST", CST_CTG_NAM, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(CST_CTG_NAM)).build();
			errors.add(error);
		    }
		    
		    // Validación marca de fisico o juridico (s-fisico, n-juridico) 
		    if (body.getData().get(PHY_PRS) == null || (!body.getData().get(PHY_PRS).equals(CIns.YES)&& !body.getData().get(PHY_PRS).equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CST", PHY_PRS,cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(PHY_PRS)).build();
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
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CST", DSB_ROW,cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW)).build();
			errors.add(error);
		    }
	
		return errors;
    }

}
