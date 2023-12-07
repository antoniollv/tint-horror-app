package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.pan.dl.IDlCmnPanDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

@Service("Pir")
public class ValidationApiServicePir implements ValidationApiService {

    protected static final String CMP_VAL = "CMP_VAL";

    protected static final String PIR_VAL = "PIR_VAL";

    protected static final String VLD_DAT = "VLD_DAT";

    protected static final String LNG_VAL = "LNG_VAL";

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    IDlCmnPanDAO iDlCmnPanDAO;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	List<ValidationError> errors = new ArrayList<>();
	String accion;
	BigDecimal cmpVal = new BigDecimal(body.getData().get(CMP_VAL));
	// Comprobar si es Crear o Modificar
	if (body.getDataOld() == null) {
	    accion = CTrn.ACN_TYP_CRT;
	} else {
	    accion = CTrn.ACN_TYP_MDF;
	}

	// Si la accion es modificar
	if (accion.equals(CTrn.ACN_TYP_MDF)) {

	    List<String> noModificables = Arrays.asList(CMP_VAL, VLD_DAT, PIR_VAL, LNG_VAL);

	    errors = compruebaErrores(noModificables, body, cmpVal);
	}	
	
	return errors;
    }

    private List<ValidationError> compruebaErrores(List<String> noModificables, DataInExtended body,
	    BigDecimal cmpVal) {

	List<ValidationError> errores = new ArrayList<>();
	ValidationError error;
	OTrnErrS lvError;
	BigDecimal codError;
	for (String name : noModificables) {
	    if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PIR", CMP_VAL,
			cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(body.getDataOld().get(CMP_VAL)).build();
		errores.add(error);
	    }
	}
	return errores;

    }
}
