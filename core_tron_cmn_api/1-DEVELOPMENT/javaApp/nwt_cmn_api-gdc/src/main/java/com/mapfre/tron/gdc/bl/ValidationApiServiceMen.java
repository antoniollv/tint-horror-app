package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.dgtp.gaia.core.exceptions.ExceptionData;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.men.bo.OCmnMenS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.men.dl.OCmnMenDao;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Men")
public class ValidationApiServiceMen implements ValidationApiService{
    
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
	     OCmnMenS OCmnMenS;
	     Boolean existe=Boolean.FALSE;
	     BigDecimal cmpVal = new BigDecimal(body.getUser().getCompany());
	     
	     // Comprobar si es Crear o Modificar
	     if (body.getDataOld() == null) {
	         accion = CTrn.ACN_TYP_CRT;
	     }
	     else {
	         accion = CTrn.ACN_TYP_MDF;
	     }
	     
	     if (accion.equals(CTrn.ACN_TYP_MDF))
	     {
		 if(!body.getDataOld().get("MEN_SQN_VAL").equals(body.getData().get("MEN_SQN_VAL")) ) {
		     // Validar si exite secuencia MEN_SQN_VAL
		     try {
			 if(body.getData().get("AGR_VAL_PRN") != null  && !(body.getData().get("AGR_VAL_PRN").equals("")) &&
			    body.getData().get("MEN_SQN_VAL") != null  && !(body.getData().get("MEN_SQN_VAL").equals(""))){
			     
			     existe=Boolean.FALSE;
			     OCmnMenS = OCmnMenDao.get_menSqnVal(cmpVal, body.getData().get("AGR_VAL_PRN"),new BigDecimal(body.getData().get("MEN_SQN_VAL")), body.getUser().getLanguage().toUpperCase());
			     existe=Boolean.TRUE;
			     
			     BigDecimal codError = new BigDecimal(20002);
			     OTrnErrS err = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MEN", "MEN_SQN_VAL", cmpVal);
			     NwtException exception = new NwtException(err.getErrIdnVal());
			     exception.add(err);
			     throw exception;
			     
			 }
		     }
		     catch (NwtException e) {
			 if (existe) {
			  	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("MEN_SQN_VAL").toString()).build();
			  	errors.add(error);
			 }
		     }  
		 }
	     }
	     
	     if (accion.equals(CTrn.ACN_TYP_CRT)) {
		 
		  // Validar si exite el AGR_VAL_PRN
		  try {
		      	if(body.getData().get("AGR_VAL_PRN") != null  && !(body.getData().get("AGR_VAL_PRN").equals(""))){
		      	OCmnMenS = OCmnMenDao.get_agrValPrn(cmpVal, body.getData().get("AGR_VAL_PRN"), body.getUser().getLanguage().toUpperCase());
		  	}
		  }
		  catch (NwtException e) {
		  	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("AGR_VAL_PRN").toString()).build();
		  	errors.add(error);
		  }
		  
		 // Validar si exite el OPR_IDN_VAL
		  try {
		      	if(body.getData().get("OPR_IDN_VAL") != null  && !(body.getData().get("OPR_IDN_VAL").equals(""))){
		      	OCmnMenS = OCmnMenDao.get_oprIdnVal(cmpVal, body.getData().get("OPR_IDN_VAL"), body.getUser().getLanguage().toUpperCase());
		  	}
		  }
		  catch (NwtException e) {
		  	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("OPR_IDN_VAL").toString()).build();
		  	errors.add(error);
		  }
		  
		  
		// Validar si exite secuencia MEN_SQN_VAL
		  try {
		      	if(body.getData().get("AGR_VAL_PRN") != null  && !(body.getData().get("AGR_VAL_PRN").equals("")) &&
		      	   body.getData().get("MEN_SQN_VAL") != null  && !(body.getData().get("MEN_SQN_VAL").equals(""))){
		      	    
		      	    existe=Boolean.FALSE;
		      	    OCmnMenS = OCmnMenDao.get_menSqnVal(cmpVal, body.getData().get("AGR_VAL_PRN"),new BigDecimal(body.getData().get("MEN_SQN_VAL")), body.getUser().getLanguage().toUpperCase());
		      	    existe=Boolean.TRUE;
		      	    
		      	    BigDecimal codError = new BigDecimal(20002);
		      	    OTrnErrS err = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MEN", "MEN_SQN_VAL", cmpVal);
		      	    NwtException exception = new NwtException(err.getErrIdnVal());
		      	    exception.add(err);
		      	    throw exception;
		      	}
		  }
		  catch (NwtException e) {		      
		      if (existe) {
		  	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("MEN_SQN_VAL").toString()).build();
		  	errors.add(error);
		      }
		  }
		
	     }
	     return errors;
    }

}
