package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.tron.api.cmn.typ.dl.IDlCmnTypDAO;
import com.mapfre.tron.api.pid.pfe.dl.IDlRcdEcdDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Ecd")
public class ValidationApiServiceEcd implements ValidationApiService{
    
    protected static final String ECC_VAL = "ECC_VAL";

    protected static final String ECM_BRW_CNC_VAL = "ECM_BRW_CNC_VAL";

    protected static final String TYP_GRG_CMS = "TYP_GRG_CMS";

    @Autowired
    IDlRcdEcdDAO   iDlRcdEcdDAO;
    
    @Autowired
    IDlCmnTypDAO iDlCmnTypDAO;
    
    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	      
	     List<ValidationError> errors = new ArrayList<>();
	     ValidationError error;
	     String accion;
	    
	     BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
	     
	     // Comprobar si es Crear o Modificar
	     if (body.getDataOld() == null) {
	         accion = CTrn.ACN_TYP_CRT;
	     }
	     else {
	         accion = CTrn.ACN_TYP_MDF;
       }
	     
	     if (accion.equals(CTrn.ACN_TYP_CRT)) {
		 // Validar si existe el desglose 4
		  try {
		      if(body.getData().get(ECM_BRW_CNC_VAL) != null  && !(body.getData().get(ECM_BRW_CNC_VAL).equals(""))){
			  iDlRcdEcdDAO.get_ecmBrwCncVal4(cmpVal, new BigDecimal(body.getData().get(ECM_BRW_CNC_VAL)), new BigDecimal (body.getData().get(ECC_VAL)), body.getUser().getLanguage().toUpperCase());
			}
		  }
		  catch (NwtException e) {
		      	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(ECM_BRW_CNC_VAL)).build();
			errors.add(error);
		  }
		  
		  // Validar si exite el concepto económico
		  try {
		      	if(body.getData().get(ECC_VAL) != null  && !(body.getData().get(ECC_VAL).equals(""))){
		      	iDlRcdEcdDAO.get_eccVal(cmpVal, new BigDecimal (body.getData().get(ECC_VAL)), body.getUser().getLanguage().toUpperCase());
		  	}
		  }
		  catch (NwtException e) {
		  	error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(ECC_VAL)).build();
		  	errors.add(error);
		  }
	     }
	     
	  
	//Comprobamos si el agrupamiento comisiones existe
	try {
	    if(body.getData().get(TYP_GRG_CMS) != null  && !(body.getData().get(TYP_GRG_CMS).equals("")))
	       iDlRcdEcdDAO.get(cmpVal, TYP_GRG_CMS, body.getData().get(TYP_GRG_CMS), body.getUser().getLanguage().toUpperCase());
	   }
	   catch (NwtException e) {
	       error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(TYP_GRG_CMS)).build();
	   	errors.add(error);
	}
	 
	     return errors;
    }

}
