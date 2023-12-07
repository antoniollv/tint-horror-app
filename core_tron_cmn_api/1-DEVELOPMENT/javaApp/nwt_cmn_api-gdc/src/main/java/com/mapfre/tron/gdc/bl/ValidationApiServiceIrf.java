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

@Service("Irf")
public class ValidationApiServiceIrf implements ValidationApiService {

    protected static final String CMP_VAL = "CMP_VAL";

    protected static final String LNG_VAL = "LNG_VAL";

    protected static final String ICC_RSN_NAM = "ICC_RSN_NAM";

    protected static final String VLD_DAT = "VLD_DAT";

    protected static final String DSB_ROW = "DSB_ROW";

    @Autowired
    DlTrnErr lvDlTrnErr;


    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	List<ValidationError> errorsIrf = new ArrayList<>();
	ValidationError errorIrf;
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

	    List<String> noModificables = Arrays.asList(LNG_VAL, CMP_VAL, ICC_RSN_NAM, VLD_DAT);

	    errorsIrf = compruebaErroresIqi(noModificables, body, cmpVal);
	}	

	// Comprobamos que DSB_ROW valga S o N
	if (body.getData().get(DSB_ROW) == null
		|| (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "IRF", DSB_ROW, cmpVal);
	    errorIrf = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get(DSB_ROW)).build();
	    errorsIrf.add(errorIrf);
	}


	return errorsIrf;
    }

    private List<ValidationError> compruebaErroresIqi(List<String> noModificables, DataInExtended body,
	    BigDecimal cmpVal) {

	List<ValidationError> erroresIrf = new ArrayList<>();
	ValidationError errorIrf;
	OTrnErrS lvError;
	BigDecimal codError;
	for (String name : noModificables) {
	    if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "IRF", CMP_VAL,
			cmpVal);
		errorIrf = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(cmpVal.toString()).build();
		erroresIrf.add(errorIrf);
	    }
	}
	return erroresIrf;

    }
}
