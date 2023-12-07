package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.cmn.opm.bo.OCmnOpmS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.opm.dl.IDlCmnOpmDAO;
import com.mapfre.tron.api.cmn.typ.dl.IDlCmnTypDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Opm")
public class ValidationApiServiceOpm implements ValidationApiService{
    
    @Autowired
    IDlCmnOpmDAO   iDlCmnOpmDAO;
    
    @Autowired
    IDlCmnTypDAO iDlCmnTypDAO;
    
    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	     // TODO Auto-generated method stub
	     String accion;
	     ValidationError error;
	     List<ValidationError> errors = new ArrayList<ValidationError>();
	     BigDecimal codError;
	     OTrnErrS lvError;
	     OCmnOpmS oCmnOpmS;
	    
	     BigDecimal cmpVal = new BigDecimal(body.getUser().getCompany());
	     // Comprobar si es Crear o Modificar
	     if (body.getDataOld() == null) {
	         accion = CTrn.ACN_TYP_CRT;
	     }
	     else {
	         accion = CTrn.ACN_TYP_MDF;
             }
	     
		  // Validar si existe la instalacion
	     try {
		    if(body.getData().get("INS_VAL") != null  && !(body.getData().get("INS_VAL").equals(""))){
		      oCmnOpmS = iDlCmnOpmDAO.get_insVal(cmpVal, body.getData().get("INS_VAL"), body.getUser().getLanguage().toUpperCase());
		      	if(!oCmnOpmS.getInsVal().equals(body.getData().get("INS_VAL"))) {
		      	    codError = new BigDecimal(20120);
		      	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "OPM", "INS_VAL", cmpVal);
		      	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("INS_VAL").toString()).build();
		      	    errors.add(error);
		      	}
		     }
		  }
		  catch (NwtException e) {
		  	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("INS_VAL").toString()).build();
		  	errors.add(error);
		  }     
	 
	     return errors;
    }

}
