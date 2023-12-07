package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.ins.c.CIns;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.cmn.pan.dl.IDlCmnPanDAO;
import com.mapfre.tron.api.trn.prc.dl.IDlTrnPrcDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;

@Service("PanPirLgp")
public class ValidationApiServicePanPirLgp implements ValidationApiService {

    protected static final String CMP_VAL = "CMP_VAL";

    protected static final String PIR_VAL = "PIR_VAL";

    protected static final String LGP_VAL = "LGP_VAL";

    protected static final String PRP_VAL = "PRP_VAL";

    protected static final String VLD_DAT = "VLD_DAT";

    protected static final String DSB_ROW = "DSB_ROW";

    protected static final String PAN_PRC_TYP_VAL = "PAN_PRC_TYP_VAL";

    @Autowired
    IDlTrnPrcDAO iDlTrnPrcDAO;

    @Autowired
    DlTrnErr lvDlTrnErr;

    @Autowired
    IDlCmnPanDAO iDlCmnPanDAO;

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

	    List<String> noModificables = Arrays.asList(CMP_VAL, VLD_DAT, PIR_VAL, LGP_VAL, PRP_VAL, PAN_PRC_TYP_VAL);

	    errors = compruebaErrores(noModificables, body, cmpVal);
	}

	// Comprobamos que DSB_ROW valga S o N
	if (body.getData().get(DSB_ROW) == null
		|| (!body.getData().get(DSB_ROW).equals(CIns.YES) && !body.getData().get(DSB_ROW).equals(CIns.NO))) {
	    codError = new BigDecimal(20010);
	    lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PAN", DSB_ROW, cmpVal);
	    error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
		    .value(body.getData().get(DSB_ROW).toString()).build();
	    errors.add(error);
	}

	// Validamos si existe el ROL
	try {
	    if (body.getData().get(PIR_VAL) != null && !(body.getData().get(PIR_VAL).equals("")) && (body.getData().get(VLD_DAT) != null  && !(body.getData().get(VLD_DAT).equals("")))) {
		Date vldDat = Date.from(LocalDateTime.parse(body.getData().get(VLD_DAT)).atZone(ZoneId.systemDefault()).toInstant());
		iDlCmnPanDAO.getRol(cmpVal, new String(body.getData().get(PIR_VAL)), vldDat,
			body.getUser().getLanguage().toUpperCase());
	    }
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(PIR_VAL).toString()).build();
	    errors.add(error);
	}

	// Validamos si existe el CONCEPTO LOGICO
	try {
	    if (body.getData().get(LGP_VAL) != null && !(body.getData().get(LGP_VAL).equals(""))) {
		iDlCmnPanDAO.getConceptoLogico(cmpVal, new String(body.getData().get(LGP_VAL)),
			body.getUser().getLanguage().toUpperCase());
	    }
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LGP_VAL).toString()).build();
	    errors.add(error);
	}

	// Validamos si existe la PROPIEDAD
	try {
	    if (body.getData().get(PRP_VAL) != null && !(body.getData().get(PRP_VAL).equals(""))) {
		iDlCmnPanDAO.getPropiedad(cmpVal, new String(body.getData().get(PRP_VAL)),
			body.getUser().getLanguage().toUpperCase());
	    }
	} catch (NwtException e) {
	    error = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal().toString())
		    .field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(PRP_VAL).toString()).build();
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
		lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "PAN", CMP_VAL,
			cmpVal);
		error = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
			.value(body.getDataOld().get(CMP_VAL).toString()).build();
		errors.add(error);
	    }
	}
	return errors;

    }
}
