package com.mapfre.tron.gdc.bl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mapfre.nwt.exception.NwtException;
import com.mapfre.nwt.trn.c.CTrn;
import com.mapfre.nwt.trn.trn.err.bo.OTrnErrS;
import com.mapfre.tron.api.trn.err.dl.DlTrnErr;
import com.mapfre.tron.api.trn.nwo.dl.IDlTrnNwoDAO;
import com.mapfre.tron.gdc.sr.dto.DataInExtended;
import com.mapfre.tron.gdc.sr.dto.ValidationError;

@Service("Nwo")
public class ValidationApiServiceNwo implements ValidationApiService {

	protected static final String CMP_VAL = "CMP_VAL";

	protected static final String LOB_VAL = "LOB_VAL";

	protected static final String ACS_USR_VAL = "ACS_USR_VAL";

	protected static final String OPR_IDN_VAL = "OPR_IDN_VAL";

	@Autowired
	DlTrnErr lvDlTrnErr;

	@Autowired
	IDlTrnNwoDAO iDlTrnNwoDAO;

	@Override
	public List<ValidationError> conceptValidation(DataInExtended body) {
		List<ValidationError> errorsNwo = new ArrayList<>();
		ValidationError errorNwo;
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

			List<String> noModificables = Arrays.asList(CMP_VAL, LOB_VAL, ACS_USR_VAL, OPR_IDN_VAL);

			errorsNwo = compruebaErrores(noModificables, body, cmpVal);
		}	

		// Validamos si existe el ramo LOB_VAL
		try {
			if (body.getData().get(LOB_VAL) != null && !(body.getData().get(LOB_VAL).equals(""))) {
				iDlTrnNwoDAO.getRamo(cmpVal, new BigDecimal(body.getData().get(LOB_VAL)),
						body.getUser().getLanguage().toUpperCase());
			}

		} catch (NwtException e) {
			errorNwo = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(LOB_VAL)).build();
			errorsNwo.add(errorNwo);
		}

		// Validamos si existe el usuario ACS_USR_VAL
		try {
			if (body.getData().get(ACS_USR_VAL) != null && !(body.getData().get(ACS_USR_VAL).equals(""))) {
				iDlTrnNwoDAO.getUsuario(cmpVal, body.getData().get(ACS_USR_VAL).toUpperCase(),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			errorNwo = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(ACS_USR_VAL)).build();
			errorsNwo.add(errorNwo);
		}

		// Validamos si existe la operacion OPR_IDN_VAL
		try {
			if (body.getData().get(OPR_IDN_VAL) != null && !(body.getData().get(OPR_IDN_VAL).equals(""))) {
				iDlTrnNwoDAO.getOperacion(cmpVal, body.getData().get(OPR_IDN_VAL).toUpperCase(),
						body.getUser().getLanguage().toUpperCase());
			}
		} catch (NwtException e) {
			errorNwo = ValidationError.builder().code(e.getErrors().get(0).getErrIdnVal())
					.field(e.getErrors().get(0).getPrpNam()).value(body.getData().get(OPR_IDN_VAL)).build();
			errorsNwo.add(errorNwo);
		}

		return errorsNwo;
	}

	private List<ValidationError> compruebaErrores(List<String> noModificables, DataInExtended body,
			BigDecimal cmpVal) {

		List<ValidationError> erroresNwo = new ArrayList<>();
		ValidationError errorNwo1;
		OTrnErrS lvError;
		BigDecimal codError;
		for (String name : noModificables) {
			if (!body.getDataOld().get(name).equals(body.getData().get(name))) {
				codError = new BigDecimal(20120);
				lvError = lvDlTrnErr.getError(codError, body.getUser().getLanguage().toUpperCase(), "NWO", CMP_VAL,
						cmpVal);
				errorNwo1 = ValidationError.builder().code(lvError.getErrIdnVal()).field(lvError.getPrpNam())
						.value(body.getDataOld().get(CMP_VAL)).build();
				erroresNwo.add(errorNwo1);
			}
		}
		return erroresNwo;

	}
}
