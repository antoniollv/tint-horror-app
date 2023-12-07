package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Iif")
public class ValidationApiServiceIif implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";
	
    protected static final String LNG_VAL = "LNG_VAL";

    protected static final String IMC_COD_TYP_VAL = "IMC_COD_TYP_VAL";
    
    protected static final String VLD_DAT = "VLD_DAT";
    
    protected static final String DSB_ROW = "DSB_ROW";

    @Autowired
    DlTrnErr lvDlTrnErr;
    

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	List<ValidationError> errorsIif = new ArrayList<>();
	ValidationError errorIif;
	String accion;
	OTrnErrS lvError;
	BigDecimal codError;
	BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));
	// Comprobar si es Crear o Modificar
	if (body.getDataOld() == null) {
	    accion = CTrn.ACN_TYP_CRT;
	} else {
	    accion = CTrn.ACN_TYP_MDF;
	}

	// Si la accion es modificar
	if (accion.equals(CTrn.ACN_TYP_MDF)) {

	    List<String> noModificables = Arrays.asList(LNG_VAL, CMP_VAL, IMC_COD_TYP_VAL, VLD_DAT);

	    errorsIif = compruebaErroresIif(noModificables, body, cmpVal);
	}	
	
	// Comprobamos que DSB_ROW valga S o N
	if (body.getData().get(DSB_ROW) == null
			|| (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
		codError = new BigDecimal(20010);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "IIF", DSB_ROW, cmpVal);
		errorIif = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
				.value(body.getData().get(DSB_ROW)).build();
		errorsIif.add(errorIif);
	}
	

	return errorsIif;
    }

    private List<ValidationError> compruebaErroresIif(List<String> noModificables, DataInExtended body,
	    BigDecimal cmpVal) {

	List<ValidationError> erroresIif = new ArrayList<>();
	ValidationError errorIif;
	OTrnErrS lvError;
	BigDecimal codError;
	for (String name : noModificables) {
	    if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "IIF", CMP_VAL,
			cmpVal);
		errorIif = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(cmpVal.toString()).build();
		erroresIif.add(errorIif);
	    }
	}
	return erroresIif;

    }
}
