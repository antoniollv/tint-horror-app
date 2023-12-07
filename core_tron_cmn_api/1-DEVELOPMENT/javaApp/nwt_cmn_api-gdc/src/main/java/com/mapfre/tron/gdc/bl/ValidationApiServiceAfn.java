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
import com.mapfre.tron.api.fdf.afn.dl.IDlFdfAfnDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Afn")
public class ValidationApiServiceAfn implements ValidationApiService{
    
    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String VLD_DAT = "VLD_DAT";

    protected static final String AFR_NDC_VAL = "AFR_NDC_VAL";

    @Autowired
    IDlFdfAfnDAO iDlFdfAfnDAO;
    
    @Autowired
    DlTrnErr lvDlTrnErr;
    
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
	     //
	     // Si estamos modificando/clonando...
	     if (accion.equals(CTrn.ACN_TYP_MDF)) {
	         // No se puede modificar el codigo Indicador Fraude
	         if(!body.getDataOld().get(AFR_NDC_VAL).equals(body.getData().get(AFR_NDC_VAL))) {
	    	       codError = new BigDecimal(20120);
		           lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AFN", AFR_NDC_VAL, cmpVal);
		           error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(AFR_NDC_VAL)).build();
		           errors.add(error);
	         }
	         // No se puede modificar la fecha validez
	         if(!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
	    	       codError = new BigDecimal(20120);
		           lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AFN", VLD_DAT, cmpVal);
		           error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT)).build();
		           errors.add(error);
	         }
	     }
	     //
	     // Validacion codigo Indicador Fraude - Comprobar si existe
	     try {
	          String indFraude = (body.getData().get(AFR_NDC_VAL));
	          String usrLngVal =  body.getUser().getLanguage().toUpperCase();	     
	          iDlFdfAfnDAO.get_afnPT(cmpVal, indFraude, usrLngVal);	     
	     }
	     catch (NwtException e) {
	          codError = new BigDecimal(20001);
	          lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AFN", AFR_NDC_VAL, cmpVal);
	          error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(AFR_NDC_VAL)).build();
	          errors.add(error);
	     }
	     //	
	     // Validacion MARCA INHABILITADO
	     if (body.getData().get(DSB_ROW) == null || (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
	         codError = new BigDecimal(20010);
	         lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "AFN", DSB_ROW,cmpVal);
	         error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW)).build();
	         errors.add(error);
	     }	
	     //
	     return errors;
    }

}
