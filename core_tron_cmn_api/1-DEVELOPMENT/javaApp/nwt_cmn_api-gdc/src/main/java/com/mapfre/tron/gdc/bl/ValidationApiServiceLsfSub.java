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
import com.mapfre.tron.api.lsf.lsf.dl.IDlLsfLsfDAO;
import com.mapfre.tron.api.thp.cmc.dl.IDlThpCmcDAO;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.prc.dl.IDlTrnPrcDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
import com.mapfre.tron.api.trn.ntf.dl.IDlTrnNtfDAO;

@Service("LsfSub")
public class ValidationApiServiceLsfSub implements ValidationApiService {

    protected static final String CMP_VAL = "CMP_VAL";

    protected static final String LOB_VAL = "LOB_VAL";

    protected static final String CVR_VAL = "CVR_VAL";

    protected static final String FIL_TYP_VAL = "FIL_TYP_VAL";

    protected static final String SUB_DYN_NAM = "SUB_DYN_NAM";

    protected static final String VLD_DAT = "VLD_DAT";

    protected static final String DSB_ROW = "DSB_ROW";

    @Autowired
    IDlTrnPrcDAO iDlTrnPrcDAO;

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    IDlLsfLsfDAO iDlLsfLsfDAO;

    @Autowired
    IDlTrnNtfDAO iDlTrnNtfDAO;

    @Autowired
    IDlThpCmcDAO iDlThpCmcDAO;

    @Override
    public List<ValidationError> conceptValidation(DataInExtended body) {
	List<ValidationError> errors = new ArrayList<ValidationError>();
	ValidationError error;
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

	    List<String> noModificables = Arrays.asList(CMP_VAL, VLD_DAT, LOB_VAL, CVR_VAL, FIL_TYP_VAL);

	    errors = compruebaErrores(noModificables, body, cmpVal);
	}

	// Comprobamos que DSB_ROW valga S o N
	if (body.getData().get(DSB_ROW) == null
		|| (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "LSF", DSB_ROW, cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get(DSB_ROW).toString()).build();
	    errors.add(error);
	}

	// Validamos si existe el RAMO
	try {
	    if (body.getData().get(LOB_VAL) != null && !(body.getData().get(LOB_VAL).equals(""))) {
		iDlLsfLsfDAO.getCodRamo(cmpVal, new BigDecimal(body.getData().get(LOB_VAL)),
			body.getUser().getLanguage().toUpperCase());
	    }
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LOB_VAL).toString()).build();
	    errors.add(error);
	}

	// Validamos si existe la COBERTURA
	try {
	    if (body.getData().get(CVR_VAL) != null && !(body.getData().get(CVR_VAL).equals(""))
		    && !(body.getData().get(CVR_VAL).equals("9999"))) {
		iDlLsfLsfDAO.getCodCob(cmpVal, new BigDecimal(body.getData().get(LOB_VAL)),
			new BigDecimal(body.getData().get(CVR_VAL)), body.getUser().getLanguage().toUpperCase());
	    }
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(CVR_VAL).toString()).build();
	    errors.add(error);
	}

	// Validamos si existe el TIPO DE EXPEDIENTE
	try {
	    if (body.getData().get(FIL_TYP_VAL) != null && !(body.getData().get(FIL_TYP_VAL).equals(""))) {
		iDlLsfLsfDAO.getFilTypVal(cmpVal, new String(body.getData().get(FIL_TYP_VAL)),
			body.getUser().getLanguage().toUpperCase());
	    }
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(FIL_TYP_VAL).toString()).build();
	    errors.add(error);
	}

	// Validamos si existe APLICA SUBLIMITE DINAMICO
	try {
	    if (body.getData().get(SUB_DYN_NAM) != null && !(body.getData().get(SUB_DYN_NAM).equals(""))) {
		iDlLsfLsfDAO.getNomPrg(body.getData().get(SUB_DYN_NAM), body.getUser().getLanguage().toUpperCase(),
			cmpVal, SUB_DYN_NAM);
	    }
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(SUB_DYN_NAM).toString()).build();
	    errors.add(error);
	}
	return errors;
    }

    private List<ValidationError> compruebaErrores(List<String> noModificables, DataInExtended body,
	    BigDecimal cmpVal) {

	List<ValidationError> errors = new ArrayList<ValidationError>();
	ValidationError error;
	OTrnErrS lvError;
	BigDecimal codError;
	for (String name : noModificables) {
	    if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
		codError = new BigDecimal(20120);
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "LSF", CMP_VAL,
			cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(body.getDataOld().get(CMP_VAL).toString()).build();
		errors.add(error);
	    }
	}
	return errors;

    }
}
