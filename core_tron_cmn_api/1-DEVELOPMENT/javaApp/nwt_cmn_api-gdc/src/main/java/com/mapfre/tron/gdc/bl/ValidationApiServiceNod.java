package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.nod.dl.IDlTrnNodDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Nod")
public class ValidationApiServiceNod implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";
	
    protected static final String LNG_VAL = "LNG_VAL";

    protected static final String OPR_IDN_VAL = "OPR_IDN_VAL";

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    IDlTrnNodDAO iDlTrnNodDAO;  

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	List<ValidationError> errors2 = new ArrayList<>();
	String accion;
	BigDecimal cmpVal = new BigDecimal(body.getUser().getCompany());
	// Comprobar si es Crear o Modificar
	if (body.getDataOld() == null) {
	    accion = CTrn.ACN_TYP_CRT;
	} else {
	    accion = CTrn.ACN_TYP_MDF;
	}

	// Si la accion es modificar
	if (accion.equals(CTrn.ACN_TYP_MDF)) {

	    List<String> noModificables = Arrays.asList(LNG_VAL, OPR_IDN_VAL);

	    errors2 = compruebaErrores(noModificables, body, cmpVal);
	}	
	

	return errors2;
    }

    private List<ValidationError> compruebaErrores(List<String> noModificables, DataInExtended body,
	    BigDecimal cmpVal) {

	List<ValidationError> errores1 = new ArrayList<>();
	ValidationError error2;
	OTrnErrS lvError;
	BigDecimal codError;
	for (String name : noModificables) {
	    if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NOD", CMP_VAL,
			cmpVal);
		error2 = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(cmpVal.toString()).build();
		errores1.add(error2);
	    }
	}
	return errores1;

    }
}
