package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
 
@Service("Cmc")
public class ValidationApiServiceCmc implements ValidationApiService {

    protected static final String ABR_CUADRO_COM = "ABR_CUADRO_COM";
    protected static final String COD_CIA = "COD_CIA";
    protected static final String COD_CUADRO_COM = "COD_CUADRO_COM";
    protected static final String NOM_CUADRO_COM = "NOM_CUADRO_COM";
    @Autowired
    DlTrnErr lvDlTrnErr;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	
	
		String accion;
		ValidationError error;
		List<ValidationError> errors = new ArrayList<>();
		BigDecimal codError;
		OTrnErrS lvError;
		
		BigDecimal cmpVal = new BigDecimal(body.getData().get(COD_CIA));
	
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		// Campos que no se pueden modificar (PK)
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    
		    // No se puede modificar la compañia
		    if(!body.getDataOld().get(COD_CIA).equals(body.getData().get(COD_CIA))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CIA, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_CIA).toString()).build();
				errors.add(error);
		    }
		    // No se puede modificar el Cuadro comision
		    if(!body.getDataOld().get(COD_CUADRO_COM).equals(body.getData().get(COD_CUADRO_COM))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CUADRO_COM,cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(COD_CUADRO_COM).toString()).build();
				errors.add(error);
		    }
	
		}
		
		// Validamos si el campo Cuadro comision está informado
		if (body.getData().get(COD_CUADRO_COM) == null || body.getData().get(COD_CUADRO_COM).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", COD_CUADRO_COM, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(COD_CUADRO_COM).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Nombre cuadro comision está informado
		if (body.getData().get(NOM_CUADRO_COM) == null || body.getData().get(NOM_CUADRO_COM).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", NOM_CUADRO_COM, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(NOM_CUADRO_COM).toString()).build();
			errors.add(error);
		}
		
		// Validamos si el campo Abreviatura del cuadro de comision está informado
		if (body.getData().get(ABR_CUADRO_COM) == null || body.getData().get(ABR_CUADRO_COM).equals("")) {
			codError = new BigDecimal(20003);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "CMC", ABR_CUADRO_COM, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(ABR_CUADRO_COM).toString()).build();
			errors.add(error);
		}
	
		return errors;
    }

}
