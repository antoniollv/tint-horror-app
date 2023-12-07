package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;


@Service("Afp")
public class ValidationApiServiceAfp implements ValidationApiService {

    protected static final String AFR_NDC_VAL = "AFR_NDC_VAL";

    protected static final String LNG_VAL = "LNG_VAL";

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;
    
    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	        
	       List<ValidationError> errors = new ArrayList<>();
	       ValidationError error;
	       String accion;
	       OTrnErrS lvError;
	       BigDecimal codError;
	       BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));	
	       
	       // Comprobar si es Crear o Modificar
	       if (body.getDataOld() == null) { 
	           accion = CTrn.ACN_TYP_CRT;
	       }
	       else {
	           accion = CTrn.ACN_TYP_MDF;
         }

	       // Validacion IDIOMA
	      try {
	          iDlCmnLngDAO.get(cmpVal, body.getData().get(LNG_VAL),  body.getUser().getLanguage().toUpperCase());
	      }
	      catch (NwtException e) {
	          error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LNG_VAL)).build();
	          errors.add(error);
	      }
	  
	      // Si estamos modificando/clonando...
	      if (accion.equals(CTrn.ACN_TYP_MDF)) {
	          //
	          // No se puede modificar el codigo Indicador Fraude
	          if(!body.getDataOld().get(AFR_NDC_VAL).equals(body.getData().get(AFR_NDC_VAL))) {
	    	        codError = new BigDecimal(20120);
		            lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AFP", AFR_NDC_VAL, cmpVal);
		            error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(AFR_NDC_VAL).toString()).build();
		            errors.add(error);
	          }
	          // No se puede modificar el idioma
	          if(!body.getDataOld().get(LNG_VAL).equals(body.getData().get(LNG_VAL))) {
	              codError = new BigDecimal(20120);
		            lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AFP", LNG_VAL, cmpVal);
		            error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(LNG_VAL).toString()).build();
		            errors.add(error);
	          }
	      }
	      //	
	      //	
	      return errors;
    }

}
