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
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("IcfRpt")
public class ValidationApiServiceIcfRpt implements ValidationApiService {

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String LNG_VAL = "LNG_VAL";

    protected static final String RPT_ICC_VAL = "RPT_ICC_VAL";

    protected static final String VLD_DAT = "VLD_DAT";

    @Autowired
    IDlCmnLngDAO iDlCmnLngDAO;
    
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
		
		if (body.getDataOld() == null) {
		    accion = CTrn.ACN_TYP_CRT;
		}
		else {
		    accion = CTrn.ACN_TYP_MDF;
		}
		
		// Validar que no se puedan modificar los siguientes campos PK
		if (accion.equals(CTrn.ACN_TYP_MDF)) {
		    if(!body.getDataOld().get(RPT_ICC_VAL).equals(body.getData().get(RPT_ICC_VAL))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", RPT_ICC_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(RPT_ICC_VAL)).build();
				errors.add(error);
		    }
		    if(!body.getDataOld().get(LNG_VAL).equals(body.getData().get(LNG_VAL))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", LNG_VAL, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(LNG_VAL)).build();
				errors.add(error);
		    }
		    if(!body.getDataOld().get(VLD_DAT).equals(body.getData().get(VLD_DAT))) {
		    	codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", VLD_DAT, cmpVal);
				error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getDataOld().get(VLD_DAT)).build();
				errors.add(error);
		    }
		}
	
		//Marca inhabilitado
		if (body.getData().get(DSB_ROW) == null || (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
			codError = new BigDecimal(20010);
			lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "ICF", DSB_ROW, cmpVal);
			error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam()).value(body.getData().get(DSB_ROW)).build();
			errors.add(error);
		}
		
		// Validar que el idioma seleccionado exista
		try {
		    iDlCmnLngDAO.get(cmpVal, body.getData().get(LNG_VAL),  body.getUser().getLanguage().toUpperCase());
		}
		catch (NwtException e) {
			error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal()).field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LNG_VAL)).build();
			errors.add(error);
		}

		return errors;
    }
}
