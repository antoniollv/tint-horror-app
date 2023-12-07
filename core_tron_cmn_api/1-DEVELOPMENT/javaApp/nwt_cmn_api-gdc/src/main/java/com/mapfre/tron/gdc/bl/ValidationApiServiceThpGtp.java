package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.thp.cmc.bo.OThpCmcS;
import com.mapfre.nwt.trn.thp.gtp.bo.OThpGtpS;
import com.mapfre.nwt.trn.tpd.avd.bo.OTpdAvdS;
import com.mapfre.nwt.trn.tpd.sps.bo.OTpdSpsS;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.thp.cmc.dl.IDlThpCmcDAO;
import com.mapfre.tron.api.thp.gtp.dl.IDlThpGtpDAO;
import com.mapfre.tron.api.tpd.avd.dl.IDlTpdAvdDAO;
import com.mapfre.tron.api.tpd.sps.dl.IDlTpdSpsDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("ThpGtp")
public class ValidationApiServiceThpGtp implements ValidationApiService {

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlThpCmcDAO iDlThpCmcDAO;
    
    @Autowired
    IDlTpdAvdDAO iDlTpdAvdDAO;
    
    @Autowired
    IDlTpdSpsDAO iDlTpdSpsDAO;
    
    @Autowired
    IDlThpGtpDAO iDlThpGtpDAO;


    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<ValidationError>();
		BigDecimal codError;
		OTrnErrS lvError;
		
		OThpCmcS oThpCmcS;
		OTpdAvdS lvOTpdAvdS;
		OTpdSpsS lvOTpdSpsS;
		OThpGtpS lvOThpGtpS;
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get("COD_CIA"));
	
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		// Campos que no se pueden modificar (PK)
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    		    
		    // No se puede modificar el código de la compañía
		    if(!body.getDataOld().get("COD_CIA").equals(body.getData().get("COD_CIA"))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GTP", "CMP_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("COD_CIA")).build();
			errors.add(error);
		    }
		    
		    // No se puede modificar la actividad tercero proveedor
		    if (!body.getDataOld().get("COD_ACT_TERCERO").equals(body.getData().get("COD_ACT_TERCERO"))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GTP","THP_ACV_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("COD_ACT_TERCERO")).build();
			errors.add(error);
		    }
		    
		    // No se puede modificar la fecha de validez
		    if (!body.getDataOld().get("FEC_VALIDEZ").equals(body.getData().get("FEC_VALIDEZ"))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GTP", "VLD_DAT",cmpVal); 
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("FEC_VALIDEZ")).build();
			errors.add(error);
		    }
		    
		 // No se puede modificar el Ramo por compañía	    
		    if(!body.getDataOld().get("COD_RAMO").equals(body.getData().get("COD_RAMO"))) {
			codError = new BigDecimal(20120);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GTP", "LOB_VAL",cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get("COD_RAMO")).build();
			errors.add(error);
		    }
		}
		
		// Validamos si el campo Fecha validez está informado 
		   if (body.getData().get("FEC_VALIDEZ") == null || body.getData().get("FEC_VALIDEZ").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GTP", "VLD_DAT", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("FEC_VALIDEZ")).build();
			errors.add(error);
		   }
		
		// Validamos que la actividad tercero proveedor no sea null 
		   if (body.getData().get("COD_ACT_TERCERO") == null || body.getData().get("COD_ACT_TERCERO").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GTP", "THP_ACV_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("COD_ACT_TERCERO")).build();
			errors.add(error);
		   } 
		   
		// Validamos si el campo Ramo está informado 
		   if (body.getData().get("COD_RAMO") == null || body.getData().get("COD_RAMO").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GTP", "LOB_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("COD_RAMO")).build();
			errors.add(error);
		   }
		   
		   
		// Validamos si el campo Número de días está informado 
		   if (body.getData().get("NUM_DIAS") == null || body.getData().get("NUM_DIAS").equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "GTP", "DAY_VAL", cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get("NUM_DIAS")).build();
			errors.add(error);
		   }
		   
		// Comprobamos si existe la actividad
			try {
			    if(body.getData().get("COD_ACT_TERCERO") != null  && !(body.getData().get("COD_ACT_TERCERO").equals(""))){
			    lvOTpdAvdS = iDlTpdAvdDAO.get(cmpVal, new BigDecimal (body.getData().get("COD_ACT_TERCERO")), body.getUser().getLanguage().toUpperCase());
			}
			}
			catch (NwtException e) {
			    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("COD_ACT_TERCERO")).build();
			    errors.add(error);
			}
		   
		 //Validar que existe el Ramo
		   try {
			if(body.getData().get("COD_RAMO") != null  && !(body.getData().get("COD_RAMO").equals(""))){
			    oThpCmcS = iDlThpCmcDAO.getCodRamo(cmpVal, new BigDecimal (body.getData().get("COD_RAMO")), body.getUser().getLanguage().toUpperCase());
			 }
		   	}
			catch (NwtException e) {
			    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("COD_RAMO")).build();
			    errors.add(error);
			}
		   
		// Comprobamos si existe la tipología
		   try {
		       if(body.getData().get("COD_TIPOLOGIA") != null && !(body.getData().get("COD_TIPOLOGIA").equals(""))){
			  lvOTpdSpsS = iDlTpdSpsDAO.get_tylTya(cmpVal, new BigDecimal (body.getData().get("COD_ACT_TERCERO")), body.getData().get("COD_TIPOLOGIA"), body.getUser().getLanguage().toUpperCase());
			}
		   }
		   catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("COD_TIPOLOGIA")).build();
			errors.add(error);
		   }

		// Comprobamos si existe la categoría de proveedor
		   try {
		       if(body.getData().get("COD_CATEGORIA_PRV") != null  && !(body.getData().get("COD_CATEGORIA_PRV").equals(""))){
			   lvOTpdSpsS = iDlTpdSpsDAO.get_splCtg(cmpVal, body.getData().get("COD_CATEGORIA_PRV"),body.getUser().getLanguage().toUpperCase(), body.getUser().getLanguage().toUpperCase());
			}
		   }
		   catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("COD_CATEGORIA_PRV")).build();
			errors.add(error);
		   }
		   
		// Comprobamos si existe el tipo de documento
		   try {
		       if(body.getData().get("TIP_DOCUM") != null  && !(body.getData().get("TIP_DOCUM").equals(""))){
			   lvOThpGtpS = iDlThpGtpDAO.get(cmpVal, body.getData().get("TIP_DOCUM"), body.getUser().getLanguage().toUpperCase());
			}
		   }
		   catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get("TIP_DOCUM")).build();
			errors.add(error);
		   }
		
		return errors;
    }

}


