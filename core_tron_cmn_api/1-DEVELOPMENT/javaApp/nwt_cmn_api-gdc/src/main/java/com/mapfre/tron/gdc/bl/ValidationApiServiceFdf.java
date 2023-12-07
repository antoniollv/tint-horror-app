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
import com.mapfre.tron.api.cmn.typ.dl.IDlCmnTypDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.prc.dl.IDlTrnPrcDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Fdf")
public class ValidationApiServiceFdf implements ValidationApiService {

    protected static final String CMP_VAL = "CMP_VAL";

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String FRD_IDN_DYN_NAM = "FRD_IDN_DYN_NAM";

    protected static final String FRD_IDN_DYN_TYP_VAL = "FRD_IDN_DYN_TYP_VAL";

    protected static final String VLD_DAT = "VLD_DAT";

    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;	

    @Autowired
    IDlCmnTypDAO iDlCmnTypDAO;	
    
    @Autowired
    IDlTrnPrcDAO iDlTrnPrcDAO;

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errors = new ArrayList<>();
		ValidationError  error;
		String           accion;
		OTrnErrS         lvError;
		BigDecimal       codError;
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));
	
		// Comprobar si es Crear o Modificar
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		
		// Si la accion es modificar
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    // No se puede modificar la compañia
		    if(!body.getDataOld().get(CMP_VAL).equals(body.getData().get(CMP_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "FDF", CMP_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(CMP_VAL).toString()).build();
				errors.add(error);
		    }
    
		    // No se puede modificar Fecha validez
		    if(!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "FDF", VLD_DAT, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT).toString()).build();
				errors.add(error);
		    }
		}
		
		// Validamos  si el Identificador de Fraude Dinámico de causa está informado
		if (body.getData().get(FRD_IDN_DYN_TYP_VAL) == null || body.getData().get(FRD_IDN_DYN_TYP_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "FDF", FRD_IDN_DYN_TYP_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(FRD_IDN_DYN_TYP_VAL).toString()).build();
			errors.add(error);
		}
		
		try {
        		// Si el tipo de identificador de fraude dinamico seleccionado es igual a FUNCION DINAMICA
        		if (body.getData().get(FRD_IDN_DYN_TYP_VAL) != null  && !(body.getData().get(FRD_IDN_DYN_TYP_VAL).equals("")) && (body.getData().get(FRD_IDN_DYN_TYP_VAL).equals("2"))) {
        		    // Si el Identificador de fraude dinamico es igual null o vacío, retornar un error. 
        		    if (body.getData().get(FRD_IDN_DYN_NAM) == null || body.getData().get(FRD_IDN_DYN_NAM).equals("")) {
        			codError = new BigDecimal(20005);
        			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "FDF", FRD_IDN_DYN_NAM, cmpVal);
        			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(FRD_IDN_DYN_NAM)).build();
        			errors.add(error);
        		    }
        		}//Si el Tipo de identificador de fraude dinamico seleccionado es igual a SECUENCIAL
        		else if(body.getData().get(FRD_IDN_DYN_TYP_VAL) != null  && !(body.getData().get(FRD_IDN_DYN_TYP_VAL).equals("")) && (body.getData().get(FRD_IDN_DYN_TYP_VAL).equals("1")) &&(body.getData().get(FRD_IDN_DYN_NAM) != null  && !(body.getData().get(FRD_IDN_DYN_NAM).equals("")))) {
    			    	// Si Tipo de identificador de fraude dinamico esta informado, retornar un error. 

        				codError = new BigDecimal(20005);
        				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "FDF", FRD_IDN_DYN_NAM, cmpVal);
        				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(FRD_IDN_DYN_NAM)).build();
        				errors.add(error);

        		}
		}
    		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(FRD_IDN_DYN_NAM)).build();
			errors.add(error);
		}
		
		// Validamos si existe el procedimiento Identificador de fraude dinamico
		try {
		    if (body.getData().get(FRD_IDN_DYN_NAM) != null && !(body.getData().get(FRD_IDN_DYN_NAM).equals(""))) {
			iDlTrnPrcDAO.getNomPrg(body.getData().get(FRD_IDN_DYN_NAM),
			body.getUser().getLanguage().toUpperCase(), cmpVal);
		    }
		} catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(FRD_IDN_DYN_NAM).toString()).build();
	    	    errors.add(error);
	 	}
		
		// Comprobamos que DSB_ROW valga S o N
		if (body.getData().get(DSB_ROW) == null || (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "FDF", DSB_ROW, cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW).toString()).build();
		    errors.add(error);
		}
		
		return errors;
    }
}
