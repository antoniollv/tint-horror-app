package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
import com.mapfre.tron.api.tpd.irf.dl.IDlTpdIrfDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

@Service("IrfMng")
public class ValidationApiServiceIrfMng implements ValidationApiService {

    protected static final String CMP_VAL = "CMP_VAL";

    protected static final String MNG_TYP_VAL = "MNG_TYP_VAL";

    protected static final String ICC_MVM_TYP_VAL = "ICC_MVM_TYP_VAL";

    protected static final String ICC_RSN_VAL = "ICC_RSN_VAL";

    protected static final String VLD_DAT = "VLD_DAT";

    protected static final String DSB_ROW = "DSB_ROW";

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    IDlTpdIrfDAO iDlTpdIrfDAO;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	List<ValidationError> errorsIrfMng = new ArrayList<>();
	ValidationError errorIrfMng;
	String accion;
	OTrnErrS lvErrorIrfMng;
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

	    List<String> noModificables = Arrays.asList(CMP_VAL, VLD_DAT, ICC_MVM_TYP_VAL, ICC_RSN_VAL);

	    errorsIrfMng = compruebaErroresIqiMng(noModificables, body, cmpVal);
	}

	// Comprobamos que DSB_ROW valga S o N
	if (body.getData().get(DSB_ROW) == null
		|| (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvErrorIrfMng = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "IRF", DSB_ROW, cmpVal);
	    errorIrfMng = ValidationError.builder().code(lvErrorIrfMng.getErrIdnVal()).field(lvErrorIrfMng.getPrpNam())
		    .value(body.getData().get(DSB_ROW)).build();
	    errorsIrfMng.add(errorIrfMng);
	}	

	// Validamos si existe TIPO de GESTION
	try {
	    if (body.getData().get(MNG_TYP_VAL) != null && !(body.getData().get(MNG_TYP_VAL).equals(""))) {
	    	iDlTpdIrfDAO.getTipoGestion(cmpVal, body.getData().get(MNG_TYP_VAL),
			body.getUser().getLanguage().toUpperCase());
	    }
	} catch (NwtException e) {
	    errorIrfMng = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(MNG_TYP_VAL)).build();
	    errorsIrfMng.add(errorIrfMng);
	}

	// Validamos si existe TIPO MOVIMIENTO
	try {
	    if (body.getData().get(ICC_MVM_TYP_VAL) != null && !(body.getData().get(ICC_MVM_TYP_VAL).equals(""))) {
	    	iDlTpdIrfDAO.getTipoMovimiento(cmpVal, body.getData().get(ICC_MVM_TYP_VAL),
			body.getUser().getLanguage().toUpperCase());
	    }
	} catch (NwtException e) {
	    errorIrfMng = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(ICC_MVM_TYP_VAL)).build();
	    errorsIrfMng.add(errorIrfMng);
	}

	// Validamos si existe MOTIVO
	try {
	    if (body.getData().get(ICC_RSN_VAL) != null && !(body.getData().get(ICC_RSN_VAL).equals(""))) {
	    	iDlTpdIrfDAO.getMotivo(cmpVal, body.getData().get(ICC_RSN_VAL),
			body.getUser().getLanguage().toUpperCase());
	    }
	} catch (NwtException e) {
	    errorIrfMng = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(ICC_RSN_VAL)).build();
	    errorsIrfMng.add(errorIrfMng);
	}

	return errorsIrfMng;
    }

    private List<ValidationError> compruebaErroresIqiMng(List<String> noModificables, DataInExtended body,
	    BigDecimal cmpVal) {

	List<ValidationError> errorsIrfMng = new ArrayList<>();
	ValidationError errorIrfMng;
	OTrnErrS lvErrorIrfMng;
	BigDecimal codError;
	for (String name : noModificables) {
	    if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
		codError = new BigDecimal(20120);
		lvErrorIrfMng = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "IRF", CMP_VAL,
			cmpVal);
		errorIrfMng = ValidationError.builder().code(lvErrorIrfMng.getErrIdnVal()).field(lvErrorIrfMng.getPrpNam())
			.value(body.getDataOld().get(CMP_VAL)).build();
		errorsIrfMng.add(errorIrfMng);
	    }
	}
	return errorsIrfMng;

    }
}
