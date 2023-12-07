package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.pgm.bo.OTrnPgmS;
import com.mapfre.tron.api.cmn.men.dl.OCmnMenDao;
import com.mapfre.tron.api.pgm.dl.IDlPgmDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Pgm")
public class ValidationApiServicePgm implements ValidationApiService{
    
    @Autowired
    IDlPgmDAO   iDlPgmDAO;
    
    @Autowired
    OCmnMenDao   OCmnMenDao;
    
    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	     // TODO Auto-generated method stub
	     List<ValidationError> errors = new ArrayList<ValidationError>();
	     ValidationError error;
	     String accion;
	     OTrnPgmS OTrnPgmS;
	    
	     BigDecimal cmpVal = new BigDecimal(body.getUser().getCompany());
	     
	     // Comprobar si es Crear o Modificar
	     if (body.getDataOld() == null) {
	         accion = CTrn.ACN_TYP_CRT;
	     }
	     else {
	         accion = CTrn.ACN_TYP_MDF;
	     }
	     
	     if(accion.equals(CTrn.ACN_TYP_MDF)){
		 
		 // Validar si exite EL APP_VAL
		  try {
		      	if(body.getData().get("APP_VAL") != null  && !(body.getData().get("APP_VAL").equals(""))){
		      	OTrnPgmS = iDlPgmDAO.get_pgmAppVal(cmpVal, body.getData().get("APP_VAL"), body.getUser().getLanguage().toUpperCase());
		  	}
		  }
		  catch (NwtException e) {
		  	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("APP_VAL").toString()).build();
		  	errors.add(error);
		  }
		  
		  
		 // Validar si exite el OPR_IDN_VAL
		  try {
		      	if(body.getData().get("OPR_IDN_VAL") != null  && !(body.getData().get("OPR_IDN_VAL").equals(""))){
		      	OTrnPgmS = iDlPgmDAO.get_oprIdnVal(cmpVal, body.getData().get("OPR_IDN_VAL"), body.getUser().getLanguage().toUpperCase());
		  	}
		  }
		  catch (NwtException e) {
		  	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("OPR_IDN_VAL").toString()).build();
		  	errors.add(error);
		  }
		 
	     }
	     if (accion.equals(CTrn.ACN_TYP_CRT)) {
		  // Validar si exite el programa
		  try {
		      	if(body.getData().get("PGM_VAL") != null  && !(body.getData().get("PGM_VAL").equals(""))){
		      	OTrnPgmS = iDlPgmDAO.get_pgmVal(cmpVal, body.getData().get("PGM_VAL"), body.getUser().getLanguage().toUpperCase());
		  	}
		  }
		  catch (NwtException e) {
		  	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("PGM_VAL").toString()).build();
		  	errors.add(error);
		  }
		  
		 // Validar si exite EL APP_VAL
		  try {
		      	if(body.getData().get("APP_VAL") != null  && !(body.getData().get("APP_VAL").equals(""))){
		      	OTrnPgmS = iDlPgmDAO.get_pgmAppVal(cmpVal, body.getData().get("APP_VAL"), body.getUser().getLanguage().toUpperCase());
		  	}
		  }
		  catch (NwtException e) {
		  	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("APP_VAL").toString()).build();
		  	errors.add(error);
		  }
		  
		  
		 // Validar si exite el OPR_IDN_VAL
		  try {
		      	if(body.getData().get("OPR_IDN_VAL") != null  && !(body.getData().get("OPR_IDN_VAL").equals(""))){
		      	OTrnPgmS = iDlPgmDAO.get_oprIdnVal(cmpVal, body.getData().get("OPR_IDN_VAL"), body.getUser().getLanguage().toUpperCase());
		  	}
		  }
		  catch (NwtException e) {
		  	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("OPR_IDN_VAL").toString()).build();
		  	errors.add(error);
		  }
	     }
	     return errors;
    }

}
