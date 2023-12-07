package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.lng.dl.IDlCmnLngDAO;
import com.mapfre.tron.api.cmn.mkd.dl.IDlCmnMkdDao;
import com.mapfre.tron.api.tpd.icf.dl.IDlTpdIcfDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
 
@Service("MkdLob")
public class ValidationApiServiceMkdLob implements ValidationApiService {

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String LOB_VAL = "LOB_VAL";

    protected static final String MKD_VAL = "MKD_VAL";

    protected static final String VLD_DAT = "VLD_DAT";

    @Autowired
    DlTrnErr lvDlTrnErr;
    
    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;
    
    @Autowired
    IDlTpdIcfDAO iDlTpdIcfDAO;
    
    @Autowired
    IDlCmnMkdDao iDlCmnMkdDAO;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
	
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<>();
		BigDecimal codError;
		OTrnErrS lvError;

		
		BigDecimal cmpVal = new BigDecimal(body.getData().get("CMP_VAL"));
	
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		// Campos que no se pueden modificar (PK)
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    // No se puede modificar el ramo
		    if(!body.getDataOld().get(LOB_VAL).equals(body.getData().get(LOB_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", LOB_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(LOB_VAL)).build();
				errors.add(error);
		    }
		    
		    // No se puede modificar el código de marca
		    if(!body.getDataOld().get(MKD_VAL).equals(body.getData().get(MKD_VAL))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", MKD_VAL,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(MKD_VAL)).build();
				errors.add(error);
		    }
		    
		    
		 // No se puede modificar fecha de validez
		    if(!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", VLD_DAT,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT)).build();
				errors.add(error);
		    }
		}
		
		// Validamos si el ramo está informado
		if (body.getData().get(LOB_VAL) == null || body.getData().get(LOB_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", LOB_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(LOB_VAL)).build();
			errors.add(error);
		}
		
		// Validamos si el campo código de marca está informado
		if (body.getData().get(MKD_VAL) == null || body.getData().get(MKD_VAL).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", MKD_VAL, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(MKD_VAL)).build();
			errors.add(error);
		}
		
		
		// Validamos si fecha de validez está informado
		if (body.getData().get(VLD_DAT) == null || body.getData().get(VLD_DAT).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", VLD_DAT, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(VLD_DAT)).build();
			errors.add(error);
		}
		
		//Validamos si existe el ramo
		try {
		    if(body.getData().get(LOB_VAL) != null  && !(body.getData().get(LOB_VAL).equals(""))) {
			iDlTpdIcfDAO.get(cmpVal, new BigDecimal (body.getData().get(LOB_VAL)), body.getUser().getLanguage().toUpperCase());
		    }
		}
		catch (NwtException e) {
		    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LOB_VAL).toString()).build();
		    errors.add(error);
		}
				
		// Validación del código del código de la marca
		try {
		    	if((body.getData().get(MKD_VAL) != null  && !(body.getData().get(MKD_VAL).equals(""))) && (body.getData().get(VLD_DAT) != null  && !(body.getData().get(VLD_DAT).equals("")))){
		    	    BigDecimal marca = new BigDecimal(body.getData().get(MKD_VAL));
		    	    Date vldDat = Date.from(LocalDateTime.parse(body.getData().get(VLD_DAT)).atZone(ZoneId.systemDefault()).toInstant());

				iDlCmnMkdDAO.get_002(cmpVal, marca, vldDat, body.getUser().getLanguage().toUpperCase());
			}
		}
		catch (NwtException e) {
				error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(MKD_VAL).toString()).build();
				errors.add(error);
		}
				
		// Validacion MARCA INHABILITADO
		if (body.getData().get(DSB_ROW) == null || body.getData().get(DSB_ROW).equals("") || (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
		    codError = new BigDecimal(20010);
		    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "MKD", DSB_ROW,cmpVal);
		    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW)).build();
		    errors.add(error);
		}
		
	
		return errors;
    }

}
